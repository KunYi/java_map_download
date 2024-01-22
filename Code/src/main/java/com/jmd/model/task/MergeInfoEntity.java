package com.jmd.model.task;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class MergeInfoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6937782459062045965L;

    private List<Integer> zoomList; // 所选层级
    private Integer imgType; // 瓦片格式
    private String savePath; // 保存路径
    private String pathStyle; // 命名风格

    private TaskAllInfoEntity taskAllInfo; // 任务实例

}
