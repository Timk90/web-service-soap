package com.servicesoapex.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.servicesoapex.ourservice.Myresult;

/*
 * Вспомогательный класс, который вызывает поток монитор (MonitorSearchThread) и ждет завершения его работы
 * Здесь же указываются файлы в которых будет осуществляться поиск числа, а также реализован метод
 * требуемый в тестовом задании - findNumber(int number) - в том виде, в котором он требуется.
 *  Фактически этот метод является просто оберткой метода потока монитора, который 
 * реализует непосредственный поиск числа в файлах посредством вспомогательных поисковых потоков. 
 */

@Component
public class MyServiceUtil {
	
	final static Logger logger = Logger.getLogger(MyServiceUtil.class);
	
	//Требуемый метод в который передается число на поиск. 
	//Файлы в которых поиск осуществляется указаны непосредственно 
	//внутри этого метода. 
	public static Myresult findNumber(int number) {
		
			//результат поиск записывается в объект Myresult, который
			//содержит поля с именами файлов, кодом исполнения и ошибкой, возникшей в ходе поиска
			Myresult result = new Myresult();
			String error = "";
			String code ="";
			
			//количество файлов в которых будет осуществляться поиск числа (по условию задания их 20)
			int filesnumber = 20;
			
			//здесь задаются имена этих 20 файлов
			List<String> filenames = new ArrayList<>();
			for(int i=1; i<=filesnumber; i++) {
				filenames.add(i+".txt");
			}
			
			//создается объект главного потока исполнения поиска числа, в котором будут созданы отдельные потоки 
			//поиска для каждого указанного файла. этот же поток соберет в конце со всех остальных информацию
			//о результатах поиска. 
			MonitorSearchThread monitor = new MonitorSearchThread(number, filenames);
			monitor.start();
			//оповещение в логах о начале процедуры поиска
			logger.info("MonitorThread has been started...");
			try {
				//текущий поток ждет завершения потока монитора перед исполнением оставшейся части кода (присоединяясь к нему)
				monitor.join();
				//получение результатов поиска
				result = monitor.getResult();
				logger.info("Result obtained from MonitorThread with code: "+result.getCode());
			//если что-то пошло не так в блоках catch в результат будет записан код ошибки поиска
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				code = "03.Result.Error";
				error = "Unexpected interrupted error occurred while getting result";
				logger.error("Unexpected interrupted error occurred while getting result", e);
				result.setCode(code);
				result.setError(error);
			} catch (Exception e) {
				e.printStackTrace();
				code = "03.Result.Error";
				error = "Unexpected unknown error occurred while getting result";	
				logger.error("Unexpected unknown error occurred while getting result", e);
				result.setCode(code);
				result.setError(error);
			}
			return result;
		}
}
