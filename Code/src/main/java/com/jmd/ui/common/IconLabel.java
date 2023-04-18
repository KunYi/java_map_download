package com.jmd.ui.common;

import javax.swing.*;
import java.io.Serial;

public class IconLabel extends JLabel {

    @Serial
    private static final long serialVersionUID = -2643890482143441343L;

    private final AutoScalingIcon icon;

    public IconLabel(String path) {
        this.icon = new AutoScalingIcon(path);
        this.setIcon(this.icon);
    }

    @Override
    public Icon getIcon() {
        return this.icon;
    }

}
