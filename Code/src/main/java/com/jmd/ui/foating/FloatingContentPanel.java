package com.jmd.ui.foating;

import com.jmd.common.StaticVar;
import com.jmd.rx.Topic;
import com.jmd.rx.client.InnerMqClient;
import com.jmd.rx.service.InnerMqService;
import com.jmd.ui.common.NoScalingIcon;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.Objects;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@Component
public class FloatingContentPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = -8670937272253637536L;

    public JLabel progressValueLabel;
    public JLabel downloadSpeedValueLabel;

//    public FloatingContentPanel() {
//        init();
//    }

    @PostConstruct
    private void init() {
        
        this.setBackground(new Color(0, 0, 0, 0));

        var logoImage = new ImageIcon(Objects.requireNonNull(FloatingContentPanel.class.getResource("/com/jmd/assets/icon/map.png")));
        var logoIconLabel = new JLabel();
        logoImage.setImage(logoImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        logoIconLabel.setIcon(new NoScalingIcon(logoImage));

        progressValueLabel = new JLabel("0%");
        progressValueLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_12);
        progressValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        progressValueLabel.setForeground(new Color(192, 192, 192));

        downloadSpeedValueLabel = new JLabel("0B/s");
        downloadSpeedValueLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_12);
        downloadSpeedValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        downloadSpeedValueLabel.setForeground(new Color(192, 192, 192));

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(7)
                                .addComponent(logoIconLabel, 30, 30, 30)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(downloadSpeedValueLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(progressValueLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                                .addGap(10))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(5)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(logoIconLabel, 30, 30, 30)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(progressValueLabel, 15, 15, 15)
                                                .addGap(0)
                                                .addComponent(downloadSpeedValueLabel, 15, 15, 15)))
                                .addGap(5))
        );
        this.setLayout(groupLayout);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int fieldX = 0;
        int fieldY = 0;
        int fieldWeight = getSize().width;
        int fieldHeight = getSize().height;
        Color bg = new Color(30, 30, 30, 200);
        g.setColor(bg);
        g.fillRoundRect(fieldX, fieldY, fieldWeight, fieldHeight, 20, 20);
        super.paintChildren(g);
    }

}
