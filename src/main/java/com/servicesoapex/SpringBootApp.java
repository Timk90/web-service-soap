package com.servicesoapex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.servicesoapex.mapper")
public class SpringBootApp {

	//Для запуска и развертывания приложения использовался Spring-boot со стандартной конфигурацией 
	//В этом месте находится точка входа в приложение. 
	  public static void main(String[] args) {
	    SpringApplication.run(SpringBootApp.class, args);
	  }
	  
}
