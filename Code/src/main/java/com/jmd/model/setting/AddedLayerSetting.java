package com.jmd.model.setting;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AddedLayerSetting implements Serializable {

    @Serial
    private static final long serialVersionUID = -7228487492627563507L;

    private String name;
    private String url;
    private String type;
    private String oriImgType;
    private Boolean proxy;

}
