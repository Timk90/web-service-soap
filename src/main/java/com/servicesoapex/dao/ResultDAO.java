package com.servicesoapex.dao;

/*
 * Класс представляет собой шаблон для создания DAO объектов при доступе к базе-данных.
 * Был создан дополнительно, поскольку результат поиска числа, выдаваемый обратно вызывающей среде
 *  не содержит в себе искомое число и ID. Класс нужен исключительно для работы с БД.  
 */

public class ResultDAO {
	
	long id;
	String code;
	int number;
	String filenames;
	String error;
	public long getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getFilenames() {
		return filenames;
	}
	public void setFilenames(String filenames) {
		this.filenames = filenames;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	public ResultDAO(String code, int number, String filenames, String error) {
		super();
		this.code = code;
		this.number = number;
		this.filenames = filenames;
		this.error = error;
	}
	
	public ResultDAO(int id, String code, int number, String filenames, String error) {
		super();
		this.id = id;
		this.code = code;
		this.number = number;
		this.filenames = filenames;
		this.error = error;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Result DAO[id="+this.id+", code="+this.code+", number="+this.number+", filename={"+this.filenames+"}, error="+this.error;
	}
	
}
