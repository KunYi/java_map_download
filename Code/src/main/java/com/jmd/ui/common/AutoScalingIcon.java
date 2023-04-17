package com.jmd.ui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class AutoScalingIcon implements Icon {

    private final int width;
    private final int height;
    private final ImageIcon originImage;
    private final ImageIcon image;

    private double lastScaleX = -999.0;
    private double lastScaleY = -999.0;

    public AutoScalingIcon(int width, int height, String path) {
        this.width = width;
        this.height = height;
        this.originImage = new ImageIcon(Objects.requireNonNull(NoScalingIcon.class.getResource(path)));
        this.image = new ImageIcon(Objects.requireNonNull(NoScalingIcon.class.getResource(path)));
        this.image.setImage(this.originImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public int getIconWidth() {
        return this.image.getIconWidth();
    }

    public int getIconHeight() {
        return this.image.getIconHeight();
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D g2d = (Graphics2D) g.create();
        AffineTransform at = g2d.getTransform();

        if (this.lastScaleX != at.getScaleX() || this.lastScaleY != at.getScaleY()) {
            this.image.setImage(this.originImage.getImage().getScaledInstance(
                    (int) (this.width * at.getScaleX()),
                    (int) (this.height * at.getScaleY()),
                    Image.SCALE_SMOOTH
            ));
            this.lastScaleX = at.getScaleX();
            this.lastScaleY = at.getScaleY();
        }

        var scaleX = x * at.getScaleX();
        var scaleY = y * at.getScaleY();

        var offsetX = this.image.getIconWidth() * (at.getScaleX() - 1) / 2;
        var offsetY = this.image.getIconHeight() * (at.getScaleY() - 1) / 2;

        var locationX = scaleX + 0;
        var locationY = scaleY + offsetY;

        //  将缩放还原为 1.0
        AffineTransform scaled = AffineTransform.getScaleInstance(1.0 / at.getScaleX(), 1.0 / at.getScaleY());
        at.concatenate(scaled);
        g2d.setTransform(at);

        // 绘制图片
        this.image.paintIcon(c, g2d, (int) locationX, (int) locationY);

        g2d.dispose();

    }

}
