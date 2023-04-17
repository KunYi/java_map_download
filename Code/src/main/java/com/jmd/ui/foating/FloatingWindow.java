package com.jmd.ui.foating;

import com.jmd.entity.task.TaskStatusEnum;
import com.jmd.rx.Topic;
import com.jmd.rx.client.InnerMqClient;
import com.jmd.rx.service.InnerMqService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;

@Component
public class FloatingWindow extends JWindow {

    @Serial
    private static final long serialVersionUID = 4044639398852146469L;

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private InnerMqClient client;

    private final int width = 100;
    private final int height = 40;

    private int first_x;
    private int first_y;

    @Autowired
    private FloatingContentPanel contentPanel;

    @PostConstruct
    private void init() {

        this.setAlwaysOnTop(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new BorderLayout());

        this.add(this.contentPanel, BorderLayout.CENTER);

        this.getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                first_x = e.getX();
                first_y = e.getY(); // 记录下位移的初点
            }
        });

        this.getContentPane().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                var x = (int) e.getLocationOnScreen().getX();
                var y = (int) e.getLocationOnScreen().getY();
                setBounds(x - first_x, y - first_y, width, height);
            }
        });

        this.setSize(this.width, this.height);
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 300, 125);
        this.setVisible(true);

        try {
            this.subInnerMqMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void subInnerMqMessage() throws Exception {
        this.client = this.innerMqService.createClient();
        this.client.<String>sub(Topic.TASK_STATUS_PROGRESS, (res) -> {
            SwingUtilities.invokeLater(() -> {
                this.contentPanel.progressValueLabel.setText(res);
                this.repaint();
            });
        });
        this.client.<String>sub(Topic.RESOURCE_USAGE_DOWNLOAD_SPEED, (res) -> {
            SwingUtilities.invokeLater(() -> {
                this.contentPanel.downloadSpeedValueLabel.setText(res);
                this.repaint();
            });
        });
        this.client.<TaskStatusEnum>sub(Topic.TASK_STATUS_ENUM, (res) -> {
            SwingUtilities.invokeLater(() -> {
                switch (res) {
                    case FINISH -> this.contentPanel.downloadSpeedValueLabel.setText("下载完成");
                    case PAUSE -> this.contentPanel.downloadSpeedValueLabel.setText("暂停下载");
                    case CANCEL -> this.contentPanel.downloadSpeedValueLabel.setText("取消下载");
                }
                this.repaint();
            });
        });
    }

    public void disposeInnerMqMessage() {
        this.innerMqService.destroyClient(this.client);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g2d);
    }

    @Override
    public void setVisible(boolean f) {
        super.setVisible(f);
        if (f) {
            try {
                this.subInnerMqMessage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            this.disposeInnerMqMessage();
        }
    }

}
