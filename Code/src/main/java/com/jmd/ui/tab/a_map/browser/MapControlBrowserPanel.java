package com.jmd.ui.tab.a_map.browser;

import java.io.Serial;

import com.jmd.ui.common.BrowserPanel;
import com.jmd.util.CommonUtils;
import com.jmd.web.common.WsSendData;
import com.jmd.web.websocket.handler.MapWebSocketHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MapControlBrowserPanel extends BrowserPanel {

    @Serial
    private static final long serialVersionUID = 5503359353536143127L;

    @Value("${setting.web.prod}")
    private boolean prod;

    @Autowired
    private MapWebSocketHandler wsHandler;

    public MapControlBrowserPanel() {
        super("BrowserPanel-MapView" + CommonUtils.generateCharMixed(32), "/map-control");
    }

    @PostConstruct
    private void init() {
        super.baseInit(this.prod);
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