package window;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import window.common.CommonContainerPanel;

public class DownloadConfigFrame extends JFrame {

	private final CommonContainerPanel zoomPanel;
	private final CommonContainerPanel otherPanel;
	private final CommonContainerPanel errorPanel;
	private final CommonContainerPanel pathPanel;
	private final JButton previewButton;
	private final JButton downloadButton;
	private final JButton cancelButton;

	public DownloadConfigFrame() {

		this.zoomPanel = new CommonContainerPanel("层级选择");
		this.otherPanel = new CommonContainerPanel("其他设置");
		this.errorPanel = new CommonContainerPanel("错误处理");
		this.pathPanel = new CommonContainerPanel("路径选择");

		this.previewButton = new JButton("预估下载量");
		this.previewButton.setFocusable(false);
		this.previewButton.setEnabled(false);

		this.downloadButton = new JButton("下载");
		this.downloadButton.setFocusable(false);
		this.downloadButton.setEnabled(false);

		this.cancelButton = new JButton("取消");
		this.cancelButton.setFocusable(false);

		var groupLayout = new GroupLayout(this.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(zoomPanel, 120, 120, 120)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(otherPanel, 305, 305, 305)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(errorPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                                        .addComponent(pathPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)))
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addComponent(previewButton, 100, 100, 100)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(downloadButton, 100, 100, 100)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(cancelButton, 100, 100, 100)))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(zoomPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(otherPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(errorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pathPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cancelButton)
                                        .addComponent(downloadButton)
                                        .addComponent(previewButton))
                                .addContainerGap())
        );
        this.getContentPane().setLayout(groupLayout);

        this.setTitle("下载设置");
        this.setSize(new Dimension(850, 480));
        this.setVisible(false);
        this.setResizable(false);

	}

}
