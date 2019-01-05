package com.servicesoapex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.servicesoapex.dao.ResultDAO;
import com.servicesoapex.mapper.MapperResultDAO;
import com.servicesoapex.mapperService.MapperService;
import com.servicesoapex.ourservice.Myresult;
import com.servicesoapex.util.MyServiceUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootAppTest {
	
	@Autowired
	MapperResultDAO mapper;
	
	@Autowired
	MapperService mapperService;
	
    @Autowired
	MyServiceUtil service;
	

	@Test
	public void insertIntoH2DB() {
		ResultDAO dao = new ResultDAO("hi", 100, "my spring", "test");
		mapper.insertResult(dao);
		System.out.println(dao);	
	}
	
	@Test
	public void getAllNotes() {
		ResultDAO dao = new ResultDAO("hi", 100, "my spring", "test");
		mapper.insertResult(dao);
		System.out.println(mapper.allResults());
	}
	
	@Test
	public void checkMyServiceUtil() {
		
		int number;
		
		number=100001;
		Myresult res = service.findNumber(number);
		ResultDAO resultDAO = new ResultDAO(res.getCode(), number, res.getFileNames(), res.getError());
		System.out.println(" Number: "+number+" is in: Files:"+res.getFileNames()+", Code: "+res.getCode()+", Error: "+res.getError());
		mapperService.insert(resultDAO);
		
		number=593;
		res = service.findNumber(number);
		resultDAO = new ResultDAO(res.getCode(), number, res.getFileNames(), res.getError());
		System.out.println(" Number: "+number+" is in: Files:"+res.getFileNames()+", Code: "+res.getCode()+", Error: "+res.getError());
		mapperService.insert(resultDAO);
		
		number=-100;
		res = service.findNumber(number);
		resultDAO = new ResultDAO(res.getCode(), number, res.getFileNames(), res.getError());
		System.out.println(" Number: "+number+" is in: Files:"+res.getFileNames()+", Code: "+res.getCode()+", Error: "+res.getError());
		mapperService.insert(resultDAO);
		
		number=1000000000;
		res = service.findNumber(number);
		resultDAO = new ResultDAO(res.getCode(), number, res.getFileNames(), res.getError());
		System.out.println(" Number: "+number+" is in: Files:"+res.getFileNames()+", Code: "+res.getCode()+", Error: "+res.getError());
		mapperService.insert(resultDAO);
		
		System.out.println(mapperService.selectAll());
	}
	
}
