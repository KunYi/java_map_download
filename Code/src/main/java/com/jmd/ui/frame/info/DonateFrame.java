package com.jmd.ui.frame.info;

import java.awt.Dimension;
import java.io.Serial;

import javax.swing.*;

import com.jmd.ui.common.CommonSubFrame;
import com.jmd.ui.common.IconLabel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DonateFrame extends CommonSubFrame {

    @Serial
    private static final long serialVersionUID = -7739920320485676764L;

    @PostConstruct
    private void init() {

        this.getContentPane().setLayout(null);

        var panel = new JPanel();
        panel.setBounds(0, 0, 633, 450);
        panel.setLayout(null);

        var alipayImageLabel = new IconLabel("/com/jmd/assets/donate/alipay.jpg");
        alipayImageLabel.setBounds(0, 0, 296, 460);
        panel.add(alipayImageLabel);

        var wechatImageLabel = new IconLabel("/com/jmd/assets/donate/wechat.png");
        wechatImageLabel.setBounds(296, 0, 337, 460);
        panel.add(wechatImageLabel);
        getContentPane().add(panel);

        this.setTitle("捐赠开发者（非强制，不影响软件使用）");
        this.setSize(new Dimension(646, 486));
        this.setVisible(false);
        this.setResizable(false);

    }

}
