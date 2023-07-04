package com.jmd.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jmd.model.district.Province;

@Mapper
public interface ProvinceDao {

	int insert(Province province);

	int isExist(String adcode);

	List<Province> queryAll();

}
