package com.jmd.http;

import java.io.IOException;

import com.jmd.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jmd.model.result.DownloadResult;
import com.jmd.inst.DownloadAmountInstance;

@Component
public class HttpDownload {

    @Autowired
    private HttpClient http;
    @Autowired
    private DownloadAmountInstance downloadAmountInstance;

    // 通过URL下载文件
    public DownloadResult downloadTile(String url, int imgType, String oriImgType, String pathAndName, int retry) {
        var result = new DownloadResult();
        var success = false;
        var bytes = http.getFileBytes(url, HttpClient.HEADERS);
        if (bytes != null) {
            try {
                var fileLength = this.saveImage(imgType, oriImgType, bytes, pathAndName);
                if (fileLength >= 0) {
                    this.downloadAmountInstance.add(fileLength);
                    success = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (success) {
            result.setSuccess(true);
        } else if (Thread.currentThread().isInterrupted()) {
            result.setSuccess(false);
        } else {
            retry = retry - 1;
            if (retry >= 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return downloadTile(url, imgType, oriImgType, pathAndName, retry);
            } else {
                result.setSuccess(false);
            }
        }
        return result;
    }

    // 转码并保存图片
    private long saveImage(int imgType, String oriImgType, byte[] imgData, String pathAndName) throws IOException {
        if (imgType == 0) {
            return ImageUtils.saveImageDirectly(imgData, pathAndName + "." + oriImgType.toLowerCase());
        }
        if (imgType == 1) {
            if ("PNG".equals(oriImgType)) {
                return ImageUtils.saveImageDirectly(imgData, pathAndName + ".png");
            } else {
                return ImageUtils.saveImageByOpenCV(imgData, pathAndName + ".png");
            }
        }
        if (imgType == 2) {
            if ("WEBP".equals(oriImgType)) {
                return ImageUtils.saveImageDirectly(imgData, pathAndName + ".webp");
            } else {
                return ImageUtils.saveImageByOpenCV(imgData, pathAndName + ".webp");
            }
        }
        if (imgType == 3) {
            if ("TIFF".equals(oriImgType)) {
                return ImageUtils.saveImageDirectly(imgData, pathAndName + ".tiff");
            } else {
                return ImageUtils.saveImageByOpenCV(imgData, pathAndName + ".tiff");
            }
        }
        if (imgType == 4 || imgType == 5 || imgType == 6) {
            return switch (imgType) {
                case 4 -> ImageUtils.saveImageToJPG(imgData, 0.2f, pathAndName + ".jpg");
                case 5 -> ImageUtils.saveImageToJPG(imgData, 0.6f, pathAndName + ".jpg");
                // 6
                default -> ImageUtils.saveImageToJPG(imgData, 0.9f, pathAndName + ".jpg");
            };
        }
        return ImageUtils.saveImageByOpenCV(imgData, pathAndName + ".png");
    }

}
