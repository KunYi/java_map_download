package com.jmd.ui.frame.download.config;

import java.awt.*;
import javax.swing.*;

import com.jmd.model.task.MergeInfoEntity;
import com.jmd.rx.Topic;
import com.jmd.rx.service.InnerMqService;
import com.jmd.task.TaskState;
import com.jmd.ui.common.CommonContainerPanel;
import com.jmd.ui.common.CommonDialog;
import com.jmd.ui.common.CommonSubFrame;
import com.jmd.ui.frame.download.config.panel.DownloadErrorHandlerPanel;
import com.jmd.ui.frame.download.preview.DownloadPreviewFrame;
import com.jmd.ui.frame.download.config.panel.DownloadOtherSettingPanel;
import com.jmd.ui.frame.download.config.panel.DownloadPathSelectorPanel;
import com.jmd.ui.frame.download.config.panel.DownloadZoomSelectorPanel;
import com.jmd.util.MyFileUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmd.common.StaticVar;
import com.jmd.model.geo.Polygon;
import com.jmd.model.task.TaskCreateEntity;
import com.jmd.task.TaskExecFunc;
import com.jmd.ui.MainFrame;
import com.jmd.util.TaskUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@Component
public class DownloadConfigFrame extends CommonSubFrame {

    @Serial
    private static final long serialVersionUID = 394597878094632313L;

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private final DownloadConfigFrame that = this;

    @Autowired
    private TaskExecFunc taskExec;
    @Autowired
    private MainFrame mainFrame;
    @Autowired
    private DownloadPreviewFrame downloadPreviewFrame;

    @Autowired
    private DownloadZoomSelectorPanel zoomSelectorPanel;
    @Autowired
    private DownloadOtherSettingPanel otherSettingPanel;
    @Autowired
    private DownloadErrorHandlerPanel errorHandlerPanel;
    @Autowired
    private DownloadPathSelectorPanel pathSelectorPanel;

    private String url;
    private List<Polygon> polygons;
    private String tileName;
    private String mapType;
    private String oriImgType;

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
        this.previewButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.downloadButton = new JButton("下载");
        this.downloadButton.setFocusable(false);
        this.downloadButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        this.cancelButton = new JButton("取消");
        this.cancelButton.setFocusable(false);
        this.cancelButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);

        var groupLayout = new GroupLayout(this.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(this.zoomPanel, 120, 120, 120)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.otherPanel, 305, 305, 305)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(this.errorPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                                        .addComponent(this.pathPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)))
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addComponent(this.previewButton, 100, 100, 100)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.downloadButton, 100, 100, 100)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.cancelButton, 100, 100, 100)))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.zoomPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.otherPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(this.errorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.pathPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.cancelButton)
                                        .addComponent(this.downloadButton)
                                        .addComponent(this.previewButton))
                                .addContainerGap())
        );
        this.getContentPane().setLayout(groupLayout);

        this.setTitle("下载设置");
        this.setSize(new Dimension(850, 480));
        this.setResizable(false);
        this.setVisible(false);

    }

    @PostConstruct
    private void init() {

        /* 层级选择 */
        this.zoomPanel.addContent(zoomSelectorPanel);
        /* 层级选择 */

        /* 其他设置 */
        this.otherPanel.addContent(otherSettingPanel);
        /* 其他设置 */

        /* 错误处理 */
        this.errorPanel.addContent(errorHandlerPanel);
        /* 其他设置 */

        /* 路径选择 */
        this.pathPanel.addContent(pathSelectorPanel);
        /* 路径选择 */

        /* 预估 */
        this.previewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1 && previewButton.isEnabled()) {
                    downloadPreviewFrame.showPreview(zoomSelectorPanel.getSelectedZooms(), polygons);
                }
            }
        });
        /* 预估 */

        /* 开始下载 */
        this.downloadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1 && downloadButton.isEnabled()) {
                    if (TaskState.IS_TASKING) {
                        CommonDialog.alert(that, null, "当前正在进行下载任务");
                        return;
                    }
                    if (zoomSelectorPanel.getSelectedZooms().size() == 0) {
                        CommonDialog.alert(that, null, "请选择下载层级");
                        return;
                    }
                    if (pathSelectorPanel.getSelectedDirPath() == null) {
                        CommonDialog.alert(that, null, "请选择保存路径");
                        return;
                    }
                    boolean isCreate = true;
                    var path = MyFileUtils.checkFilePath(pathSelectorPanel.getSelectedDirPath().getAbsolutePath());
                    if (isTaskExist(path)) {
                        String[] options = {"导入任务", "创建新任务"};
                        var n = CommonDialog.option("选择", "该目录下已存在下载任务，请选择导入任务或新建任务", options);
                        isCreate = (n != null && n == 1);
                    }
                    if (isCreate) {
                        // 创建新任务
                        innerMqService.pub(Topic.MAIN_FRAME_SELECTED_INDEX, 1);
                        startNewTask();
                    } else {
                        // 导入现有任务
                        var taskAllInfo = TaskUtils.getExistTaskByPath(path);
                        if (null != taskAllInfo) {
                            innerMqService.pub(Topic.MAIN_FRAME_SELECTED_INDEX, 1);
                            taskExec.loadTask(taskAllInfo);
                        } else {
                            var f = CommonDialog.confirm("选择", "读取现有任务失败，这将创建新的下载任务");
                            if (f) {
                                innerMqService.pub(Topic.MAIN_FRAME_SELECTED_INDEX, 1);
                                startNewTask();
                            } else {
                                CommonDialog.alert(null, "用户取消创建下载任务");
                                return;
                            }
                        }
                    }
                    setVisible(false);
                }
            }
        });

        /* 取消 */
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setVisible(false);
            }
        });
        /* 取消 */

    }

    @PreDestroy
    protected void destroy() {
        super.destroy();
    }

    // 创建任务，打开面板
    public void createNewTask(String url, List<Polygon> polygons, String tileName, String mapType, String oriImgType)
            throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            this.setVisible(true);
        });
        this.zoomSelectorPanel.removeAllSelectedZooms();
        this.url = url;
        this.polygons = polygons;
        this.tileName = tileName;
        this.mapType = mapType;
        this.oriImgType = oriImgType;
    }

    // 创建任务实例类
    private TaskCreateEntity getTaskCreate() {
        TaskCreateEntity taskCreate = new TaskCreateEntity();
        taskCreate.setTileUrl(this.url);
        taskCreate.setTileName(this.tileName);
        taskCreate.setMapType(this.mapType);
        taskCreate.setSavePath(this.pathSelectorPanel.getSelectedDirPath().getAbsolutePath());
        taskCreate.setZoomList(this.zoomSelectorPanel.getSelectedZooms());
        taskCreate.setPolygons(this.polygons);
        taskCreate.setPathStyle(this.otherSettingPanel.getPathStyle());
        taskCreate.setImgType(this.otherSettingPanel.getImgType());
        taskCreate.setOriImgType(this.oriImgType);
        taskCreate.setIsCoverExists(this.otherSettingPanel.isCoverExist());
        taskCreate.setIsMergeTile(this.otherSettingPanel.isMergeTile());
        taskCreate.setMergeType(this.otherSettingPanel.getMergeType());
        taskCreate.setErrorHandlerType(this.errorHandlerPanel.getErrorHandlerType());
        return taskCreate;
    }

    // 开始
    private void startNewTask() {
        var taskCreate = this.getTaskCreate();
        this.taskExec.createTask(taskCreate);
        if (this.otherSettingPanel.isMergeFileSave()) {
            var mergeInfo = new MergeInfoEntity();
            mergeInfo.setImgType(taskCreate.getImgType());
            mergeInfo.setOriImgType(taskCreate.getOriImgType());
            mergeInfo.setSavePath(taskCreate.getSavePath());
            mergeInfo.setPathStyle(taskCreate.getPathStyle());
            try {
                MyFileUtils.saveObj2File(mergeInfo, taskCreate.getSavePath() + "/merge_info.jmdmergefile");
            } catch (IOException e) {
                CommonDialog.alert(null, "保存瓦片合并配置失败");
            }
        }
    }

    private boolean isTaskExist(String path) {
        var file = new File(path + "/task_info.jmd");
        return file.exists() && file.isFile();
    }

}
