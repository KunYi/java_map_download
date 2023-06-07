package com.jmd.ui.common

import com.jmd.ApplicationConfig
import com.jmd.browser.core.ChromiumEmbeddedCore
import com.jmd.common.StaticVar
import com.jmd.rx.Topic
import com.jmd.rx.client.InnerMqClient
import com.jmd.rx.service.InnerMqService
import org.cef.CefClient
import org.cef.browser.CefBrowser
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.io.Serial
import javax.swing.*

abstract class BrowserPanel(
    private val compId: String,
    private val path: String,
    private val prod: Boolean
) : JPanel() {

    companion object {
        @Serial
        private val serialVersionUID = 296955159808720054L
    }

    private val innerMqService = InnerMqService.getInstance()
    private var mq: InnerMqClient? = null
    private var client: CefClient? = null
    private var browser: CefBrowser? = null
    private val splitPane: JSplitPane
    private val framePanel: JPanel
    private val devToolPanel: JPanel
    private var devToolOpen = false

    init {

        this.layout = BorderLayout()
        this.isFocusable = true

        this.splitPane = JSplitPane()
        this.splitPane.border = null
        this.splitPane.dividerSize = 0
        this.splitPane.isOneTouchExpandable = false
        this.splitPane.isContinuousLayout = false
        this.splitPane.orientation = JSplitPane.HORIZONTAL_SPLIT
        this.add(splitPane, BorderLayout.CENTER)

        // 浏览器界面
        this.framePanel = JPanel()
        this.framePanel.layout = BorderLayout()
        this.splitPane.leftComponent = this.framePanel

        val label = JLabel("WebView初始化")
        label.horizontalAlignment = SwingConstants.CENTER
        label.font = StaticVar.FONT_SourceHanSansCNNormal_13
        this.framePanel.add(label, BorderLayout.CENTER)

        // 开发者工具
        this.devToolPanel = JPanel()
        this.devToolPanel.layout = BorderLayout()
        this.splitPane.rightComponent = null

    }

    private fun showBrowser() {
        val url = if (this.prod) {
            "http://localhost:${ApplicationConfig.startPort}/web/index.html/#${this.path}"
        } else {
            "http://localhost:4500/#${this.path}"
        }
        this.client = ChromiumEmbeddedCore.getInstance().createClient(this.compId)
        this.browser = ChromiumEmbeddedCore.getInstance().createBrowser(this.client, url)
        this.browser!!.setFocus(true)
        this.browser!!.uiComponent!!.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                browser!!.setFocus(true)
                innerMqService.pub(Topic.BROWSER_FOCUS, true)
            }
        })
        this.framePanel.removeAll()
        this.browser!!.uiComponent!!.let { this.framePanel.add(it, BorderLayout.CENTER) }
        this.framePanel.revalidate()
    }

    protected fun baseInit() {
        try {
            subInnerMqMessage()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun baseDestroy() {
        this.innerMqService.destroyClient(this.mq)
    }

    @Throws(Exception::class)
    private fun subInnerMqMessage() {
        this.mq = innerMqService.createClient(this.compId)
        this.mq!!.sub(Topic.APPLICATION_START_FINISH) { res: Any? -> SwingUtilities.invokeLater { showBrowser() } }
        this.mq!!.sub(Topic.OPEN_BROWSER_DEV_TOOL) { res: Any? -> SwingUtilities.invokeLater { toggleDevTools() } }
    }

    private fun toggleDevTools() {
        this.devToolOpen = if (this.devToolOpen) {
            closeDevTools()
            false
        } else {
            openDevTools()
            true
        }
    }

    private fun openDevTools() {
        this.splitPane.dividerSize = 5
        this.splitPane.rightComponent = this.devToolPanel
        this.splitPane.isContinuousLayout = true
        this.splitPane.dividerLocation = this.size.width - 500
        this.devToolPanel.add(this.browser!!.devTools.uiComponent, BorderLayout.CENTER)
        this.devToolPanel.revalidate()
        this.splitPane.revalidate()
    }

    private fun closeDevTools() {
        this.devToolPanel.removeAll()
        this.devToolPanel.revalidate()
        this.splitPane.remove(this.devToolPanel)
        this.splitPane.dividerSize = 0
        this.splitPane.rightComponent = null
        this.splitPane.isContinuousLayout = false
        this.splitPane.dividerLocation = this.size.width
        this.splitPane.revalidate()
    }

    fun reload() {
        this.browser!!.reload()
    }

    fun execJS(javaScript: String?) {
        this.browser!!.executeJavaScript(javaScript, null, 0)
    }

}