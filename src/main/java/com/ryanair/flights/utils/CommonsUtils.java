/**
 * 
 */
package com.ryanair.flights.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author antonio.delrio
 *
 */
public class CommonsUtils {

	public static final String ISO_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm";
	public static final String ISO_FORMAT_TIME = "HH:mm";

	/***
	 * Funcion de utileria para convertir un string en localdatetime
	 * @param isoDate
	 * @return
	 */
	public static LocalDateTime stringToLocalDateTime(String isoDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_FORMAT_DATE);
		LocalDateTime dateTime = LocalDateTime.parse(isoDate, formatter);
		return dateTime;
	}
	
	/***
	 * Funcion de utileria para convertir un string en localtime
	 * @param isoDate
	 * @return
	 */
	public static LocalTime stringToLocalTime(String isoDate){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_FORMAT_TIME);
		LocalTime dateTime = LocalTime.parse(isoDate, formatter);
		return dateTime;
	}
	
	/***
	 * Funcion que devuelve el listado de años entre dos fechas, minimo 1 
	 * @param init
	 * @param end
	 * @return
	 */
	public static List<String> getYearsInInterval(LocalDateTime init, LocalDateTime end){
		int initYear = init.getYear();
		int finishYear = end.getYear();
		
		List<String> yearsResult = new ArrayList<String>();
		
		for(int i = initYear; i<=finishYear; i++){
			yearsResult.add(String.valueOf(i));
		}
		
		return yearsResult;
	}
	
	/***
	 * Funcion que devuelve el listado de meses entre dos fechas, minimo 1 
	 * @param init
	 * @param end
	 * @return
	 */
	public static List<String> getMonthsInInterval(LocalDateTime init, LocalDateTime end){
		int initMonth = init.getMonthValue();
		int finishMonth = end.getMonthValue();
		
		List<String> monthsResult = new ArrayList<String>();
		
		for(int i = initMonth; i<=finishMonth; i++){
			monthsResult.add(String.valueOf(i));
		}
		
		return monthsResult;
	}
	
	/***
	 * Funcion que devuelve el listado de años entre dos fechas, minimo 1 
	 * @param init
	 * @param end
	 * @return
	 */
	public static List<String> getDaysInInterval(LocalDateTime init, LocalDateTime end){
		Period periodFly = Period.between(init.toLocalDate(), end.toLocalDate());
		int days = periodFly.getDays(); 
		
		List<String> daysResult = new ArrayList<String>();
		
		for(int i = 0; i<=days; i++){
			daysResult.add(String.valueOf(init.getDayOfMonth() + i));
		}
		
		return daysResult;
	}
	
	/***
	 * Funcion que devuelve el listado de años entre dos fechas, minimo 1 
	 * @param init
	 * @param end
	 * @return
	 */
	public static List<String> getDaysInInterval(String init, String end){
		
		return getDaysInInterval(stringToLocalDateTime(init), stringToLocalDateTime(end));
	}
	
	/***
	 * Genera una fecha a partir del string d elas partes
	 * @param year
	 * @param month
	 * @param day
	 * @param time
	 * @return
	 */
	public static String isoStringDateTime(Integer year, Integer month, Integer day, String time){
		StringBuffer dateBuffer = new StringBuffer();
		
		dateBuffer.append(String.valueOf(year));
		dateBuffer.append("-");
		if(month<10){
			dateBuffer.append("0");
		}
		dateBuffer.append(month);
		dateBuffer.append("-");
		if(day<10){
			dateBuffer.append("0");
		}
		dateBuffer.append(day);
		dateBuffer.append("T");
		dateBuffer.append(time);
		
		return dateBuffer.toString();
	}
	
	/***
	 * Genera una fecha a partir del string de las partes
	 * @param year
	 * @param month
	 * @param day
	 * @param time
	 * @return
	 */
	public static LocalDateTime isoLocalDateTime(Integer year, Integer month, Integer day, String time){
		String dateString = isoStringDateTime(year, month, day, time);
		return stringToLocalDateTime(dateString);
	}
	
	
	
}
