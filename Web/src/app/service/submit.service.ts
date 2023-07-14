import { Injectable } from '@angular/core';
import { HttpClientService } from '../http/http-client.service';
import { HttpMethod } from '../http/http-method';
import { RESTfulResult } from '../http/restful-result.type';

@Injectable()
export class SubmitService {

	constructor(private http: HttpClientService) {
	}

	/** 提交区域下载 */
	public blockDownload(data: {
		tileName: string,
		mapType: string,
		tileUrl: Array<string>,
		points: number[][][],
		oriImgType: string,
	}): Promise<RESTfulResult> {
		return this.http.request({
			method: HttpMethod.POST,
			url: '/submit/blockDownload',
			data: data
		})
	}

	/** 世界下载 */
	public worldDownload(data: {
		tileName: string,
		mapType: string,
		tileUrl: Array<string>,
		oriImgType: string,
	}): Promise<RESTfulResult> {
		return this.http.request({
			method: HttpMethod.POST,
			url: '/submit/worldDownload',
			data: data
		})
	}

}
