package window.common;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class CommonContainerPanel extends JPanel {

	public CommonContainerPanel(String title) {
		if (title != null) {
			this.setBorder(new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		}
		this.setLayout(new BorderLayout());
	}

	public void addContent(Component comp) {
        super.add(comp, BorderLayout.CENTER);
    }

}
