package com.jmd.ui.frame.setting;

import java.awt.Dimension;

import javax.swing.*;

import com.jmd.http.HttpClient;
import com.jmd.rx.Topic;
import com.jmd.rx.service.InnerMqService;
import com.jmd.ui.common.CommonSubFrame;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmd.common.StaticVar;
import com.jmd.http.ProxySetting;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

@Component
public class ProxySettingFrame extends CommonSubFrame {

    @Serial
    private static final long serialVersionUID = -3198278534105518944L;

    private final InnerMqService innerMqService = InnerMqService.getInstance();

    @Autowired
    private HttpClient httpClient;

    private final JCheckBox proxyEnableCheckBox;
    private final JTextField hostnameInputTextField;
    private final JTextField portInputTextField;
    private final JButton okButton;

    public ProxySettingFrame() {

        this.proxyEnableCheckBox = new JCheckBox("使用代理");
        this.proxyEnableCheckBox.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.proxyEnableCheckBox.setSelected(ProxySetting.enable);

        var hostnameTitleLabel = new JLabel("域名/IP地址：");
        hostnameTitleLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.hostnameInputTextField = new JTextField();
        this.hostnameInputTextField.setColumns(10);
        this.hostnameInputTextField.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.hostnameInputTextField.setText(ProxySetting.hostname);

        var portTitleLabel = new JLabel("HTTP代理端口：");
        portTitleLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.portInputTextField = new JTextField();
        this.portInputTextField.setColumns(10);
        this.portInputTextField.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.portInputTextField.setText(String.valueOf(ProxySetting.port));

        this.okButton = new JButton("确定");
        this.okButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.okButton.setFocusable(false);

        var groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup().addContainerGap()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(this.proxyEnableCheckBox)
                                .addComponent(hostnameTitleLabel)
                                .addComponent(this.hostnameInputTextField, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(portTitleLabel)
                                .addComponent(this.portInputTextField, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(this.okButton, Alignment.TRAILING))
                        .addContainerGap()));
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(this.proxyEnableCheckBox)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(hostnameTitleLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(this.hostnameInputTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(portTitleLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(this.portInputTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .addComponent(this.okButton)
                        .addContainerGap()));
        this.getContentPane().setLayout(groupLayout);

        this.setTitle("代理设置");
        this.setSize(new Dimension(300, 260));
        this.setVisible(false);
        this.setResizable(false);

    }

    @PostConstruct
    private void init() {
        this.okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ProxySetting.enable = proxyEnableCheckBox.isSelected();
                ProxySetting.hostname = hostnameInputTextField.getText();
                ProxySetting.port = Integer.parseInt(portInputTextField.getText());
                String result = httpClient.rebuild();
                if (!result.equals("success")) {
                    JOptionPane.showMessageDialog(null, result);
                }
                innerMqService.pub(Topic.UPDATE_PROXY_STATUS, true);
                setVisible(false);
            }
        });
    }

    @Override
    public void setVisible(boolean b) {
        this.proxyEnableCheckBox.setSelected(ProxySetting.enable);
        this.hostnameInputTextField.setText(ProxySetting.hostname);
        this.portInputTextField.setText(String.valueOf(ProxySetting.port));
        super.setVisible(b);
    }

}
