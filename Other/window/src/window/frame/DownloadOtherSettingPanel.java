package window.frame;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DownloadOtherSettingPanel extends JPanel {

    private final JComboBox<String> imgTypeComboBox;
    private final JComboBox<String> pathStyleDefaultComboBox;
    private final JCheckBox isCoverCheckBox;
    private final JCheckBox mergeFileSaveCheckBox;
    private final JCheckBox mergeTileCheckBox;
    private final JLabel mergeTypeLabel;
    private final JComboBox<String> mergeTypeComboBox;
    private final JTextArea mergeTipTextArea;
    
    public DownloadOtherSettingPanel() {

        var imgTypeLabel = new JLabel("图片格式：");

        this.imgTypeComboBox = new JComboBox<>();
        this.imgTypeComboBox.setFocusable(false);
        this.imgTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"PNG", "WEBP", "JPG-低", "JPG-中", "JPG-高"}));
        this.imgTypeComboBox.setSelectedIndex(0);

        var pathStyleLabel = new JLabel("命名风格：");

        var pathStyleRadioButton1 = new JRadioButton("预设");
        pathStyleRadioButton1.setFocusable(false);
        pathStyleRadioButton1.setSelected(true);

        var pathStyleBtnGroup = new ButtonGroup();
        pathStyleBtnGroup.add(pathStyleRadioButton1);

        this.pathStyleDefaultComboBox = new JComboBox<>();
        this.pathStyleDefaultComboBox.setFocusable(false);
        this.pathStyleDefaultComboBox.setSelectedIndex(1);

        this.isCoverCheckBox = new JCheckBox("覆盖已下载的瓦片");
        this.isCoverCheckBox.setSelected(true);
        this.isCoverCheckBox.setFocusable(false);

        this.mergeFileSaveCheckBox = new JCheckBox("保存瓦片合并配置");
        this.mergeFileSaveCheckBox.setFocusable(false);
        this.mergeFileSaveCheckBox.setSelected(true);

        this.mergeTileCheckBox = new JCheckBox("下载完成后合并瓦片");
        this.mergeTileCheckBox.setFocusable(false);

        this.mergeTypeLabel = new JLabel("合并格式：");
        this.mergeTypeLabel.setVisible(false);

        this.mergeTypeComboBox = new JComboBox<>();
        this.mergeTypeComboBox.setVisible(false);
        this.mergeTypeComboBox.setFocusable(false);
        this.mergeTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"自动", "WEBP", "JPG", "PNG"}));
        this.mergeTypeComboBox.setSelectedIndex(0);

        this.mergeTipTextArea = new JTextArea("" +
                "基于已下载的瓦片进行拼接合并，" +
                "WEBP与PNG可保留透明度，JPG则使用黑色代替透明位置，但会获得更小的体积。\n" +
                "自动：默认导出为WEBP，超过16383*16383导出为JPG，超过65535*65535导出为PNG。" +
                "");
        this.mergeTipTextArea.setVisible(false);
        this.mergeTipTextArea.setLineWrap(true);
        this.mergeTipTextArea.setEditable(false);
        this.mergeTipTextArea.setFocusable(false);

        var groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(imgTypeLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.imgTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(pathStyleLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(pathStyleRadioButton1))
                                        .addComponent(this.pathStyleDefaultComboBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.isCoverCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.mergeFileSaveCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addComponent(this.mergeTileCheckBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(this.mergeTypeLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.mergeTypeComboBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(this.mergeTipTextArea, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(imgTypeLabel)
                                        .addComponent(this.imgTypeComboBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(pathStyleLabel)
                                        .addComponent(pathStyleRadioButton1))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(this.pathStyleDefaultComboBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(this.isCoverCheckBox)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(this.mergeFileSaveCheckBox)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(this.mergeTileCheckBox)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.mergeTypeLabel)
                                        .addComponent(this.mergeTypeComboBox, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(this.mergeTipTextArea, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        this.setLayout(groupLayout);

    }
    
}
