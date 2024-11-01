package com.jmd.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serial;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import com.jmd.ApplicationSetting;
import com.jmd.common.WsSendTopic;
import com.jmd.rx.Topic;
import com.jmd.rx.client.InnerMqClient;
import com.jmd.rx.service.InnerMqService;
import com.jmd.task.TaskState;
import com.jmd.ui.common.AutoScalingIcon;
import com.jmd.ui.common.CommonDialog;
import com.jmd.ui.frame.info.AboutFrame;
import com.jmd.ui.frame.info.DonateFrame;
import com.jmd.ui.frame.info.LicenseFrame;
import com.jmd.ui.frame.setting.ProxySettingFrame;
import com.jmd.ui.tab.a_map.panel.MapControlBrowserPanel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmd.common.StaticVar;
import com.jmd.model.theme.ThemeEntity;
import com.jmd.task.TaskExecFunc;
import com.jmd.ui.tab.a_map.panel.BottomInfoPanel;
import com.jmd.util.TaskUtils;
import com.jmd.util.I18nUtil;

@Component
public class MainMenuBar extends JMenuBar {

    @Serial
    private static final long serialVersionUID = 6614126656093043485L;

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private InnerMqClient client;

    @Autowired
    private AboutFrame aboutFrame;
    @Autowired
    private LicenseFrame licenseFrame;
    @Autowired
    private DonateFrame donateFrame;
    @Autowired
    private ProxySettingFrame proxySettingFrame;
    @Autowired
    private TaskExecFunc taskExec;
    @Autowired
    private MapControlBrowserPanel mapViewBrowserPanel;
    @Autowired
    private BottomInfoPanel bottomInfoPanel;

    private final Icon selectedIcon = new AutoScalingIcon(
            15, 15,
            "assets/icon/selected.png",
            AutoScalingIcon.XPosition.LEFT, AutoScalingIcon.YPosition.CENTER,
            6, 2
    );

    private final JMenu styleMenu;
    private final JMenuItem refreshMenuItem;
    private final JMenuItem revertMenuItem;
    private final JMenuItem loadTaskMenuItem;
    private final JMenuItem downloadAllWorldMenuItem;
    private final JMenuItem proxyMenuItem;
    private final JMenuItem floatingMenuItem;
    private final JMenuItem aboutMenuItem;
    private final JMenuItem licenseMenuItem;
    private final JMenuItem donateMenuItem;
    private final JMenuItem themeNameLabel;

    public MainMenuBar() {

        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        this.styleMenu = new JMenu(I18nUtil.getString("menu.theme"));
        this.styleMenu.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.add(this.styleMenu);

        var browserMenu = new JMenu(I18nUtil.getString("menu.browser"));
        browserMenu.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.add(browserMenu);

        this.refreshMenuItem = new JMenuItem(I18nUtil.getString("menu.refresh"));
        this.refreshMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        browserMenu.add(this.refreshMenuItem);

        this.revertMenuItem = new JMenuItem(I18nUtil.getString("menu.clearCache"));
        this.revertMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        browserMenu.add(this.revertMenuItem);

        var taskMenu = new JMenu(I18nUtil.getString("menu.tasks"));
        taskMenu.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.add(taskMenu);

        this.loadTaskMenuItem = new JMenuItem(I18nUtil.getString("menu.importDownloads"));
        this.loadTaskMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        taskMenu.add(this.loadTaskMenuItem);

        this.downloadAllWorldMenuItem = new JMenuItem(I18nUtil.getString("menu.downloadWorldMap"));
        this.downloadAllWorldMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        taskMenu.add(this.downloadAllWorldMenuItem);

        var settingMenu = new JMenu(I18nUtil.getString("menu.settings"));
        settingMenu.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.add(settingMenu);

        this.proxyMenuItem = new JMenuItem(I18nUtil.getString("menu.proxySettings"));
        this.proxyMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        settingMenu.add(this.proxyMenuItem);

        this.floatingMenuItem = new JMenuItem(I18nUtil.getString("menu.floatingWindow"));
        this.floatingMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        settingMenu.add(this.floatingMenuItem);

        var otherMenu = new JMenu(I18nUtil.getString("menu.others"));
        otherMenu.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.add(otherMenu);

        this.aboutMenuItem = new JMenuItem(I18nUtil.getString("menu.about"));
        this.aboutMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        otherMenu.add(this.aboutMenuItem);

        this.licenseMenuItem = new JMenuItem(I18nUtil.getString("menu.license"));
        this.licenseMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        otherMenu.add(this.licenseMenuItem);

        this.donateMenuItem = new JMenuItem(I18nUtil.getString("menu.donate"));
        this.donateMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        otherMenu.add(this.donateMenuItem);

        this.themeNameLabel = new JMenuItem();
        this.themeNameLabel.setText("Theme: " + ApplicationSetting.getSetting().getThemeName());
        this.themeNameLabel.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.themeNameLabel.setFocusable(false);
        this.themeNameLabel.setEnabled(false);
        this.add(this.themeNameLabel);

    }

    @PostConstruct
    private void init() {
        // 主题
        for (ThemeEntity parent : StaticVar.THEME_LIST) {
            var themeMenu = new JMenu(parent.getName());
            themeMenu.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
            this.styleMenu.add(themeMenu);
            for (var theme : parent.getSub()) {
                var themeSubMenuItem = new JMenuItem(theme.getName());
                themeSubMenuItem.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
                themeMenu.add(themeSubMenuItem);
                themeSubMenuItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == 1) {
                            var name = parent.getName() + " " + theme.getName();
                            var map = new HashMap<String, Object>();
                            map.put("name", name);
                            map.put("type", theme.getType());
                            map.put("clazz", theme.getClazz());
                            innerMqService.pub(Topic.CHANGE_THEME, map);
                        }
                    }
                });
            }
        }
        // 浏览器 - 刷新
        this.refreshMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    mapViewBrowserPanel.reload();
                }
            }
        });
        // 浏览器 - 清除缓存
        this.revertMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    mapViewBrowserPanel.clearLocalStorage();
                    final var confirmTitle = I18nUtil.getString("dialog.confirmTitle");
                    final var cacheClearedMessage = I18nUtil.getString("dialog.cacheCleared");
                    boolean shouldRefresh = CommonDialog.confirm(confirmTitle, cacheClearedMessage);
                    if (shouldRefresh) {
                        mapViewBrowserPanel.reload();
                    }
                }
            }
        });
        // 任务 - 导入未完成的下载
        this.loadTaskMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    if (TaskState.IS_TASKING) {
                        CommonDialog.alert(null, I18nUtil.getString("dialog.taskInProgress"));
                        return;
                    }
                    var file = selectTaskFile();
                    if (file != null) {
                        var taskAllInfo = TaskUtils.getExistTaskByFile(file);
                        if (taskAllInfo != null) {
                            innerMqService.pub(Topic.MAIN_FRAME_SELECTED_INDEX, 1);
                            taskExec.loadTask(taskAllInfo);
                        } else {
                            CommonDialog.alert(null, I18nUtil.getString("dialog.importFailed"));
                        }
                    }
                }
            }
        });
        // 任务 - 直接下载世界地图
        this.downloadAllWorldMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    if (TaskState.IS_TASKING) {
                        CommonDialog.alert(null, I18nUtil.getString("dialog.taskInProgress"));
                        return;
                    }
                    mapViewBrowserPanel.sendMessageByWebsocket(WsSendTopic.SUBMIT_WORLD_DOWNLOAD, null);
                }
            }
        });
        // 设置 - 代理设置
        this.proxyMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    proxySettingFrame.setVisible(true);
                }
            }
        });
        // 设置 - 悬浮窗
        if (ApplicationSetting.getSetting().getFloatingWindowShow()) {
            this.floatingMenuItem.setIcon(this.selectedIcon);
        }
        this.floatingMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    innerMqService.pub(Topic.FLOATING_WINDOW_TOGGLE, true);
                    ApplicationSetting.getSetting().setFloatingWindowShow(!ApplicationSetting.getSetting().getFloatingWindowShow());
                    ApplicationSetting.save();
                    if (ApplicationSetting.getSetting().getFloatingWindowShow()) {
                        floatingMenuItem.setIcon(selectedIcon);
                    } else {
                        floatingMenuItem.setIcon(null);
                    }
                }
            }
        });
        // 其他 - 关于
        this.aboutMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    aboutFrame.setVisible(true);
                }
            }
        });
        // 其他 - license
        this.licenseMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    licenseFrame.setVisible(true);
                }
            }
        });
        // 其他 - 捐赠开发者
        this.donateMenuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 1) {
                    donateFrame.setVisible(true);
                }
            }
        });
        try {
            this.subInnerMqMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void destroy() {
        this.innerMqService.destroyClient(this.client);
    }

    private void subInnerMqMessage() throws Exception {
        this.client = this.innerMqService.createClient();
        client.<String>sub(Topic.UPDATE_THEME_TEXT, (res) -> {
            themeNameLabel.setText("Theme: " + res);
        });
    }

    private File selectTaskFile() {
        var chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                return I18nUtil.getString("fileChooser.fileDescription");
            }

            @Override
            public boolean accept(File f) {
                var end = f.getName().toLowerCase();
                return end.endsWith(".jmd") || f.isDirectory();
            }
        });
        chooser.setDialogTitle(I18nUtil.getString("fileChooser.title"));
        chooser.setApproveButtonText(I18nUtil.getString("fileChooser.approveButton"));
        chooser.setMultiSelectionEnabled(true);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

}
