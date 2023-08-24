package window.frame;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class AddLayerFrame extends JFrame {

	private final JTextField nameInputTextField;
	private final JRadioButton typeWgs84RadioButton;
	private final JRadioButton typeGcj02RadioButton;
	private final JRadioButton pngRadioButton;
	private final JRadioButton webpRadioButton;
	private final JRadioButton tiffRadioButton;
	private final JRadioButton jpgRadioButton;
	private final JRadioButton proxyCloseRadioButton;
	private final JRadioButton proxyOpenRadioButton;
	private final JTextArea urlInputTextArea;
	private final JButton okButton;

	public AddLayerFrame() {

		var nameInputLabel = new JLabel("标题：");

		this.nameInputTextField = new JTextField();

		var typeSelectLabel = new JLabel("坐标类型：");

		this.typeWgs84RadioButton = new JRadioButton("wgs84");
		this.typeWgs84RadioButton.setSelected(true);

		this.typeGcj02RadioButton = new JRadioButton("gcj02");
		this.typeGcj02RadioButton.setSelected(false);

		var btnGroup1 = new ButtonGroup();
		btnGroup1.add(this.typeWgs84RadioButton);
		btnGroup1.add(this.typeGcj02RadioButton);

		var oriImgTypeSelectLabel = new JLabel("图片类型：");

		this.pngRadioButton = new JRadioButton("png");
		this.pngRadioButton.setSelected(true);

		this.webpRadioButton = new JRadioButton("webp");
		this.webpRadioButton.setSelected(false);

		this.tiffRadioButton = new JRadioButton("tiff");
		this.tiffRadioButton.setSelected(false);

		this.jpgRadioButton = new JRadioButton("jpg");
		this.jpgRadioButton.setSelected(false);

		var proxyLabel = new JLabel("瓦片资源访问方式：");

		this.proxyCloseRadioButton = new JRadioButton("内嵌浏览器页面直接访问");
		this.proxyCloseRadioButton.setSelected(true);

		this.proxyOpenRadioButton = new JRadioButton("通过本程序okhttp代理访问（解决瓦片跨域问题）");
		this.proxyOpenRadioButton.setSelected(false);

		var btnGroup2 = new ButtonGroup();
		btnGroup2.add(this.proxyCloseRadioButton);
		btnGroup2.add(this.proxyOpenRadioButton);

		var urlInputLabel = new JLabel("地址：（请严格按照{x}{y}{z}的格式填写地址）");

		var urlInputScrollPane = new JScrollPane();
		this.urlInputTextArea = new JTextArea();
		this.urlInputTextArea.setLineWrap(true);
		urlInputScrollPane.setViewportView(this.urlInputTextArea);

		this.okButton = new JButton("确定");
		this.okButton.setFocusable(false);

		var groupLayout = new GroupLayout(this.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(nameInputLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(nameInputTextField, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(typeSelectLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addGroup(Alignment.LEADING,
												groupLayout.createSequentialGroup()
														.addComponent(typeWgs84RadioButton, GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(typeGcj02RadioButton, GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(oriImgTypeSelectLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addGroup(Alignment.LEADING,
												groupLayout.createSequentialGroup()
														.addComponent(pngRadioButton, GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(webpRadioButton, GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(tiffRadioButton, GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(jpgRadioButton, GroupLayout.PREFERRED_SIZE,
																GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(proxyLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(proxyCloseRadioButton, Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(proxyOpenRadioButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(urlInputLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(urlInputScrollPane, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
										.addComponent(okButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addContainerGap()
				.addComponent(nameInputLabel).addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(nameInputTextField, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(typeSelectLabel)
				.addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(typeWgs84RadioButton)
						.addComponent(typeGcj02RadioButton))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(oriImgTypeSelectLabel)
				.addPreferredGap(ComponentPlacement.RELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(pngRadioButton)
						.addComponent(webpRadioButton)
						.addComponent(tiffRadioButton)
						.addComponent(jpgRadioButton))
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(proxyLabel)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(proxyCloseRadioButton)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(proxyOpenRadioButton)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(urlInputLabel)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(urlInputScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED).addComponent(okButton).addContainerGap()));
		this.getContentPane().setLayout(groupLayout);

		this.setTitle("添加自定义图层");
		this.setSize(new Dimension(400, 400));
		this.setVisible(false);
		this.setResizable(false);

	}
}
