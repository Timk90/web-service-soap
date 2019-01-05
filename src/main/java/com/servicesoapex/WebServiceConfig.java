package com.servicesoapex;

import org.springframework.boot.web.servlet.ServletRegistrationBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/*
 * Файл с конфигурацией Веб-сервиса. Конфигурация стандартная. 
 * Единственное место, которое было изменено - название сервиса и указана схема.
 * Весь код в пакете com.servicesoapex.ourservice был сгенерирован автоматически на 
 * основании этой схемы средствами Spring-boot.   
 */

@EnableWs
@Configuration
public class WebServiceConfig {

  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
    MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
    messageDispatcherServlet.setApplicationContext(context);
    messageDispatcherServlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
  }
  
  @Bean(name = "ourservice")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema mySchema) {
    DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
    definition.setPortTypeName("ServicePort");
    definition.setTargetNamespace("http://www.servicesoapex.com/ourservice");
    definition.setLocationUri("/ws");
    definition.setSchema(mySchema);
    return definition;
  }

  @Bean
  public XsdSchema mySchema() {
    return new SimpleXsdSchema(new ClassPathResource("MyService.xsd"));
  }
  
}
