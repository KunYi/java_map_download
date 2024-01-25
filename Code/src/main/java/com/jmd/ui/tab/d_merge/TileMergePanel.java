package com.jmd.ui.tab.d_merge;

import com.jmd.common.StaticVar;
import com.jmd.task.SingleMergeExecFunc;
import com.jmd.task.TaskState;
import com.jmd.ui.common.CommonContainerPanel;
import com.jmd.ui.common.CommonDialog;
import com.jmd.ui.tab.d_merge.panel.SingleMergeFileSelectorPanel;
import com.jmd.ui.tab.d_merge.panel.SingleMergeLogPanel;
import com.jmd.ui.tab.d_merge.panel.SingleMergeStatusPanel;
import com.jmd.ui.tab.d_merge.panel.SingleMergeProgressPanel;
import com.jmd.util.TaskUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serial;

@Component
public class TileMergePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = -1007443598128038241L;

    @Autowired
    private SingleMergeExecFunc mergeExec;

    @Autowired
    private SingleMergeFileSelectorPanel singleMergeFileSelectorPanel;
    @Autowired
    private SingleMergeStatusPanel singleMergeStatusPanel;
    @Autowired
    private SingleMergeProgressPanel singleMergeProgressPanel;
    @Autowired
    private SingleMergeLogPanel singleMergeLogPanel;

    private final CommonContainerPanel filePanel;
    private final CommonContainerPanel statusPanel;
    private final CommonContainerPanel mergePanel;
    private final CommonContainerPanel logPanel;
    private final JButton startButton;

    public TileMergePanel() {

        this.filePanel = new CommonContainerPanel("配置文件");
        this.statusPanel = new CommonContainerPanel("任务状态");
        this.mergePanel = new CommonContainerPanel("合并进度");
        this.logPanel = new CommonContainerPanel("任务日志");

        this.startButton = new JButton("开始任务");
        this.startButton.setFocusable(false);
        this.startButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(this.filePanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.logPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addComponent(this.statusPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.mergePanel, 450, 450, 450))
                                        .addComponent(this.startButton, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(this.filePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(this.statusPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.mergePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(this.logPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(this.startButton)
                                .addContainerGap())
        );
        this.setLayout(groupLayout);

    }

    @PostConstruct
    private void init() {

        /* 配置文件 */
        this.filePanel.addContent(this.singleMergeFileSelectorPanel);
        /* 配置文件 */

        /* 任务状态 */
        this.statusPanel.addContent(this.singleMergeStatusPanel);
        /* 任务状态 */

        /* 合并进度 */
        this.mergePanel.addContent(this.singleMergeProgressPanel);
        /* 合并进度 */

        /* 任务日志 */
        this.logPanel.addContent(this.singleMergeLogPanel);
        /* 任务日志 */

        this.startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1 && startButton.isEnabled()) {
                    startMerge();
                }
            }
        });

    }

    private void startMerge() {
        if (TaskState.IS_MERGING) {
            CommonDialog.alert("错误", "正在合并中");
            return;
        }
        var mergeInfo = this.singleMergeFileSelectorPanel.getMergeInfo();
        if (mergeInfo == null) {
            CommonDialog.alert("错误", "错误的合并配置文件");
            return;
        }
        this.startButton.setEnabled(false);
        this.singleMergeFileSelectorPanel.getFileSelectorButton().setEnabled(false);
        this.mergeExec.start(mergeInfo, () -> {
            this.startButton.setEnabled(true);
            this.singleMergeFileSelectorPanel.getFileSelectorButton().setEnabled(true);
        });
    }

}
