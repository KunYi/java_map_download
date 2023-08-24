package window.tab.d_merge;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import window.common.CommonContainerPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class TileMergePanel extends JPanel {

	private final CommonContainerPanel filePanel;
    private final CommonContainerPanel taskPanel;
    private final CommonContainerPanel mergePanel;
    private final CommonContainerPanel logPanel;
    private final JButton startButton;

    public TileMergePanel() {

        this.filePanel = new CommonContainerPanel("配置文件");
        this.taskPanel = new CommonContainerPanel("任务状态");
        this.mergePanel = new CommonContainerPanel("合并进度");
        this.logPanel = new CommonContainerPanel("任务日志");

        this.startButton = new JButton("开始任务");

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(this.filePanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.logPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addComponent(this.taskPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
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
                                        .addComponent(taskPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(mergePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(logPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(startButton)
                                .addContainerGap())
        );
        this.setLayout(groupLayout);

    }
    
}
