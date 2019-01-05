package com.servicesoapex.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicesoapex.ourservice.Myresult;

/*
 * Основная задача этого класса запустить поиск в разных потоках, и подождать пока поисковые потоки (SearchThread) закончат поиск числа каждый в своем файле. 
 * После этого он должен собрать данные по результатам этого поиска с каждого потока. А именно, поток собирает данные по именам файлов в которых было найдено число и записывает их 
 * стрингбилдером в одну строку, разделенную ";". Также по результатам сбора определеяется код и проверяется на наличие сообщение об ошибках. 
 */
public class MonitorSearchThread extends Thread{
	
	final static Logger logger = Logger.getLogger(MonitorSearchThread.class);
	
	//сюда будет записан результат поиск
	Myresult result = new Myresult();
	
	int number;
	List<String> filenames;
	
	public Myresult getResult() {
		return result;
	}
	
	//В конструктор передается массив с названием файлов , в которых производится поиск числа и само искомое число. 
	public MonitorSearchThread(int number, List<String> filenames) {
		super();
		this.number = number;
		this.filenames = filenames;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Запускается один единственный метод, который запускает поисковые потоки и собирает данные по результатам поиска этих потоков
		//Результат возвращает как объект Myresult, необходимый нам для возврата в вызывающую среду веб-сервиса (response)
		result = get_results(number, filenames);
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	//Здесь осуществляется реализация основного метода, собирающего данные с "поисковых" потоков
	public Myresult get_results(int number, List<String> fileNames) {
		 
		String code = "02.Result.Error";
		//стрингбилдер собирает названия файлов в которых встретиться искомое число 
		StringBuilder sb = new StringBuilder();
		
		//инициализация массивов для поисковых потоков, 
		//чтобы в дальнейшем хранить на них ссылки для присоединения к ним и обработки результатов их работы
		List<Thread> threads = new ArrayList<>();
		List<SearchThread> searchThreads = new ArrayList<>();
		
		//здесь происходит запись сылок на поисковые потоки в лист и попутно их запуск
		for(String file : fileNames) {
			SearchThread searchTh = new SearchThread(number, file);
			searchThreads.add(searchTh);
			Thread thread = new Thread(searchTh);
			threads.add(thread);
			thread.start();
		}
		
		//Поток монитор присединяется последовательно к каждому не завершенному поисковому потоку,
		//чтобы дождаться результатов завершения работы их всех
		for(Thread th : threads) {
			try {
				th.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				result.setCode(code);
				result.setFileNames("");
				result.setError("Monitor thread has been interrupted");
				e.printStackTrace();
				logger.error("Monitor thread has been interrupted");
				return result;
			}
		}
		
		//после того, как все потоки поиска завершены, происходит сбор результатов
		for(SearchThread sth: searchThreads) {
			//проверка на возникшие ошибки в ходе поиска, с последующей установкой текста ошибки полю error объекта MyResult
			if(sth.getErrorMsg().length() > 0 || !sth.getErrorMsg().equals("")) {
				result.setError(sth.getErrorMsg());
				sth.removeErrorMsg();
			}	
			//проверка на содержание в конкретном потоке поиска ичкомо числа и добавление в стрингбилдер
			//если число было найдено соответствующим потоком в файле
			if(sth.getIsContent() == true) {
				sb.append(sth.getFileName()+"; ");
			}
		}
		
		//в конце осуществляется проверка. Если хотя бы один поток нашел число в каком-то файле, то задается код объета "00.Result.ОК", 
		//если число не было найдено нигде, т.е. стрингбилдер пустой, то задается код "01.Result.NotFound". 
		code = (sb.toString().length()!=0 || !sb.toString().equals("")) ? "00.Result.OK" : "01.Result.NotFound";
		//код и имена файлов в которых нашлось число записываются в объект результата поиска в поле с именами файлов для возврата в вызывающую среду.
		result.setCode(code);
		result.setFileNames(sb.toString());
		//в логах отображается нормальное завершение поиска и происходит возврат результата в сервисный метод (MyServiceUtil). 
		logger.info("Normal termination of monitor thread");
		return result;
	}
		
}
