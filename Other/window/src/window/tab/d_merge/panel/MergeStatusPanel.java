package window.tab.d_merge.panel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class MergeStatusPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1609822863496587388L;

    private JLabel currentContentLabel;
    private JLabel zoomsContentLabel;
    private JLabel imgTypeContentLabel;
    private JLabel savePathContentLabel;
    private JLabel pathStyleContentLabel;

	public MergeStatusPanel() {
		init();
	}

    private void init() {

        var gbl_this = new GridBagLayout();
        gbl_this.columnWidths = new int[]{0, 0, 0, 0};
        gbl_this.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_this.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_this.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        this.setLayout(gbl_this);

        /* 当前任务 */
        var currentTitleLabel = new JLabel("当前任务：");
        var gbc_currentTitleLabel = new GridBagConstraints();
        gbc_currentTitleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_currentTitleLabel.gridx = 0;
        gbc_currentTitleLabel.gridy = 0;
        this.add(currentTitleLabel, gbc_currentTitleLabel);

        this.currentContentLabel = new JLabel("");
        var gbc_currentContentLabel = new GridBagConstraints();
        gbc_currentContentLabel.anchor = GridBagConstraints.WEST;
        gbc_currentContentLabel.insets = new Insets(0, 0, 5, 0);
        gbc_currentContentLabel.gridx = 1;
        gbc_currentContentLabel.gridy = 0;
        this.add(this.currentContentLabel, gbc_currentContentLabel);
        /* 当前任务 */

        /* 所选图层 */
        var zoomsTitleLabel = new JLabel("所选层级：");
        var gbc_zoomsTitleLabel = new GridBagConstraints();
        gbc_zoomsTitleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_zoomsTitleLabel.gridx = 0;
        gbc_zoomsTitleLabel.gridy = 1;
        this.add(zoomsTitleLabel, gbc_zoomsTitleLabel);

        this.zoomsContentLabel = new JLabel("");
        var gbc_zoomsContentLabel = new GridBagConstraints();
        gbc_zoomsContentLabel.insets = new Insets(0, 0, 5, 0);
        gbc_zoomsContentLabel.anchor = GridBagConstraints.WEST;
        gbc_zoomsContentLabel.gridx = 1;
        gbc_zoomsContentLabel.gridy = 1;
        this.add(this.zoomsContentLabel, gbc_zoomsContentLabel);
        /* 所选图层 */

        /* 瓦片格式 */
        var imgTypeTitleLabel = new JLabel("瓦片格式：");
        var gbc_imgTypeTitleLabel = new GridBagConstraints();
        gbc_imgTypeTitleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_imgTypeTitleLabel.gridx = 0;
        gbc_imgTypeTitleLabel.gridy = 2;
        this.add(imgTypeTitleLabel, gbc_imgTypeTitleLabel);

        this.imgTypeContentLabel = new JLabel("");
        var gbc_imgTypeContentLabel = new GridBagConstraints();
        gbc_imgTypeContentLabel.anchor = GridBagConstraints.WEST;
        gbc_imgTypeContentLabel.insets = new Insets(0, 0, 5, 0);
        gbc_imgTypeContentLabel.gridx = 1;
        gbc_imgTypeContentLabel.gridy = 2;
        this.add(this.imgTypeContentLabel, gbc_imgTypeContentLabel);
        /* 瓦片格式 */

        /* 保存路径 */
        var savePathTitleLabel = new JLabel("保存路径：");
        var gbc_savePathTitleLabel = new GridBagConstraints();
        gbc_savePathTitleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_savePathTitleLabel.gridx = 0;
        gbc_savePathTitleLabel.gridy = 3;
        this.add(savePathTitleLabel, gbc_savePathTitleLabel);

        this.savePathContentLabel = new JLabel("");
        var gbc_savePathContentLabel = new GridBagConstraints();
        gbc_savePathContentLabel.anchor = GridBagConstraints.WEST;
        gbc_savePathContentLabel.insets = new Insets(0, 0, 5, 0);
        gbc_savePathContentLabel.gridx = 1;
        gbc_savePathContentLabel.gridy = 3;
        this.add(this.savePathContentLabel, gbc_savePathContentLabel);
        /* 保存路径 */

        /* 命名风格 */
        var pathStyleTitleLabel = new JLabel("命名风格：");
        var gbc_pathStyleTitleLabel = new GridBagConstraints();
        gbc_pathStyleTitleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_pathStyleTitleLabel.gridx = 0;
        gbc_pathStyleTitleLabel.gridy = 4;
        this.add(pathStyleTitleLabel, gbc_pathStyleTitleLabel);

        this.pathStyleContentLabel = new JLabel("");
        var gbc_pathStyleContentLabel = new GridBagConstraints();
        gbc_pathStyleContentLabel.anchor = GridBagConstraints.WEST;
        gbc_pathStyleContentLabel.insets = new Insets(0, 0, 5, 0);
        gbc_pathStyleContentLabel.gridx = 1;
        gbc_pathStyleContentLabel.gridy = 4;
        this.add(this.pathStyleContentLabel, gbc_pathStyleContentLabel);
        /* 命名风格 */

    }

}
