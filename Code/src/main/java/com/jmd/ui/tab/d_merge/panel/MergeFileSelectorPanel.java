package com.jmd.ui.tab.d_merge.panel;

import com.jmd.ApplicationSetting;
import com.jmd.common.StaticVar;
import com.jmd.rx.Topic;
import com.jmd.task.TaskState;
import com.jmd.ui.common.CommonDialog;
import com.jmd.util.TaskUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serial;

@Component
public class MergeFileSelectorPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 8713308918946071631L;

    private final JButton fileSelectorButton;
    private final JLabel filePathLabel;

    private String selectedFilePath;

    public MergeFileSelectorPanel() {

        this.fileSelectorButton = new JButton("选择文件");
        this.fileSelectorButton.setFocusable(false);
        this.fileSelectorButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.filePathLabel = new JLabel();
        this.filePathLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(this.fileSelectorButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(this.filePathLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.fileSelectorButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.filePathLabel))
                                .addContainerGap())
        );
        this.setLayout(groupLayout);

    }

    @PostConstruct
    private void init() {
        this.fileSelectorButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    var chooser = new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    var lastDirPath = ApplicationSetting.getSetting().getLastDirPath();
                    if (lastDirPath != null && !lastDirPath.isEmpty() && new File(lastDirPath).isDirectory()) {
                        chooser.setCurrentDirectory(new File(lastDirPath));
                    }
                    chooser.setFileFilter(new FileFilter() {
                        @Override
                        public String getDescription() {
                            return "瓦片合并配置(*.jmdmergefile)";
                        }

                        @Override
                        public boolean accept(File f) {
                            var end = f.getName().toLowerCase();
                            return end.endsWith(".jmdmergefile") || f.isDirectory();
                        }
                    });
                    chooser.setDialogTitle("选择瓦片合并配置文件...");
                    chooser.setApproveButtonText("选择");
                    chooser.setMultiSelectionEnabled(true);
                    chooser.showOpenDialog(null);
                    var file = chooser.getSelectedFile();
                    if (file != null) {
                        selectedFilePath = file.getAbsolutePath();
                        filePathLabel.setText(file.getAbsolutePath());
                        ApplicationSetting.getSetting().setLastDirPath(file.getParent());
                    }
                }
            }
        });
    }

    public String getMergeFilePath() {
        return this.selectedFilePath;
    }

}
