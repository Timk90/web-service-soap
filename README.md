# web-service-soap
web-service + soap (Spring-boot, H2 db, SOAP (ws), MyBatis)

---Short Description---
========================

An app allows to search int number in N files composed of 
int numbers splitted by ","(comma). 
As an interface it is using web-service with SOAP. 

---------------------------

Instructions:
- in order to test this web-service you have to generate all the txt files strating method main() in /src/servicesoapex/util/FileGenerator.class
- the next step is to add all the libraries to classpath using maven building. Start goals "clean install"
- the app is ready to be launched. In order to start it call the main() in src/com/servicesoapex/SpringBootApp.class
- now you can use the web-service with, for example,  Wizdler on GoogleChrome. Open it in browser on page: http://localhost:8080/ourservice.wsdl (look at scheme)

------------------------------------------------

Initially the app was created using Eclipse IDE 

Don't hesitate to ask if any questions all suggestions. 

Enjoy

all the best.

Timk90

