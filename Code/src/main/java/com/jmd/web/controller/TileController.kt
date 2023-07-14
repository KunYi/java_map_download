package com.jmd.web.controller

import com.jmd.util.ImageUtils
import com.jmd.web.service.TileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/tile")
class TileController {

    @Autowired
    private val tileService: TileService? = null

    @RequestMapping(value = ["/local"], method = [RequestMethod.GET])
    @ResponseBody
    fun local(@RequestParam("z") z: Int, @RequestParam("x") x: Int, @RequestParam("y") y: Int): ResponseEntity<Any> {
        val headers = HttpHeaders()
        val result = tileService!!.getTileImageByteLocal(z, x, y)
        headers.add(HttpHeaders.CONTENT_TYPE, getContentType(result.type))
        return ResponseEntity(result.data, headers, HttpStatus.OK)
    }

    @RequestMapping(value = ["/proxy"], method = [RequestMethod.GET])
    @ResponseBody
    fun proxy(
        @RequestParam("z") z: Int,
        @RequestParam("x") x: Int,
        @RequestParam("y") y: Int,
        @RequestParam("type") type: String,
        @RequestParam("url") url: String
    ): ResponseEntity<Any> {
        val headers = HttpHeaders()
        val bytes = tileService!!.getTileImageByteByProxy(z, x, y, url)
        headers.add(HttpHeaders.CONTENT_TYPE, getContentType(type))
        return ResponseEntity(bytes, headers, HttpStatus.OK)
    }

    private fun getContentType(type: String): String? {
        return when (type) {
            "PNG" -> {
                MediaType.IMAGE_PNG_VALUE
            }

            "JPG" -> {
                MediaType.IMAGE_JPEG_VALUE
            }

            "WEBP" -> {
                "image/webp"
            }

            else -> {
                MediaType.IMAGE_PNG_VALUE
            }
        }
    }
}