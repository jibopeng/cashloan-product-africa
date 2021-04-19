package com.ajaya.cashloan.core.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lsk on 2017/2/14.
 */
public class DateUtil extends tool.util.DateUtil{

    @SuppressWarnings("deprecation")
	public static Date dateAddMins(Date date, int minCnt) {
        Date d = new Date(date.getTime());
        d.setMinutes(d.getMinutes() + minCnt);
        return d;
    }
    
    /**
     * 计算时间差,单位分
     * @param date1
     * @param date2
     * @return
     */
    public static int minuteBetween(Date date1, Date date2){
		DateFormat sdf=new SimpleDateFormat(DATEFORMAT_STR_001);
		Calendar cal = Calendar.getInstance();
		try {
			Date d1 = sdf.parse(DateUtil.dateStr4(date1));
			Date d2 = sdf.parse(DateUtil.dateStr4(date2));
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((time2 - time1) / 60000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
    
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
     * 计算时间差,单位分
     * @param date1
     * @param date2
     * @return
     */
    public static int secondBetween(Date date1, Date date2){
		DateFormat sdf=new SimpleDateFormat(DATEFORMAT_STR_001);
		Calendar cal = Calendar.getInstance();
		try {
			Date d1 = sdf.parse(DateUtil.dateStr4(date1));
			Date d2 = sdf.parse(DateUtil.dateStr4(date2));
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((time2 - time1) / 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
    
	/**
	 * 获取指定时间天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStartTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * String转化Date格式
	 * @param date
	 * @param type
	 * @return
	 */
	public static Date parse(String date,String type){
		SimpleDateFormat formatter = new SimpleDateFormat(type);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(date, pos);
		return strtodate;
		
	}
	
	/**
	 * 得到指定日期之间的天数集合
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public static List<Date> dateSplit(Date startDate, Date endDate)
	        throws Exception {
	    if (!startDate.before(endDate))
	        throw new Exception("开始时间应该在结束时间之后");
	    Long spi = endDate.getTime() - startDate.getTime();
	    Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

	    List<Date> dateList = new ArrayList<Date>();
	    dateList.add(endDate);
	    for (int i = 1; i <= step; i++) {
	        dateList.add(new Date(dateList.get(i - 1).getTime()
	                - (24 * 60 * 60 * 1000)));// 比上一天减一
	    }
	    return dateList;
	}
	
	/**
	 * 得到指定日期之间的月数集合
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException{
	    ArrayList<String> result = new ArrayList<String>();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

	    Calendar min = Calendar.getInstance();
	    Calendar max = Calendar.getInstance();

	    min.setTime(sdf.parse(minDate));
	    min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

	    max.setTime(sdf.parse(maxDate));
	    max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

	    Calendar curr = min;
	    while (curr.before(max)) {
	     result.add(sdf.format(curr.getTime()));
	     curr.add(Calendar.MONTH, 1);
	    }

	    return result;
	  }
	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date smdate,Date bdate)
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 得到指定之前的前后几天
	 * @param day
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getDateBefore(int day,Date date){
		Calendar   calendar   =   new GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动 
		date=calendar.getTime();
		return date;
	}

	@SuppressWarnings("deprecation")
	public static Date dateAddDays(Date date, int days) {
		Date d = new Date(date.getTime());
		d.setDate(d.getDate() + days);
		return d;
	}
	
	
	/**
	 * 注意i是负数表示上几个月
	 * @param i
	 * @throws ParseException 
	 */
	public static Map<String, Date> getMonthStartEnd(Integer i) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date timeStart = null;
		Date timeEnd = null;
		if (i == 0) {
			// 说明要获取的是当前月的第一天和最后一天
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			timeStart = c.getTime();

			// 获取当前月最后一天
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			timeEnd = ca.getTime();
		} else {
			Calendar cal_1 = Calendar.getInstance();// 获取当前日期
			cal_1.add(Calendar.MONTH, i);
			cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
			timeStart = cal_1.getTime();

			Calendar cale = Calendar.getInstance();
			cale.add(Calendar.MONTH, i + 1);
			cale.set(Calendar.DAY_OF_MONTH, 0); // 设置为1号,当前日期既为本月第一天
			timeEnd = cale.getTime();
		}
		
		Map<String, Date> result = new HashMap<>();
		String timeStartStr = format1.format(timeStart);
		timeStart = format2.parse(timeStartStr + " 00:00:00");
		
		String timeEndStr = format1.format(timeEnd);
		timeEnd = format2.parse(timeEndStr + " 23:59:59");
		result.put("timeStart", timeStart);
		result.put("timeEnd", timeEnd);
		return result;
	}
	
	/**
	 * 判断传入的日期是否属于from和to之间
	 * @param time
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean belongCalendar(Date time, Date from, Date to) {
        Calendar date = Calendar.getInstance();
        date.setTime(time);

        Calendar after = Calendar.getInstance();
        after.setTime(from);

        Calendar before = Calendar.getInstance();
        before.setTime(to);

        if (date.after(after) && date.before(before)) {
            return true;
        } else {
            return false;
        }
    }
	
	
	/**
	 * 获取之后n个月的时间
	 * @param time
	 * @param next
	 * @return
	 */
	public static Date getNextMouth(Date time, Integer next) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time  = format.parse(format.format(time).split(" ")[0] + " 23:59:59");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.MONTH, next);
			Date m = calendar.getTime();
			return m;
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 获取之后n天的时间
	 * @param time
	 * @param next
	 * @return
	 */
	public static Date getNextDay(Date time, Integer next) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			time  = format.parse(format.format(time));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.DATE, next);
			Date m = calendar.getTime();
			return m;
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取指定日期的月
	 */
	public static String getMonth(Date date){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int i = now.get(Calendar.MONTH);
		return i+"";
	}

	
	/**
	 * 获取指定日期的日
	 */
	public static String getDayRi(Date date){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		int i = now.get(Calendar.DAY_OF_MONTH);
		return i+"";
	}
	
	/**
	 * 获取上n个月的时间
	 * 注意n是负数
	 */
	public static String getBeforeMonth(Integer n, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
        c.add(Calendar.MONTH, n);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
	}
	
	public static String getBeforeDay(Date time, Integer next) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			time  = format.parse(format.format(time));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(time);
			calendar.add(Calendar.DATE, next);
			Date m = calendar.getTime();
			return format.format(m);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}
	
	public static Boolean isWeekend(String bDate) throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
        Date bdate = format1.parse(bDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }
 
	}
	public static Boolean isWeekend(Date bDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(bDate);
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			return true;
		} else{
			return false;
		}

	}
}
