package com.jmd.web.service.impl;

import com.jmd.http.HttpClient;
import com.jmd.model.tile.TileViewParam;
import com.jmd.rx.Topic;
import com.jmd.rx.client.InnerMqClient;
import com.jmd.rx.service.InnerMqService;
import com.jmd.util.CommonUtils;
import com.jmd.util.MyFileUtils;
import com.jmd.util.TaskUtils;
import com.jmd.web.service.TileService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TileServiceImpl implements TileService {

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private InnerMqClient client;

    @Autowired
    private HttpClient http;

    private TileViewParam tileViewParam;

    @PostConstruct
    private void init() {
        try {
            this.subInnerMqMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    protected void destroy() {
        this.innerMqService.destroyClient(this.client);
    }

    private void subInnerMqMessage() throws Exception {
        this.client = innerMqService.createClient();
        this.client.<TileViewParam>sub(Topic.OPEN_TILE_VIEW, (res) -> this.tileViewParam = res);
    }

    @Override
    public TileImageResult getTileImageByteLocal(int z, int x, int y) {
        var result = new TileImageResult();
        if (this.tileViewParam != null && this.tileViewParam.check()) {
            var filePath = this.tileViewParam.getPath() +
                    TaskUtils.getFilePathNameNoSuffix(this.tileViewParam.getPathStyle(), z, x, y) +
                    "." + tileViewParam.getType().toLowerCase();
            filePath = MyFileUtils.checkFilePath(filePath);
            result.setType(tileViewParam.getType());
            try {
                result.setData(MyFileUtils.getFileBytes(filePath));
            } catch (Exception ignored) {
            }
        }
        return result;
    }

    @Override
    public byte[] getTileImageByteByProxy(int z, int x, int y, String url) {
        try {
            var tileUrl = CommonUtils.getDialectUrl("", url, z, x, y);
            return this.http.getFileBytes(tileUrl, HttpClient.HEADERS);
        } catch (Exception e) {
            return new byte[0];
        }
    }

}
