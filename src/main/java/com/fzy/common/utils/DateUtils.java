/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.fzy.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyyMMddHHmmss", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (date == null){
			return "";
		}
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 根据自定义日期格式输出时间字符串
	 * @param date 时间
	 * @param datetype 自定义格式（如：yyyy-MM-dd）
	 * @return
	 */
	public static String formatDateTime(Date date,String datetype) {
		return formatDate(date, datetype);
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd）
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 按照自定义的类型将时间字符串转换为时间
	 * @param typeStr：自定义转换类型(如：yyyy-MM-dd HH:mm:ss)
	 * @param dateStr：时间字符串
	 * @return
	 */
	public static Date parseDateByType(String typeStr,String dateStr){
		SimpleDateFormat format=new SimpleDateFormat(typeStr);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 按照自定义的类型专家
	 * @param typeStr：自定义转换类型(如：yyyy-MM-dd HH:mm:ss)
	 * @param date：时间
	 * @return
	 */
	public static Date parseDate(Date date, String typeStr){
		if (date == null){
			return null;
		}
		String dateStr = formatDateTime(date, typeStr);
		return parseDateByType(typeStr, dateStr);
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 以当前时间为基准，在某个时间区域(年、月、日，时、分、秒)加减一段时间长度
	 * @param fieldType 时间区域
	 * @param expire 加减时间长度
	 * @return
	 */
	public static Date getDate(int fieldType, int expire){
		Calendar calendar=Calendar.getInstance();
		calendar.add(fieldType,expire);
		Date date=calendar.getTime();
		return date;
	}
	
	/**
	 * 根据时间字符串的时间为基准，在某个时间区域(年、月、日，时、分、秒)加减一段时间长度
	 * @param fieldType 时间区域
	 * @param expire 加减时间长度
	 * @param dateStr 基准时间字符串
	 * @return
	 */
	public static Date getDate(int fieldType, int expire,String dateStr){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(parseDate(dateStr));
		calendar.add(fieldType,expire);
		Date date=calendar.getTime();
		return date;
	}
	
	/**
	 * @param args
	 * @throws ParseException
	 */
//	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2015/6/6 15:00:50")));
//		long pastMinutes = DateUtils.pastMinutes(parseDate("2015/6/6 16:00:50"));
//		System.out.println(pastMinutes);
//		System.out.println(pastMinutes > -40);
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//	}
	
	/**
	 * 根据时间字符串获取其中的月份
	 * @param date
	 * @return
	 */
	public static int getDateMonth(String date){
		Calendar c = Calendar.getInstance();   
		c.setTime(parseDate(date));
		return c.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 根据时间字符串获取其月份的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDay(String date){
		Calendar c = Calendar.getInstance();
		c.setTime(parseDate(date));
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);
		return new Date(c.getTimeInMillis());
	}
	
	/**
	 * 根据时间字符串获取其月份的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDay(String date){
		Calendar c = Calendar.getInstance();
		c.setTime(parseDate(date));
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Date(c.getTimeInMillis());
	}
	
	/**
	 * 计算两个时间相差是否小于自定义的小时数
	 * @param hours 自定义的小时数
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @return true为小于，false为不小于(大于或等于)
	 */
	public static boolean checkMinusTimeHour(int hours ,Date starttime,Date endtime){
		if(starttime==null||endtime==null){
			return false;
		}
		long start = starttime.getTime();
		long end = endtime.getTime();
		long hourstime = hours*1000*60*60;
		long minus = end-start;
		if(minus<hourstime&&minus>=0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取本周前n周或后n周的开始结束时间
	 * 
	 * @param n //n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
	 * @return
	 * @version 1.0.0 2015年5月18日
	 */
	public static Date[] getAweekDates(int n){
		Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, n*7);
        //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal1.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Date startd = cal1.getTime();
        
        cal1.set(Calendar.DAY_OF_MONTH,cal1.get(Calendar.DAY_OF_MONTH) +6);
        cal1.set(Calendar.HOUR_OF_DAY, 23);
        cal1.set(Calendar.MINUTE, 59);
        cal1.set(Calendar.SECOND, 59);
        Date endd = cal1.getTime();
        return new Date[]{startd, endd};
	}
	
	/**
	 * 获取第n天的开始结束时间范围
	 * 
	 * @param n 
	 * @return
	 * @version 1.0.0 2015年5月18日
	 */
	public static Date[] getAdayDates(int n){
		//第n天的开始时间
		Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DAY_OF_MONTH, n);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        Date startd = cal1.getTime();
        //一天的最后一秒
        cal1.set(Calendar.HOUR_OF_DAY, 23);
        cal1.set(Calendar.MINUTE, 59);
        cal1.set(Calendar.SECOND, 59);
        Date endd = cal1.getTime();
		
        return new Date[]{startd, endd};
	}
	
	public static List<String> getListBetweenDays(Date smdate,Date bdate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<String> list = new ArrayList<>();
        try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(smdate);
	        long time1 = cal.getTimeInMillis();
	        cal.setTime(bdate);
	        long time2 = cal.getTimeInMillis();
	        long between_days=(time2-time1)/(1000*3600*24);
	        int days = Integer.parseInt(String.valueOf(between_days));
	        if(days<0){
	        	days = 0;
	        }
	        sdf=new SimpleDateFormat("yyyy-MM-dd");
	        for(int i=0;i<=days;i++){
	        	cal.setTime(smdate);
	        	cal.add(Calendar.DAY_OF_MONTH, i);
	        	list.add(sdf.format(cal.getTime()));
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return list;
	}

	public static Date piliTime(String s){
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
}
