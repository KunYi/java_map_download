package com.jmd.browser.core;

import com.jetbrains.cef.JCefAppConfig;
import com.jmd.ApplicationPort;
import com.jmd.browser.handler.MenuHandler;
import com.jmd.browser.view.BrowserViewContainer;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefRendering;

public class ChromiumEmbeddedCore {

    private static volatile ChromiumEmbeddedCore instance;

    private final CefApp cefApp;

    public ChromiumEmbeddedCore() {
        String[] args = JCefAppConfig.getInstance().getAppArgs();
        CefSettings settings = JCefAppConfig.getInstance().getCefSettings();
        settings.cache_path = System.getProperty("user.dir") + "/context/jcef/data_" + ApplicationPort.startPort;
        // 获取CefApp实例
        CefApp.startup(args);
        this.cefApp = CefApp.getInstance(args, settings);
    }

    public CefClient createClient(BrowserViewContainer container) {
        CefClient cefClient = this.cefApp.createClient();
        cefClient.addContextMenuHandler(new MenuHandler(container));
        return cefClient;
    }

    public CefBrowser createBrowser(CefClient client, String url) {
        return client.createBrowser(url, CefRendering.DEFAULT, true);
    }

    public String getVersion() {
        return "Chromium Embedded Framework (CEF), " + "ChromeVersion: " + cefApp.getVersion().getChromeVersion();
    }

    public void dispose() {
        cefApp.dispose();
    }

    public static ChromiumEmbeddedCore getInstance() {
        if (instance == null) {
            synchronized (ChromiumEmbeddedCore.class) {
                if (instance == null) {
                    instance = new ChromiumEmbeddedCore();
                }
            }
        }
        return instance;
    }

}
