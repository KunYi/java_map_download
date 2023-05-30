package com.jmd.ui.frame.info;

import java.awt.Dimension;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Desktop;

import com.jmd.ui.common.*;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Component;

import com.jmd.common.StaticVar;
import com.jmd.util.CommonUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serial;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.border.EtchedBorder;

@Component
public class AboutFrame extends CommonSubFrame {

    @Serial
    private static final long serialVersionUID = -5612514860347124476L;

    private final String git = "https://gitee.com/CrimsonHu/java_map_download";

    public AboutFrame() {

        var springBootTextArea = new JTextArea();
        this.getContentPane().add(springBootTextArea, BorderLayout.NORTH);
        springBootTextArea.setFont(StaticVar.FONT_YaHeiConsolas_13);
        springBootTextArea.setText(""
                + "  .   ____          _            __ _ _\n"
                + " /\\\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\\n"
                + "( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\\n"
                + " \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )\n"
                + "  '  |____| .__|_| |_|_| |_\\__, | / / / /\n"
                + " =========|_|==============|___/=/_/_/_/\n"
                + " :: Spring Boot ::        (" + SpringBootVersion.getVersion() + ")" + "");
        springBootTextArea.setEditable(false);

        var panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel, BorderLayout.CENTER);

        var jdkIconLabel = new IconLabel("/com/jmd/assets/icon/jetbrains.png");
        jdkIconLabel.setBounds(15, 10, 30, 30);
        panel.add(jdkIconLabel);

        var jdkTextLabel = new JLabel("JetBrains Runtime 17");
        jdkTextLabel.setBounds(55, 10, 166, 30);
        jdkTextLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        panel.add(jdkTextLabel);

        var jcefIconLabel = new IconLabel("/com/jmd/assets/icon/cef.png");
        jcefIconLabel.setBounds(238, 10, 40, 30);
        panel.add(jcefIconLabel);

        var jcefTextLabel = new JLabel("JCEF 104");
        jcefTextLabel.setBounds(288, 10, 156, 30);
        jcefTextLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        panel.add(jcefTextLabel);

        var openlayersIconLabel = new IconLabel("/com/jmd/assets/icon/openlayers.png");
        openlayersIconLabel.setBounds(15, 46, 30, 30);
        panel.add(openlayersIconLabel);

        var openlayersTextLabel = new JLabel("OpenLayers 7.3.0");
        openlayersTextLabel.setBounds(55, 46, 166, 30);
        openlayersTextLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        panel.add(openlayersTextLabel);

        var angularIconLabel = new IconLabel("/com/jmd/assets/icon/angular.png");
        angularIconLabel.setBounds(238, 46, 30, 30);
        panel.add(angularIconLabel);

        var angularTextLabel = new JLabel("Angular 15.2.2");
        angularTextLabel.setBounds(278, 46, 166, 30);
        angularTextLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        panel.add(angularTextLabel);

        var opencvIconLabel = new IconLabel("/com/jmd/assets/icon/opencv.png");
        opencvIconLabel.setBounds(15, 82, 30, 30);
        panel.add(opencvIconLabel);

        var opencvTextLabel = new JLabel("OpenCV 4.5.5");
        opencvTextLabel.setBounds(55, 82, 166, 30);
        opencvTextLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        panel.add(opencvTextLabel);

        var gitIconLabel = new IconLabel("/com/jmd/assets/icon/git.png");
        gitIconLabel.setBounds(15, 118, 30, 30);
        panel.add(gitIconLabel);

        var gitTextLabel = new JLabel(git);
        gitTextLabel.setBounds(55, 118, 389, 30);
        gitTextLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        panel.add(gitTextLabel);

        var gitCopyButton = new JButton("复制git地址");
        gitCopyButton.setBounds(10, 158, 217, 29);
        gitCopyButton.setFocusable(false);
        gitCopyButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        gitCopyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    CommonUtils.setClipboardText(git);
                    CommonDialog.alert(null, "已复制到剪贴板");
                }
            }
        });
        panel.add(gitCopyButton);

        var gitOpenButton = new JButton("打开git");
        gitOpenButton.setBounds(233, 158, 211, 29);
        gitOpenButton.setFocusable(false);
        gitOpenButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        gitOpenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    try {
                        Desktop desktop = Desktop.getDesktop();
                        desktop.browse(new URI(git));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        panel.add(gitOpenButton);

        var tipTextArea = new JTextArea();
        tipTextArea.setBounds(10, 193, 434, 78);
        tipTextArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        tipTextArea.setLineWrap(true);
        tipTextArea.setText("Build日期：2023-05-30");
        tipTextArea.setEditable(false);
        panel.add(tipTextArea);

        this.setTitle("关于地图下载器");
        this.setSize(new Dimension(470, 450));
        this.setVisible(false);
        this.setResizable(false);

    }

}
