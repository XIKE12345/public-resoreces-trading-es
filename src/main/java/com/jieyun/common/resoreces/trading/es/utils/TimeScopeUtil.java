package com.jieyun.common.resoreces.trading.es.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


/**
 * @author rendonglai
 * 获取String 类型的时间
 */
public class TimeScopeUtil {
	
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static final SimpleDateFormat sdf3= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date getDateByString(String time){
		try {
			Date d;
			if(time.length() == 10 ){
				d = sdf1.parse(time);
			}else if(time.length() == 16 ){
				d = sdf2.parse(time);
			}else{
				d = sdf3.parse(time);
			}

			return d;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 获取当前时间  00:00:00 
	 */
	public static String nowDayFirstSecond() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00");
	}
	
	/**
	 * 获取当前时间  23:59:59 
	 */
	public static String nowDayLastSecond() {
		return DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59");
	}
	
	/**获取传入时间的 00:00:00
	 * 要求格式  yyyy-MM-dd
	 * */
	public static String fistSecondForDay(String time) {

		try {
			Date d = sdf1.parse(time);
			return DateFormatUtils.format(d, "yyyy-MM-dd 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
		
	}
	

	/**获取当天时间的 23:59:59
	 * 要求格式  yyyy-MM-dd
	 * */
     public static String lastSecondForDay(String time) {
    	 Date d;
 		try {
 			d = sdf1.parse(time);
 			return DateFormatUtils.format(d, "yyyy-MM-dd 23:59:59");
 		} catch (ParseException e) {
 			e.printStackTrace();
 		}
 		return time;
	}

	/** 获取带T的开始时间 yyyy-MM-ddT00:00:00 */
	public static String startTimeHaveT (String time){

		return time+ TimeDict.MIN_TIME;
	}
	/** 获取带T的结束时间 yyyy-MM-ddT23:59:59 */
	public static String endTimeHaveT (String time){
		return time+ TimeDict.MAX_TIME;
	}

	/** 根据字典范围获取开始时间*/
	public static String timeRang (String timeDictCode){
		LocalDate day =  LocalDate.now();
		if(TimeDict.RANG_02.equals(timeDictCode)){
			return day.minusDays(4).toString()+ TimeDict.MIN_TIME;
		}
		if(TimeDict.RANG_03.equals(timeDictCode)){
			return day.minusDays(11).toString()+ TimeDict.MIN_TIME;
		}
		if(TimeDict.RANG_04.equals(timeDictCode)){
			return day.minusMonths(3).minusDays(1).toString()+ TimeDict.MIN_TIME;
		}
        return null;
	}

	/** 时间范围字典*/
	public static final class TimeDict{

		/**全部*/
		public static final String RANG_01 = "01";

		/**近三天*/
		private static final String RANG_02 = "02";

		/**近十天*/
		private static final String RANG_03 = "03";

		/**近三个月*/
		private static final String RANG_04 = "04";

		/**最小时间*/
		private static final String MIN_TIME = "T16:00:00.000Z";

		/**最大时间*/
		private static final String MAX_TIME = "T15:59:59.999Z";

	}
}
