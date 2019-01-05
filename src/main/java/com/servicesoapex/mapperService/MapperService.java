package com.servicesoapex.mapperService;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.servicesoapex.dao.ResultDAO;
import com.servicesoapex.mapper.MapperResultDAO;
import com.servicesoapex.util.MyServiceUtil;

/*
 * Работа с БД осуществляется через сервис, а не непосредственно через маппер.
 * Это позволяется добавлять проверки и использовать разные мапперы при необходимости. 
 * В частности, здесь добавлена проверка кода результата. Если поиск числа завершился с кодом ОК, 
 * то данные заносятся в БД, в противном случае ничего не происходит. 
 */

@Service
public class MapperService {
	
	final static Logger logger = Logger.getLogger(MapperService.class);
	
	@Autowired
	MapperResultDAO mapper;
	
	//два метода обертки для маппера
	public void insert(ResultDAO result) {
		//Проверка кода результата, если записи найдены, то запись попадает в БД. 
		if(result.getCode().equals("00.Result.OK")) {
		mapper.insertResult(result);
		logger.info("Note has been added to DB "+ result);
		}
	}
	
	public List<ResultDAO> selectAll(){
		return mapper.allResults();
	}
	
}
