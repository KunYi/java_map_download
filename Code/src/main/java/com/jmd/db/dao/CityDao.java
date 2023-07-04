package com.jmd.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jmd.model.district.City;

@Mapper
public interface CityDao {

	int insert(City city);

	int isExist(String adcode);

	List<City> queryAll();

	List<City> queryByPadcode(String padcode);

}
