package com.jmd.ui.tab.a_map.bottom;

import javax.swing.JPanel;

import org.springframework.stereotype.Component;

import com.jmd.common.StaticVar;

import lombok.Getter;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.io.Serial;

@Component
public class BottomInfoPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = -5223551680329511378L;

    @Getter
    private final JLabel contentLabel;

    public BottomInfoPanel() {

        this.setLayout(new BorderLayout(0, 0));

        JLabel leftLabel = new JLabel(" JetBrains Runtime: " + System.getProperty("java.vm.version") + ", ");
        leftLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_12);
        this.add(leftLabel, BorderLayout.WEST);

        contentLabel = new JLabel("");
        contentLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_12);
        this.add(contentLabel, BorderLayout.CENTER);

    }

}
