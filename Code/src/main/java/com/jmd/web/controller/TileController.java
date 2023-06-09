package com.jmd.web.controller;

import com.jmd.util.ImageUtils;
import com.jmd.web.service.TileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/tile")
public class TileController {

    @Autowired
    private TileService tileService;

    @RequestMapping(value = "/local", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> local(@RequestParam("z") int z, @RequestParam("x") int x, @RequestParam("y") int y) {
        var headers = new HttpHeaders();
        var bytes = this.tileService.getTileImageByteLocal(z, x, y);
        headers.add(HttpHeaders.CONTENT_TYPE, this.getContentType(bytes));
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/proxy", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> proxy(@RequestParam("z") int z, @RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("url") String url) {
        var headers = new HttpHeaders();
        var bytes = this.tileService.getTileImageByteByProxy(z, x, y, url);
        headers.add(HttpHeaders.CONTENT_TYPE, this.getContentType(bytes));
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private String getContentType(byte[] imageBytes) {
        var type = ImageUtils.getImageType(imageBytes);
        if (type == null) {
            return null;
        }
        switch (type) {
            case PNG -> {
                return MediaType.IMAGE_PNG_VALUE;
            }
            case JPG -> {
                return MediaType.IMAGE_JPEG_VALUE;
            }
            case GIF -> {
                return MediaType.IMAGE_GIF_VALUE;
            }
            case WEBP -> {
                return "image/webp";
            }
            default -> {
                return null;
            }
        }
    }

}
