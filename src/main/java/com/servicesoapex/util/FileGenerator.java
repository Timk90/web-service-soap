package com.servicesoapex.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Класс, содержащий метод для генерации n файлов с заданной по условиям задания структурой (числа разделены ","). 
 * В качестве содержимого формируются числа с помощью генератора псевдослучайных чисел, предоставляемые классом Random.
 */

//класс имплементерует интерфейс Runnable, чтобы иметь возможность создавать файлы 
//в разных потоках (для экономии времени). 
public class FileGenerator implements Runnable{
	String filename;
	
	public FileGenerator(String filename) {
		super();
		this.filename = filename;
	}

	//Основной метод в котором происходит создание текстовых файлов с числами, разделенными запятыми
	public static void createTxtFile(String filename) {
		//для записи в файл , используется буфферизированный символьный поток. 
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(filename));
			Random random = new Random();
			//число i подбиралось исходя из необходимости раздуть файл до 1 Гб. 
			for(int i = 0 ; i <250000000; i++) {
				//проверка введена для записи результатов в строки длиной 10000 цифр (произвольно выбранное ограничение)
				//Каждый 10000 чисел добавляется символ конца строки - \n
				if(i>0 && i%10000 == 0) {
					//генерация чисел организована с вычитанием для получения и отрицательных чисел (ради разнообразия)
					bw.append(random.nextInt(1000)-random.nextInt(500)+",\n");
				}else {
					bw.append(random.nextInt(1000)-random.nextInt(500)+",");		
				}
				
			}
			
			//В конец файла я добавил заведомо большое число (100001), учитывая, что оно никогда не будет сгенерировано экземпляром класса Random. 
			//Сделано это исключительно для тестовых целей, чтобы в последствии оценить МАКСИМАЛЬНОЕ ВОЗМОЖНОЕ 
			//время поиска числа (т.к. оно находится на последней позиции в файле).  
			bw.append("100001");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//ИНФО о том, что произошла ошибка ввода/вывода при формировании файла.  
			System.out.println("file "+filename+" has not been created in "+Thread.currentThread().getName());
			e.printStackTrace();
		}finally {
			if(bw != null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		System.out.println("file "+filename+" has been created in "+Thread.currentThread().getName());
	}
	
	//чтобы сгенерировать все файлы необходимо просто запустить метод мейн
	public static void main(String[] args) {
		
		//количество фалов, которые будут созданы
		//по условию задания их должно быть 20
		int numberFiles = 20;
		List<String> files = new ArrayList<>();
		for(int i=1; i<=numberFiles; i++) {
			//для простоты имена файлов имеют вид: 1.txt, 2.txt и тд. 
			files.add(i+".txt");
		}
		
		//каждый файл генерируется в новом потоке, просто чтобы сэкономить время. 
		for(String file : files)
		new Thread(new FileGenerator(file)).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		createTxtFile(filename);
	}
	
}
