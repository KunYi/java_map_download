package com.jmd.web.service;

import lombok.Data;

public interface TileService {

    TileImageResult getTileImageByteLocal(int z, int x, int y);

    byte[] getTileImageByteByProxy(int z, int x, int y, String url);

    @Data
    public static class TileImageResult {
        private byte[] data = new byte[0];
        private String type;
    }

}
