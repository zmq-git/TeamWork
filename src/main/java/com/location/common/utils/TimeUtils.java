package com.location.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 日期工具类.
 * 
 * @author yecl
 * 
 */
public final class TimeUtils {

	private static final String[] parsePatterns = { "yyyy/MM/dd", "yyyy-MM-dd", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss",
			"yyyy-MM-dd HH:mm:ss", };

	/**
	 * 下个月1号
	 * 
	 * @return
	 */
	public static Date nextMonthFirstDay() {
		return plusMonthFirstDay(1);
	}

	/**
	 * 返回指定日期的下个月1号
	 * 
	 * @param date
	 *            指定日期
	 * @return
	 */
	public static Date plusMonthFirstDay(Date date) {
		return plusMonthFirstDay(date, 1);
	}

	/**
	 * 加上指定月份后的1号
	 * 
	 * @param month
	 *            月份
	 * @return
	 */
	public static Date plusMonthFirstDay(int month) {
		return plusMonthFirstDay(new Date(), month);
	}

	/**
	 * 对指定的时间加上指定月份后的1号
	 * 
	 * @param date
	 *            时间
	 * @param month
	 *            月份
	 * @return
	 */
	public static Date plusMonthFirstDay(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 本月最后一秒
	 * 
	 * @return
	 */
	public static Date monthLastSecond() {
		return plusMonthAndSetMonthLastSecond(1);
	}

	/**
	 * 加上指定月份后的上一个月后最后一秒
	 * 
	 * @param month
	 *            月份
	 * @return
	 */
	public static Date plusMonthAndSetMonthLastSecond(int month) {
		return plusMonthAndSetMonthLastSecond(new Date(), month);
	}

	/**
	 * 对指定的时间加上指定月份后的上一个月后最后一秒
	 * 
	 * @param date
	 *            时间
	 * @param month
	 *            月份
	 * @return
	 */
	public static Date plusMonthAndSetMonthLastSecond(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, -1);
		return calendar.getTime();
	}


	/**
	 * 获取当前时间
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getCurrentTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取YYYY-MM-DD
	 * 
	 * @return
	 */
	public static String getYYYYMMDD(Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(time);
	}
	/**
	 * 获取YYYYMM
	 *
	 * @return
	 */
	public static String getYYYYMM(Timestamp time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(time);
	}
	/**
	 * 返回当前时间
	 * 
	 * @return
	 */
	public static Date thisDate() {
		return new Date();
	}

	/**
	 * 获取两个日期的相差月份.
	 * 
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 相差的月份
	 */
	public static int getMonthSpace(Timestamp start, Timestamp end) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTimeInMillis(start.getTime());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTimeInMillis(end.getTime());
		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		return 12 * year + month;
	}

	/**
	 * 获取某天的第一秒(yyyy/MM/dd 00:00:00)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstSecondDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取某天的最后一秒(yyyy/MM/dd 23:59:59)
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastSecondDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, -1);
		return calendar.getTime();
	}

	public static Timestamp getBeforeDayStartDate(Date date){
		Calendar rightNow = Calendar.getInstance();
		Timestamp timestamp = null;
		if (null != rightNow) {
			rightNow.setTime(date);
	        rightNow.set(5, rightNow.get(5) - 1);
	        rightNow.set(11, 0);
	        rightNow.set(14, 0);
	        rightNow.set(13, 0);
	        rightNow.set(12, 0);
	        rightNow.set(2, rightNow.get(2));
	        timestamp = new Timestamp(rightNow.getTimeInMillis());
		}
        
		return timestamp;
	}
	
	public static Timestamp getNextMouthLastDay(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(date));
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 2);
        calendar.set(Calendar.DATE, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Timestamp(calendar.getTimeInMillis()));
        calendar2.set(5, calendar2.getActualMaximum(5));
        calendar2.set(11, 23);
        calendar2.set(14, 59);
        calendar2.set(13, 59);
        calendar2.set(12, 59);
        calendar2.set(2, calendar2.get(2));
        Timestamp calendar3 = new Timestamp(calendar2.getTimeInMillis());
		return calendar3;
	}
	/**
	 * 获取2099/12/30 23:59:59
	 *
	 * @return Timestamp 2099/12/30 23:59:59
	 */
	public static Timestamp getLastDayOf_2099() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date exp = null;
		try {
			exp = sf.parse("20991231235959");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Timestamp(exp.getTime());
	}


}
