package com.jmd.web.controller;

import com.jmd.util.CommonUtils;
import com.jmd.web.service.TileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/tile")
public class TileController {

    @Autowired
    private TileService tileService;

    @RequestMapping(value = "/localPNG", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] localPNG(@RequestParam("z") int z, @RequestParam("x") int x, @RequestParam("y") int y) {
        return this.tileService.getTileImageByteLocal(z, x, y);
    }

    @RequestMapping(value = "/localJPG", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] localJPG(@RequestParam("z") int z, @RequestParam("x") int x, @RequestParam("y") int y) {
        return this.tileService.getTileImageByteLocal(z, x, y);
    }

    @RequestMapping(value = "/localWEGP", method = RequestMethod.GET, produces = "image/webp")
    @ResponseBody
    public byte[] localWEGP(@RequestParam("z") int z, @RequestParam("x") int x, @RequestParam("y") int y) {
        return this.tileService.getTileImageByteLocal(z, x, y);
    }

    @RequestMapping(value = "/proxyPNG", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getTilePNG(@RequestParam("z") int z, @RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("url") String url) {
        return this.tileService.getTileImageByteByProxy(z, x, y, url);
    }

    @RequestMapping(value = "/proxyJPG", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getTileJPEG(@RequestParam("z") int z, @RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("url") String url) {
        return this.tileService.getTileImageByteByProxy(z, x, y, url);
    }

}
