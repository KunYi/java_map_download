package com.jmd.ui.tab.c_tile.panel;

import com.jmd.rx.Topic;
import com.jmd.rx.client.InnerMqClient;
import com.jmd.rx.service.InnerMqService;
import com.jmd.ui.common.BrowserPanel;
import com.jmd.util.CommonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.Serial;

@Component
public class TileViewBrowserPanel extends BrowserPanel {

    @Serial
    private static final long serialVersionUID = -6812120790251794674L;

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private InnerMqClient client;

    @Value("${setting.web.prod}")
    private boolean prod;

    public TileViewBrowserPanel() {
        super("BrowserPanel-TileView" + CommonUtils.generateCharMixed(32), "/tile-view", "请选择瓦片并加载");
    }

    @PostConstruct
    private void init() {
        try {
            this.subInnerMqMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    protected void destroy() {
        this.innerMqService.destroyClient(this.client);
    }

    private void subInnerMqMessage() throws Exception {
        this.client = innerMqService.createClient(this.getCompId());
        this.client.sub(Topic.OPEN_BROWSER_DEV_TOOL, (res) -> {
            SwingUtilities.invokeLater(this::toggleDevTools);
        });
    }

}
