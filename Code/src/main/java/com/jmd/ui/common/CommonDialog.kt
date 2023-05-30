package com.jmd.ui.common

import com.jmd.ApplicationStore
import java.util.*
import javax.swing.JOptionPane
import javax.swing.UIManager

object CommonDialog {

    @JvmStatic
    fun alert(title: String?, message: String?) {
        var title = title
        val pane = JOptionPane()
        pane.messageType = JOptionPane.INFORMATION_MESSAGE
        pane.message = message
        if (title == null) {
            title = UIManager.getString("OptionPane.messageDialogTitle", ApplicationStore.commonParentFrame.locale)
        }
        val dialog = pane.createDialog(ApplicationStore.commonParentFrame, title)
        val lx = ApplicationStore.MAIN_FRAME_LOCATION_X
        val ly = ApplicationStore.MAIN_FRAME_LOCATION_Y
        val mw = ApplicationStore.MAIN_FRAME_WIDTH
        val mh = ApplicationStore.MAIN_FRAME_HEIGHT
        val x = lx - dialog.size.getWidth().toInt() / 2 + mw / 2
        val y = ly - dialog.size.getHeight().toInt() / 2 + mh / 2
        dialog.setLocation(x, y)
        dialog.isVisible = true
        dialog.dispose()
    }

    @JvmStatic
    fun confirm(title: String?, message: String?): Boolean {
        var title = title
        val pane = JOptionPane()
        pane.messageType = JOptionPane.QUESTION_MESSAGE
        pane.message = message
        pane.optionType = JOptionPane.YES_NO_OPTION
        if (title == null) {
            title = UIManager.getString("OptionPane.messageDialogTitle", ApplicationStore.commonParentFrame.locale)
        }
        val dialog = pane.createDialog(ApplicationStore.commonParentFrame, title)
        pane.selectInitialValue()
        val lx = ApplicationStore.MAIN_FRAME_LOCATION_X
        val ly = ApplicationStore.MAIN_FRAME_LOCATION_Y
        val mw = ApplicationStore.MAIN_FRAME_WIDTH
        val mh = ApplicationStore.MAIN_FRAME_HEIGHT
        val x = lx - dialog.size.getWidth().toInt() / 2 + mw / 2
        val y = ly - dialog.size.getHeight().toInt() / 2 + mh / 2
        dialog.setLocation(x, y)
        dialog.isVisible = true
        dialog.dispose()
        val selectedValue = pane.value ?: return false
        return if (selectedValue is Int) {
            selectedValue == JOptionPane.YES_OPTION
        } else false
    }

    @JvmStatic
    fun option(title: String?, message: String?, options: Array<String>): Int? {
        var title = title
        val pane = JOptionPane()
        pane.messageType = JOptionPane.QUESTION_MESSAGE
        pane.message = message
        pane.options = Arrays.copyOf(options, options.size)
        if (title == null) {
            title = UIManager.getString("OptionPane.messageDialogTitle", ApplicationStore.commonParentFrame.locale)
        }
        val dialog = pane.createDialog(ApplicationStore.commonParentFrame, title)
        pane.selectInitialValue()
        val lx = ApplicationStore.MAIN_FRAME_LOCATION_X
        val ly = ApplicationStore.MAIN_FRAME_LOCATION_Y
        val mw = ApplicationStore.MAIN_FRAME_WIDTH
        val mh = ApplicationStore.MAIN_FRAME_HEIGHT
        val x = lx - dialog.size.getWidth().toInt() / 2 + mw / 2
        val y = ly - dialog.size.getHeight().toInt() / 2 + mh / 2
        dialog.setLocation(x, y)
        dialog.isVisible = true
        dialog.dispose()
        val selectedValue = pane.value ?: return null
        for (i in options.indices) {
            if (options[i] == selectedValue) {
                return i
            }
        }
        return null
    }

}