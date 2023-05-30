package com.jmd.ui.common

import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.geom.AffineTransform
import java.math.BigDecimal
import java.util.*
import javax.swing.Icon
import javax.swing.ImageIcon

class AutoScalingIcon : Icon {

    private val autoSize: Boolean
    private val originIcon: ImageIcon
    private val icon = ImageIcon()
    private var width: Int
    private var height: Int
    private var lastScaleX = -999.0
    private var lastScaleY = -999.0

    constructor(path: String) {
        this.autoSize = true
        this.width = 2
        this.height = 2
        this.originIcon = ImageIcon(Objects.requireNonNull(NoScalingIcon::class.java.getResource(path)))
        this.icon.image = this.originIcon.image.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)
    }

    constructor(width: Int, height: Int, path: String) {
        this.autoSize = false
        this.width = width
        this.height = height
        this.originIcon = ImageIcon(Objects.requireNonNull(NoScalingIcon::class.java.getResource(path)))
        this.icon.image = this.originIcon.image.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH)
    }

    override fun getIconWidth(): Int {
        return this.icon.iconWidth
    }

    override fun getIconHeight(): Int {
        return this.icon.iconHeight
    }

    override fun paintIcon(c: Component, g: Graphics, x: Int, y: Int) {

        if (this.autoSize) {
            this.width = if (c.width != 0) c.width else c.preferredSize.getWidth().toInt()
            this.height = if (c.height != 0) c.height else c.preferredSize.getHeight().toInt()
        }

        // 最低图片宽高，防止宽高在后面计算中为0
        if (this.width < 2) {
            this.width = 2
        }
        if (this.height < 2) {
            this.height = 2
        }

        val g2d = g.create() as Graphics2D
        val at = g2d.transform

        if (this.lastScaleX != at.scaleX || this.lastScaleY != at.scaleY) {
            var imgWidth = width
            var imgHeight = height
            if (at.scaleX > 1.0) {
                imgWidth = (width * at.scaleX).toInt()
            }
            if (at.scaleY > 1.0) {
                imgHeight = (height * at.scaleY).toInt()
            }
            // 在1除以缩放比例时，如果结果为无限小数，则需要将宽高减去1才能正确显示
            if (this.cantDivide(1.0, at.scaleX)) {
                imgWidth -= 1
            }
            if (this.cantDivide(1.0, at.scaleY)) {
                imgHeight -= 1
            }
            this.icon.image = this.originIcon.image.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH)
            this.lastScaleX = at.scaleX
            this.lastScaleY = at.scaleY
        }

        // 新的 X 位置
        val locationX = x * at.scaleX
        // 新的 Y 位置
        // Y 位置需要修正
        val originY = y * at.scaleY
        val offsetY = this.height * at.scaleY * ((at.scaleY - 1) / 2)
        val locationY = originY + offsetY

        //  将缩放还原为 1.0
        val scaled = AffineTransform.getScaleInstance(1.0 / at.scaleX, 1.0 / at.scaleY)
        at.concatenate(scaled)
        g2d.transform = at

        // 绘制图片
        this.icon.paintIcon(c, g2d, locationX.toInt(), locationY.toInt())
        g2d.dispose()

    }

    /* 判断两个数相除是否为无限小数 */
    private fun cantDivide(a: Double, b: Double): Boolean {
        try {
            BigDecimal(a).divide(BigDecimal(b))
        } catch (e: ArithmeticException) {
            if (e.message!!.contains("Non-terminating")) {
                return true
            }
        }
        return false
    }

}