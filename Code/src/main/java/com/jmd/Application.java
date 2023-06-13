package com.jmd;

import java.io.PrintStream;

import javax.swing.*;

import com.jmd.browser.core.ChromiumEmbeddedCore;
import com.jmd.common.StaticVar;
import com.jmd.rx.Topic;
import com.jmd.rx.callback.OnSubscribeCallback;
import com.jmd.rx.service.InnerMqService;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jmd.ui.StartupWindow;

@SpringBootApplication
public class Application {

    public static boolean isStartComplete = false;
    private static final InnerMqService innerMqService = InnerMqService.getInstance();
    private static final CompositeDisposable compositeDisposable = new CompositeDisposable();

    static {
        if (StaticVar.IS_Windows) {
            System.setProperty("sun.java2d.d3d", "true");
        } else if (StaticVar.IS_Mac) {
            System.setProperty("sun.java2d.metal", "true");
        }
        System.load(System.getProperty("user.dir") + "/native/opencv_java470.dll");
    }

    public static void main(String[] args) {

        // Print
        Application.print();
        // 加载界面主题
        try {
            UIManager.setLookAndFeel(ApplicationSetting.getSetting().getThemeClazz());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 加载启动界面
        SwingUtilities.invokeLater(() -> StartupWindow.getInstance().setVisible(true));
        compositeDisposable.add(ProgressBeanPostProcessor.observe().subscribe((result) -> {
            // 监听SpringBoot启动进度
            SwingUtilities.invokeLater(() -> {
                StartupWindow.getInstance().getProgressLabel().setText("正在加载：" + result.getPerc() + "%");
                StartupWindow.getInstance().getBeanNameLabel().setText(result.getBeanName());
                StartupWindow.getInstance().getProgressBar().setValue(result.getPerc());
            });
        }, (e) -> {
        }, () -> {
            // SpringBoot启动完成
            StartupWindow.getInstance().getProgressLabel().setText("加载完成：100%");
            StartupWindow.getInstance().getProgressBar().setValue(100);
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                compositeDisposable.dispose();
                isStartComplete = true;
                innerMqService.pub(Topic.APPLICATION_START_FINISH, true);
            }).start();
        }));
        // 实例化浏览器内核
        ChromiumEmbeddedCore.getInstance();
        // 重定向至ConsoleTextArea
//        JTextAreaOutputStream out = new JTextAreaOutputStream(ApplicationStore.consoleTextArea);
//        System.setOut(new PrintStream(out)); // 设置输出重定向
//        System.setErr(new PrintStream(out)); // 将错误输出也重定向,用于e.printStackTrace
        // 异步启动SpringBoot核心
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                SpringApplication.run(Application.class, args);
                return null;
            }
        }.execute();

    }

    private static void print() {
        System.out.println("SpringBoot Version: " + SpringBootVersion.getVersion());
        System.out.println("User OS: " + System.getProperty("os.name"));
        System.out.println("Java Name: " + System.getProperty("java.vm.name"));
        System.out.println("Java Version: " + System.getProperty("java.vm.version"));
    }

}
