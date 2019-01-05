package com.servicesoapex.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.servicesoapex.dao.ResultDAO;
import com.servicesoapex.ourservice.Myresult;

/*
 * Для работы с БД использовался маппер на основе MyBatis. 
 * Здесь описаны два метода для работы с данными. Полный список CRUD не был реализован 
 * в виду отсутствия необходимости в рамках тестового задания. 
 */

@Mapper
public interface MapperResultDAO {
	
	@Insert("INSERT INTO results (code, number, filenames, error) values (#{code}, #{number}, #{filenames}, #{error})")
	public void insertResult(ResultDAO result);
	
	@Select("SELECT * FROM results")
	public List<ResultDAO> allResults();
	
}
