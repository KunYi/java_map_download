package com.jmd.z0test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.jmd.db.dao.AreaDao;
import com.jmd.db.dao.CityDao;
import com.jmd.db.dao.DistrictDao;
import com.jmd.db.dao.ProvinceDao;
import com.jmd.model.district.Area;
import com.jmd.model.district.City;
import com.jmd.model.district.District;
import com.jmd.model.district.Province;
import com.jmd.model.district.WebAPIResult;
import com.jmd.http.HttpClient;

@Component
public class TestFunc {

    /**
     * 本方法用于通过高德地图API建立行政区表
     * SQLite表结构
     * (1)tb_province：     省级行政区及对应code
     * (2)tb_city：         市级行政区及对应code+父code
     * (3)tb_district：     区级行政区及对应code+父code
     * (4)tb_area：         每个行政区的经纬度坐标信息
     */

    private final String WEB_API_URL = "https://restapi.amap.com/v3/config/district?keywords={adcode}&subdistrict=0&extensions=all&key=84fe8781d6effe901187090b738a4f96";

    @Autowired
    private HttpClient http;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private DistrictDao districtDao;
    @Autowired
    private ProvinceDao provinceDao;

    public void run() {
        // 建立全国行政区信息
        // this.writeProvinceInfo();
        // 建立省级行政区坐标信息
        // this.writeProvinceArea();
        // 建立市级行政区坐标信息
        // this.writeCityArea();
        // 建立区级行政区坐标信息
        // this.writeDistrictArea();
    }

    // 建立全国行政区信息
    private void writeProvinceInfo() {
        var url = "https://restapi.amap.com/v3/config/district?keywords=100000&subdistrict=3&key=84fe8781d6effe901187090b738a4f96";
        var result = this.http.doGet(url);
        var jsonObj = JSON.parseObject(result);
        var webApiResults = JSON.parseArray(jsonObj.get("districts").toString(), WebAPIResult.class);
        // 省级
        var provinces = webApiResults.get(0).getDistricts();
        for (var province : provinces) {
            System.out.println(province);
            if (this.provinceDao.isExist(province.getAdcode()) == 0) {
                var p = new Province();
                p.setProvince(province.getName());
                p.setAdcode(province.getAdcode());
                this.provinceDao.insert(p);
            }
            // 市级
            var cities = province.getDistricts();
            for (var city : cities) {
                System.out.println(city);
                if (this.cityDao.isExist(city.getAdcode()) == 0) {
                    System.out.println("添加");
                    var c = new City();
                    c.setName(city.getName());
                    c.setCenter(city.getCenter());
                    c.setCitycode(city.getCitycode());
                    c.setAdcode(city.getAdcode());
                    c.setPadcode(province.getAdcode());
                    this.cityDao.insert(c);
                }
                // 区级
                var districts = city.getDistricts();
                for (var district : districts) {
                    System.out.println(district);
                    if (this.districtDao.isExist(district.getAdcode()) == 0) {
                        System.out.println("添加");
                        var d = new District();
                        d.setName(district.getName());
                        d.setCenter(district.getCenter());
                        d.setCitycode(district.getCitycode());
                        d.setAdcode(district.getAdcode());
                        d.setPadcode(city.getAdcode());
                        this.districtDao.insert(d);
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    // 建立省级行政区坐标信息
    private void writeProvinceArea() {
        var provinces = this.provinceDao.queryAll();
        for (var province : provinces) {
            if (this.areaDao.isExist(province.getAdcode()) == 0) {
                this.getAreaAndInsert(province.getAdcode());
            }
        }
        System.out.println("finish!");
    }

    // 建立市级行政区坐标信息
    private void writeCityArea() {
        var cities = this.cityDao.queryAll();
        for (var city : cities) {
            if (this.areaDao.isExist(city.getAdcode()) == 0) {
                this.getAreaAndInsert(city.getAdcode());
            }
        }
        System.out.println("finish!");
    }

    // 建立区级行政区坐标信息
    private void writeDistrictArea() {
        var districts = this.districtDao.queryAll();
        for (var district : districts) {
            if (this.areaDao.isExist(district.getAdcode()) == 0) {
                this.getAreaAndInsert(district.getAdcode());
            }
        }
        System.out.println("finish!");
    }

    private void getAreaAndInsert(String adcode) {
        System.out.println(adcode);
        var url = this.WEB_API_URL.replaceAll("\\{adcode\\}", adcode);
        var isFinish = false;
        var result = this.http.doGet(url);
        if (result != null) {
            try {
                var jsonObj = JSON.parseObject(result);
                var webApiResults = JSON.parseArray(jsonObj.get("districts").toString(), WebAPIResult.class);
                var line = webApiResults.get(0).getPolyline();
                var code = webApiResults.get(0).getAdcode();
                var area = new Area();
                area.setAdcode(code);
                area.setPolyline(line);
                this.areaDao.insert(area);
                isFinish = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!isFinish) {
            System.out.println(adcode + "重新查询");
            try {
                Thread.sleep(5000);
                this.getAreaAndInsert(adcode);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
