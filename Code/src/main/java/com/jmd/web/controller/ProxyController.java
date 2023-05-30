package com.jmd.web.controller;

import com.jmd.http.HttpClient;
import com.jmd.util.CommonUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/proxy")
public class ProxyController {

    @Autowired
    private HttpClient http;

    @RequestMapping(value = "/getTilePNG", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getTilePNG(HttpServletResponse response,
                             @RequestParam("z") int z,
                             @RequestParam("x") int x,
                             @RequestParam("y") int y,
                             @RequestParam("url") String url) throws IOException {
        String tileUrl = CommonUtils.getDialectUrl("", url, z, x, y);
        return http.getFileBytes(tileUrl, HttpClient.HEADERS);
    }

    @RequestMapping(value = "/getTileJPEG", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getTileJPEG(HttpServletResponse response,
                              @RequestParam("z") int z,
                              @RequestParam("x") int x,
                              @RequestParam("y") int y,
                              @RequestParam("url") String url) throws IOException {
        String tileUrl = CommonUtils.getDialectUrl("", url, z, x, y);
        return http.getFileBytes(tileUrl, HttpClient.HEADERS);
    }

}
