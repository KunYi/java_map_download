package com.jmd.ui.tab.d_merge.panel;

import com.jmd.common.StaticVar;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.io.Serial;

@Component
public class MergeFileSelectorPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 8713308918946071631L;

    private final JButton fileSelectorButton;
    private final JLabel filePathLabel;

    public MergeFileSelectorPanel() {

        this.fileSelectorButton = new JButton("选择文件");
        this.fileSelectorButton.setFocusable(false);
        this.fileSelectorButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.filePathLabel = new JLabel();
        this.filePathLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(this.fileSelectorButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(this.filePathLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.fileSelectorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.filePathLabel))
                                .addContainerGap())
        );
        this.setLayout(groupLayout);

    }

    @PostConstruct
    private void init() {

    }

}
