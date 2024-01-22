package com.jmd.task;

import com.jmd.async.pool.executor.TileMergeExecutorPool;
import com.jmd.async.pool.scheduler.IntervalConfig;
import com.jmd.async.task.scheduler.TileMergeMonitoringInterval;
import com.jmd.callback.CommonCallback;
import com.jmd.model.task.MergeInfoEntity;
import com.jmd.model.task.TaskAllInfoEntity;
import com.jmd.rx.Topic;
import com.jmd.rx.service.InnerMqService;
import com.jmd.util.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Random;

@Slf4j
@Component
public class SingleMergeExecFunc {

    private final InnerMqService innerMqService = InnerMqService.getInstance();

    @Autowired
    private TaskStepFunc taskStep;
    @Autowired
    private TileMergeExecutorPool tileMergeExecutorPool;

    private final DecimalFormat df2 = new DecimalFormat("#.##");

    public void start(MergeInfoEntity mergeInfo, CommonCallback callback) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                // 显示任务信息
                innerMqService.pub(Topic.MERGE_STATUS_LAYERS, mergeInfo.getZoomList().toString());
                innerMqService.pub(Topic.MERGE_STATUS_SAVE_PATH, mergeInfo.getSavePath());
                innerMqService.pub(Topic.MERGE_STATUS_PATH_STYLE, mergeInfo.getPathStyle());
                innerMqService.pub(Topic.MERGE_STATUS_IMG_TYPE, ImageUtils.getImageTypeName(mergeInfo.getImgType()));
                // 开始任务
                TaskState.IS_MERGING = true;
                var taskAllInfo = mergeInfo.getTaskAllInfo();
                for (var inst : taskAllInfo.getEachLayerTask().values()) {
                    eachLayerTileMerge(taskAllInfo, inst.getZ());
                }
                TaskState.IS_MERGING = false;
                callback.execute();
                return null;
            }
        };
        worker.execute();
    }

    // 各层级合并图片
    private void eachLayerTileMerge(TaskAllInfoEntity taskAllInfo, int z) {
        if (z == 0) {
            return;
        }
        int id = new Random().nextInt(1000000000);
        innerMqService.pub(Topic.MERGE_STATUS_CURRENT, "正在合成第" + z + "级地图");
        // 声明Mat
        var mat = new TileMergeMatWrap();
        // 合并进度监视定时器
        innerMqService.pub(Topic.SET_INTERVAL, new IntervalConfig(id, new TileMergeMonitoringInterval(mat, (progress) -> {
            innerMqService.pub(Topic.SINGLE_MERGE_PROCESS_PIXEL_COUNT, progress.getRunPixel() + "/" + progress.getAllPixel());
            innerMqService.pub(Topic.SINGLE_MERGE_PROCESS_THREAD, String.valueOf(tileMergeExecutorPool.getActiveCount()));
            innerMqService.pub(Topic.SINGLE_MERGE_PROCESS_PROGRESS, df2.format(progress.getPerc() * 100) + "%");
        }), 100L));
        // 开始合并
        taskStep.mergeTileImage(mat, taskAllInfo, z, () -> {
            innerMqService.pub(Topic.SINGLE_MERGE_PROCESS_PIXEL_COUNT, mat.getAllPixel() + "/" + mat.getAllPixel());
            innerMqService.pub(Topic.SINGLE_MERGE_PROCESS_THREAD, "0");
            innerMqService.pub(Topic.SINGLE_MERGE_PROCESS_PROGRESS, "100.00%");
            // 结束合并进度监视定时器
            innerMqService.pub(Topic.CLEAR_INTERVAL, id);
        });
    }

}
