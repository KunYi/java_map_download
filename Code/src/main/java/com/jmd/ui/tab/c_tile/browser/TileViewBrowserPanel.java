package com.jmd.ui.tab.c_tile.browser;

import com.jmd.ui.common.BrowserPanel;
import com.jmd.util.CommonUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serial;

@Component
public class TileViewBrowserPanel extends BrowserPanel {

    @Serial
    private static final long serialVersionUID = -6812120790251794674L;

    @Value("${setting.web.prod}")
    private boolean prod;
    
    public TileViewBrowserPanel() {
        super("BrowserPanel-TileView" + CommonUtils.generateCharMixed(32), "/tile-view");
    }

    @PostConstruct
    private void init() {
        super.baseInit(this.prod);
    }

    @PreDestroy
    protected void destroy() {
        super.baseDestroy();
    }

}
