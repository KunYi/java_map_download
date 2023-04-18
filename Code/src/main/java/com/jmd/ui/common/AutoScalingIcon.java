package com.jmd.ui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class AutoScalingIcon implements Icon {

    private final boolean autoSize;
    private final ImageIcon originIcon;
    private final ImageIcon icon = new ImageIcon();

    private int width;
    private int height;

    private double lastScaleX = -999.0;
    private double lastScaleY = -999.0;

    public AutoScalingIcon(String path) {
        this.autoSize = true;
        this.width = 1;
        this.height = 1;
        this.originIcon = new ImageIcon(Objects.requireNonNull(NoScalingIcon.class.getResource(path)));
        this.icon.setImage(this.originIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public AutoScalingIcon(int width, int height, String path) {
        this.autoSize = false;
        this.width = width;
        this.height = height;
        this.originIcon = new ImageIcon(Objects.requireNonNull(NoScalingIcon.class.getResource(path)));
        this.icon.setImage(this.originIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public int getIconWidth() {
        return this.icon.getIconWidth();
    }

    public int getIconHeight() {
        return this.icon.getIconHeight();
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        if (this.autoSize) {
            this.width = c.getWidth() != 0 ? c.getWidth() : (int) c.getPreferredSize().getWidth();
            this.height = c.getHeight() != 0 ? c.getHeight() : (int) c.getPreferredSize().getHeight();
        }

        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = g2d.getTransform();

        if (this.lastScaleX != at.getScaleX() || this.lastScaleY != at.getScaleY()) {
            this.icon.setImage(this.originIcon.getImage().getScaledInstance(
                    (int) (this.width * at.getScaleX()),
                    (int) (this.height * at.getScaleY()),
                    Image.SCALE_SMOOTH
            ));
            this.lastScaleX = at.getScaleX();
            this.lastScaleY = at.getScaleY();
        }

        var scaleX = x * at.getScaleX();
        var scaleY = y * at.getScaleY();

        var offsetX = this.icon.getIconWidth() * (at.getScaleX() - 1) / 2;
        var offsetY = this.icon.getIconHeight() * (at.getScaleY() - 1) / 2;

        var locationX = scaleX + 0;
        var locationY = scaleY + offsetY;

        //  将缩放还原为 1.0
        AffineTransform scaled = AffineTransform.getScaleInstance(1.0 / at.getScaleX(), 1.0 / at.getScaleY());
        at.concatenate(scaled);
        g2d.setTransform(at);

        // 绘制图片
        this.icon.paintIcon(c, g2d, (int) locationX, (int) locationY);

        g2d.dispose();

    }

}
