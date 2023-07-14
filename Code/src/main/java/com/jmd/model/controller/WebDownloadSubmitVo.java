package com.jmd.model.controller;

import java.util.List;

import lombok.Data;

@Data
public class WebDownloadSubmitVo {

	private String tileName;
	private String mapType;
	private List<String> tileUrl;
	private List<List<double[]>> points;
	private String oriImgType;

}
