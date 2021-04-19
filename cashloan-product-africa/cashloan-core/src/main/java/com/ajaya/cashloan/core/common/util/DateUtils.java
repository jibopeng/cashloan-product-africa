package com.ajaya.cashloan.core.common.util;

import com.ajaya.cashloan.core.enums.MonthIndianEnums;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能说明：日期处理类
 *
 * @author yanzhiqiang
 * @since 2020-02-26 20:37
 **/
public class DateUtils {

    /**
     * dd/MM/yyyy格式生日获取年龄
     *
     * @param birthStr 生日字符串
     * @return 年龄
     */
    public static int getAgeByBirth(String birthStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        int age = 0;
        try {
            Date birthday = simpleDateFormat.parse(birthStr);
            Calendar now = Calendar.getInstance();
            // 当前时间
            now.setTime(new Date());
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);
            //如果传入的时间，在当前时间的后面，返回0岁
            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
            //兼容性更强,异常后返回数据
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取n月n天n分n秒后或者前的时间
     *
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return 指定日期
     */

    public static Date getAppointDate(Date date, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 获取n月n天n分n秒后或者前的时间
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 指定日期
     */

    public static ArrayList<Date> getAppointDate(Date startDate, Date endDate) {
        ArrayList<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        while (startDate.getTime() <= endDate.getTime()) {
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, +1);
            calendar.add(Calendar.HOUR_OF_DAY, 0);
            calendar.add(Calendar.MINUTE, 0);
            calendar.add(Calendar.SECOND, 0);
            calendar.add(Calendar.DATE, 0);
            startDate = calendar.getTime();
            dates.add(startDate);
        }
        return dates;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param firstTime 前时间
     * @param lastTime  后时间
     * @return 天数
     */
    public static int differentDaysByMillisecond(Date firstTime, Date lastTime) {
        return (int) ((lastTime.getTime() - firstTime.getTime()) / (1000 * 3600 * 24));
    }

    public static String getIndiatnFormatDate(Date date){
        SimpleDateFormat formatYY = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMM = new SimpleDateFormat("MM");
        SimpleDateFormat formatDD = new SimpleDateFormat("dd");
        String yy = formatYY.format(date);
        String mm = formatMM.format(date);
        String dd = formatDD.format(date);
        StringBuilder builder = new StringBuilder(dd);
        builder.append(" ").append(MonthIndianEnums.getStateNameByCode(mm)).append(" ").append(yy);
        return builder.toString();
    }

    /**
     * 日期更格式化
     * @param date 日期
     * @return 日期字符串
     */
    public static String getDateStr(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-YYYY");
        return format.format(date);
    }
}
