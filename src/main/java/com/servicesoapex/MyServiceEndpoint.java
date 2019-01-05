package com.servicesoapex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.servicesoapex.dao.ResultDAO;
import com.servicesoapex.mapperService.MapperService;
import com.servicesoapex.ourservice.Myresult;
import com.servicesoapex.ourservice.ServiceRequest;
import com.servicesoapex.ourservice.ServiceResponse;
import com.servicesoapex.util.MyServiceUtil;

/*
 * Основной класс веб-сервиса. Указывает точку входа в сервис "http://www.servicesoapex.com/ourservice" 
 * и исполняет вызов методов при обращении к сервису - response, request. 
 */
@Endpoint
public class MyServiceEndpoint {
	 
	 @Autowired
	 MapperService mapperservice;
	 @Autowired
	 MyServiceUtil service;
	 
	  @PayloadRoot(namespace = "http://www.servicesoapex.com/ourservice", localPart = "ServiceRequest")
	  @ResponsePayload
	  public ServiceResponse processCourseDetailsRequest(@RequestPayload ServiceRequest request) {
		
		ServiceResponse response = new ServiceResponse();
		
		//вызов главного метода приложения, который осуществлялет поиск указанного числа и формирует результат этого поиска
	    Myresult res = service.findNumber(request.getNumber()); 
	    //поскольку структура результата отличается от структуры записи в БД для маппера пришлось создать расширенный результат с новой
	    //структурой, который содержит в себе дополнительно ИСКОМОЕ ЧИСЛО. Поэтому в этом месте программы создается объект ResultDAO, который 
	    //помощью маппера будет записан в БД.  
		ResultDAO resultDAO = new ResultDAO(res.getCode(), request.getNumber(), res.getFileNames(), res.getError());
		//Запись осуществляется, через сервис, а не напрямую через маппер, т.к. в сервисе осуществляется проверка кода.  Если он сответствует 
		//ОК, то запись будет добавлена в БД, в противном случае - нет. (Использовал встроенную БД H2 сконфигурированну Spring-boot)
	    mapperservice.insert(resultDAO);
	    //результат поиска числа возвращается в виде ответа в исполняющую среду. 
	    response.setMyresult(res);
	    return response;
	  }
}
