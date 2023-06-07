package com.jmd.ui.tab.a_map.browser;

import java.io.Serial;

import com.jmd.ApplicationConfig;
import com.jmd.ui.common.BrowserPanel;
import com.jmd.util.CommonUtils;
import com.jmd.web.common.WsSendData;
import com.jmd.web.websocket.handler.MapWebSocketHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapViewBrowserPanel extends BrowserPanel {

    @Serial
    private static final long serialVersionUID = 5503359353536143127L;

    @Autowired
    private MapWebSocketHandler wsHandler;

    public MapViewBrowserPanel() {
        super("BrowserPanel-MapViewBrowser" + CommonUtils.generateCharMixed(32), "/map", false);
    }

    @PostConstruct
    private void init() {
        super.baseInit();
    }

    @PreDestroy
    protected void destroy() {
        super.baseDestroy();
    }

    public void clearLocalStorage() {
        this.execJS("localStorage.removeItem(\"jmd-config\")");
    }

    public void sendMessageByWebsocket(String topic, String message) {
        this.wsHandler.send(new WsSendData(topic, message));
    }

}