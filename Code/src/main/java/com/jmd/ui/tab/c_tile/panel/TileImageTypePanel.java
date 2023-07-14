package com.jmd.ui.tab.c_tile.panel;

import com.jmd.common.StaticVar;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.Serial;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

@Component
public class TileImageTypePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = -3412842231250390141L;

    private final JRadioButton pngRadioButton;
    private final JRadioButton webpRadioButton;
    private final JRadioButton tiffRadioButton;
    private final JRadioButton jpgRadioButton;

    private String type = "png";

    public TileImageTypePanel() {

        this.pngRadioButton = new JRadioButton("PNG");
        this.pngRadioButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.pngRadioButton.setFocusable(false);
        this.pngRadioButton.setSelected(true);

        this.webpRadioButton = new JRadioButton("WEBP");
        this.webpRadioButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.webpRadioButton.setFocusable(false);

        this.tiffRadioButton = new JRadioButton("TIFF");
        this.tiffRadioButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.tiffRadioButton.setFocusable(false);

        this.jpgRadioButton = new JRadioButton("JPG");
        this.jpgRadioButton.setFont(StaticVar.FONT_SourceHanSansCNNormal_13);
        this.jpgRadioButton.setFocusable(false);

        var btnGroup = new ButtonGroup();
        btnGroup.add(this.pngRadioButton);
        btnGroup.add(this.webpRadioButton);
        btnGroup.add(this.tiffRadioButton);
        btnGroup.add(this.jpgRadioButton);

        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(this.pngRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(this.webpRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(this.jpgRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap(9, 9)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.pngRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(this.webpRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(this.jpgRadioButton, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        setLayout(groupLayout);

    }

    @PostConstruct
    private void init() {
        this.pngRadioButton.addItemListener((e) -> {
            if (this.pngRadioButton == e.getSource() && this.pngRadioButton.isSelected()) {
                this.type = "PNG";
            }
        });
        this.webpRadioButton.addItemListener((e) -> {
            if (this.webpRadioButton == e.getSource() && this.webpRadioButton.isSelected()) {
                this.type = "WEBP";
            }
        });
        this.tiffRadioButton.addItemListener((e) -> {
            if (this.tiffRadioButton == e.getSource() && this.tiffRadioButton.isSelected()) {
                this.type = "TIFF";
            }
        });
        this.jpgRadioButton.addItemListener((e) -> {
            if (this.jpgRadioButton == e.getSource() && this.jpgRadioButton.isSelected()) {
                this.type = "JPG";
            }
        });
    }

    public String getImageType() {
        return this.type;
    }

}
