package com.jmd.ui.tab.c_tile.panel;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import com.jmd.ApplicationConfig;
import com.jmd.common.StaticVar;

import javax.swing.*;
import java.io.Serial;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@Component
public class TileApiAddressPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 6755052092289839991L;

    private final JButton copyBrowserViewAddressButton;
    private final JButton copyLocalApiAddressButton;

    public TileApiAddressPanel() {

        this.copyBrowserViewAddressButton = new JButton("复制");
        this.copyBrowserViewAddressButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.copyBrowserViewAddressButton.setFocusable(false);

        this.copyLocalApiAddressButton = new JButton("复制");
        this.copyLocalApiAddressButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.copyLocalApiAddressButton.setFocusable(false);

        JLabel browserViewAddressLabel = new JLabel("在浏览器中查看：" + "http://localhost:" + ApplicationConfig.startPort + "/web/index.html/tile-view");
        JLabel localApiAddressLabel = new JLabel("在Web项目中使用：" + "http://localhost:" + ApplicationConfig.startPort + "/tile/view?z={z}&x={x}&y={y}");

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(localApiAddressLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(browserViewAddressLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(this.copyBrowserViewAddressButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(this.copyLocalApiAddressButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.copyBrowserViewAddressButton)
                                        .addComponent(browserViewAddressLabel))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.copyLocalApiAddressButton)
                                        .addComponent(localApiAddressLabel))
                                .addContainerGap())
        );
        this.setLayout(groupLayout);

    }

    @PostConstruct
    private void init() {

    }
}
