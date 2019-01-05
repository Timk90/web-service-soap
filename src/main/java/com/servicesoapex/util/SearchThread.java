package com.servicesoapex.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

/*
 * Основной поисковый класс (Отдельный поток для каждого файла). 
 * Он имплементирует интерфейс Runnable для запуска в отдельном потоке. 
 * Каждый экземпляр класса нужен для поиска числа в отдельном файле.  
 * Распределение файлов по потокам реализовано для экономии времени и увеличения скорости поиска. 
 * 
 * Дополнительно:
 * Возможно, дальнейшего увеличения производительности можно было бы добиться, если:
 * 1). Размапить еще и каждый файл на несколько частей и для каждой части файла отдельно организовать поиск числа.
 * 2). (Возможно)Организовать поиск байт-, а не символьно-ориентированными потоками ввода/вывода. 
 */

public class SearchThread implements Runnable{
	
	final static Logger logger = Logger.getLogger(MyServiceUtil.class);

	static int thrCount = 0;
	
	private String filename; 
	private int number;
	private boolean isContent = false;
	private static String errormsg = "";
	
	public void set_file(int number, String filename) {
		this.filename = filename;
		this.number = number;
	}
	
	public String getErrorMsg() {
		return errormsg;
	}
	public void removeErrorMsg() {
		errormsg = "";
	}
	
	//в конструктор передается значение искомого числа и название файла в 
	//котором будет осуществлен поиск этого числа 
	public SearchThread(int number, String filename) {
		super();
		thrCount++;
		this.filename = filename;
		this.number = number;
	}
	
	public boolean getIsContent() {
		return isContent;
	}
	
	public String getFileName() {
		return filename;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Вызов основного метода, осуществляющего поиск числа в заданном файле
		isContent = get_result(number, filename);
	}
	
	//Основной метод данного класса, реализующий поиск числа в файле. 
	public boolean get_result(int number, String pathname) {
		
		//данные переменные введены для оценки времени поиска числа в файле.
        long start, end, dt; 
        
        //Начало отсчета поиска
    	start = System.currentTimeMillis();
    	
		try(BufferedReader in = new BufferedReader(new FileReader(pathname))) {
			
			String str;
			
			//поиск реализован примитивно. Считываются последовательно линии буфферизированным символьно-ориентированным потоком. 
			while((str = in.readLine()) != null) {
				//далее строка разбивается на массив сплитером (",") 
				String[] splitted = str.split(",");
				
				//число преобразуется в строку
				String impr = number+"";

				//в массиве ищется данное строковое представление числа
				for(String seq : splitted) {
					isContent = false;
					if(seq.equals(impr)) {
						//конец отсчета, если число найдено
						end = System.currentTimeMillis();
						//оценка времени поиска в мс и вывод результата
						String msg = "Search: in "+(dt = end - start) +" ms number: "+number+" has been found by "+Thread.currentThread().getName()+" in file: "+pathname;
						//запись в логи, что число найдено
						logger.info(msg);
						isContent = true;
						//выход из метода с флажком true - указывающим на то, что число было найдено
						return isContent;
					}
				}
			}
		// обработка возможных исключений и запись соответствующих логов при возникновении исключительной ситуации. 
		}catch (FileNotFoundException e) {
			if(errormsg.length() == 0) {
			errormsg="Absent file(s):";
			}
			errormsg = errormsg + filename+",";
			System.err.println(errormsg);
			logger.error("file with \""+filename+"\" has not been found...", e);
			//e.printStackTrace();
		}catch (IOException e) {
			errormsg = " I/O error occured"+Thread.currentThread().toString();
			System.err.println(errormsg);
			logger.error(errormsg, e);
			//e.printStackTrace();
		}catch (Exception e) {
			errormsg = " Unknown error occured while executing search thread "+Thread.currentThread().toString();
			System.err.println(errormsg);
			logger.error(errormsg, e);
			//e.printStackTrace();
		}
		//Если число так и не нашлось
		//конец отсчета (связанный с концом поиска в файле). 
		//в данном случае число не было найдено поэтому флажок получает значение false
		end = System.currentTimeMillis();
		String msg = "Search of "+number+ " in "+(dt = end - start) +" ms failed in "+Thread.currentThread().getName()+" for filename: "+pathname;
		logger.info(msg);
		//Здесь флажку присваивается значение false = в данном файле, указанного number нет.
		isContent = false;
		//выход из метода.
		return isContent;
	}

}
