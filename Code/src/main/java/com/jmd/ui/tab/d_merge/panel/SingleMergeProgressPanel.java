package com.jmd.ui.tab.d_merge.panel;

import com.jmd.common.StaticVar;
import com.jmd.rx.Topic;
import com.jmd.rx.client.InnerMqClient;
import com.jmd.rx.service.InnerMqService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.io.Serial;

@Component
public class SingleMergeProgressPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = -1882793538888709465L;

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private InnerMqClient client;

    private final JLabel pixelCountValueLabel;
    private final JLabel threadCountValueLabel;
    private final JLabel mergeProgressValueLabel;

    public SingleMergeProgressPanel() {

        /* label */
        var tablePanel = new JPanel();

        var pixelCountTitleLabel = new JLabel("像素数量：");
        pixelCountTitleLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.pixelCountValueLabel = new JLabel("0/0");
        this.pixelCountValueLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        var threadCountTitleLabel = new JLabel("任务线程数：");
        threadCountTitleLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.threadCountValueLabel = new JLabel("0");
        this.threadCountValueLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        var mergeProgressTitleLabel = new JLabel("合并进度：");
        mergeProgressTitleLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.mergeProgressValueLabel = new JLabel("0.00%");
        this.mergeProgressValueLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        var gl_tablePanel = new GroupLayout(tablePanel);
        gl_tablePanel.setHorizontalGroup(
                gl_tablePanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_tablePanel.createSequentialGroup()
                                .addGroup(gl_tablePanel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_tablePanel.createSequentialGroup()
                                                .addComponent(pixelCountTitleLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.pixelCountValueLabel))
                                        .addGroup(gl_tablePanel.createSequentialGroup()
                                                .addComponent(threadCountTitleLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.threadCountValueLabel))
                                        .addGroup(gl_tablePanel.createSequentialGroup()
                                                .addComponent(mergeProgressTitleLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.mergeProgressValueLabel)))
                                .addContainerGap(40, Short.MAX_VALUE))
        );
        gl_tablePanel.setVerticalGroup(
                gl_tablePanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_tablePanel.createSequentialGroup()
                                .addGroup(gl_tablePanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(pixelCountTitleLabel)
                                        .addComponent(this.pixelCountValueLabel))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_tablePanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(threadCountTitleLabel)
                                        .addComponent(this.threadCountValueLabel))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(gl_tablePanel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(mergeProgressTitleLabel)
                                        .addComponent(this.mergeProgressValueLabel))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        tablePanel.setLayout(gl_tablePanel);
        /* label */

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addContainerGap(0, Short.MAX_VALUE)
                                                .addComponent(tablePanel, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        this.setLayout(groupLayout);

    }

    @PostConstruct
    private void init() {
        try {
            this.subInnerMqMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    protected void destroy() {
        this.innerMqService.destroyClient(this.client);
    }

    private void subInnerMqMessage() throws Exception {
        this.client = this.innerMqService.createClient();
        this.client.<String>sub(Topic.SINGLE_MERGE_PROCESS_PIXEL_COUNT, (res) -> {
            SwingUtilities.invokeLater(() -> pixelCountValueLabel.setText(res));
        });
        this.client.<String>sub(Topic.SINGLE_MERGE_PROCESS_THREAD, (res) -> {
            SwingUtilities.invokeLater(() -> threadCountValueLabel.setText(res));
        });
        this.client.<String>sub(Topic.SINGLE_MERGE_PROCESS_PROGRESS, (res) -> {
            SwingUtilities.invokeLater(() -> mergeProgressValueLabel.setText(res));
        });
        this.client.sub(Topic.SINGLE_MERGE_PROCESS_CLEAR, (res) -> {
            SwingUtilities.invokeLater(() -> {
                pixelCountValueLabel.setText("0/0");
                threadCountValueLabel.setText("0");
                mergeProgressValueLabel.setText("0.00%");
            });
        });
    }

}
