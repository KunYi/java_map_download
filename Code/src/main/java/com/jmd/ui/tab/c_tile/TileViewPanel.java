package com.jmd.ui.tab.c_tile;

import com.jmd.ui.common.CommonContainerPanel;
import com.jmd.ui.tab.c_tile.browser.TileViewBrowserPanel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.Serial;

@Component
public class TileViewPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = -3295266893592422327L;

    @Autowired
    private TileViewBrowserPanel browserInstPanel;

    private final CommonContainerPanel browserPanel;

    public TileViewPanel() {

        this.browserPanel = new CommonContainerPanel("浏览器");

    }

    @PostConstruct
    private void init() {

        /* 浏览器 */
        this.browserPanel.addContent(this.browserInstPanel);
        /* 浏览器 */

    }

}
