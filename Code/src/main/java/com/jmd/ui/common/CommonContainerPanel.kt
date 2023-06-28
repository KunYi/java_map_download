package com.jmd.ui.common

import com.jmd.common.StaticVar
import java.awt.BorderLayout
import java.awt.Component
import java.io.Serial
import javax.swing.JPanel
import javax.swing.border.TitledBorder

class CommonContainerPanel(title: String?) : JPanel() {

    companion object {
        @Serial
        private val serialVersionUID = 6628330531744102560L
    }

    init {
        if (title != null) {
            border = TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP,
                    StaticVar.FONT_SourceHanSansCNNormal_12, null)
        }
        this.layout = BorderLayout()
    }

    fun addContent(comp: Component) {
        super.add(comp, BorderLayout.CENTER)
    }

}