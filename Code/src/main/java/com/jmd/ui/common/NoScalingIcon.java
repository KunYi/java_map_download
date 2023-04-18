package com.jmd.ui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class NoScalingIcon implements Icon {

    private final ImageIcon icon = new ImageIcon();

    public NoScalingIcon(int width, int height, String path) {
        var originIcon = new ImageIcon(Objects.requireNonNull(NoScalingIcon.class.getResource(path)));
        this.icon.setImage(originIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public int getIconWidth() {
        return icon.getIconWidth();
    }

    public int getIconHeight() {
        return icon.getIconHeight();
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D g2d = (Graphics2D) g.create();

        AffineTransform at = g2d.getTransform();

        int scaleX = (int) (x * at.getScaleX());
        int scaleY = (int) (y * at.getScaleY());

        int offsetX = (int) (icon.getIconWidth() * (at.getScaleX() - 1) / 2);
        int offsetY = (int) (icon.getIconHeight() * (at.getScaleY() - 1) / 2);

        int locationX = scaleX + offsetX;
        int locationY = scaleY + offsetY;

        //  Reset scaling to 1.0 by concatenating an inverse scale transform

        AffineTransform scaled = AffineTransform.getScaleInstance(1.0 / at.getScaleX(), 1.0 / at.getScaleY());
        at.concatenate(scaled);
        g2d.setTransform(at);

        icon.paintIcon(c, g2d, locationX, locationY);

        g2d.dispose();

    }

}
