package com.netease.ar.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author zxwu
 */
public class DateUtil {
    // 标准日期时间格式
	/**
     * yyyy-MM
     */
    public static final String FORMAT_MONTH = "yyyy-MM";
    /**
     * yyyy
     */
    public static final String FORMAT_DATE_YEAR = "yyyy";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_DATE_MIN = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    // 无符号格式
    /**
     * yyyyMMdd
     */
    public static final String FORMAT_DATE_UNSIGNED = "yyyyMMdd";
    /**
     * yyyyMMddHHmmss
     */
    public static final String FORMAT_DATE_TIME_UNSIGNED = "yyyyMMddHHmmss";

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";

    public final static int TYPE_DAY = 0; //类型天
    public final static int TYPE_HOUR = 1; //类型小时
    public final static int TYPE_MINUTE = 2; //类型分钟
    public final static int TYPE_SECOND = 3; //类型秒数

    /**
     * 将标准时间转成时间格式
     *
     * @param date 标准时间
     * @return 时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String format(Date date) {
        return format(date, FORMAT_DATE_TIME);
    }

    /**
     * 按指定格式格式化时期时间
     *
     * @param date   date
     * @param format format
     * @return string.
     */
    public static String format(Date date, String format) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.format(date);
        } else {
            return "";
        }
    }

    /**
     * 将时间的字符串格式转成Date
     *
     * @param str str
     * @return Date
     */
    public static Date parse(String str) {
        return parse(str, FORMAT_DATE_TIME);
    }

    /**
     * 按指定格式解析字符串，将字符串转为日期时间格式
     *
     * @param str    string
     * @param format format
     * @return date
     */
    public static Date parse(String str, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 显示x秒前、x分前。。。的时间格式
     */
    public static String toDisplayDatetime(String src) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE_TIME);
            Date date = sf.parse(src);

            long delta = System.currentTimeMillis() - date.getTime();
            if (delta < ONE_MINUTE) {
                long seconds = toSeconds(delta);
                return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;

            } else if (delta < ONE_HOUR) {
                long minutes = toMinutes(delta);
                return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;

            } else if (delta < ONE_DAY) {
                long hours = toHours(delta);
                return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;

            } else if (delta < 2L * ONE_DAY) {
                return "昨天";

            } else if (delta < 30L * ONE_DAY) {
                long days = toDays(delta);
                return (days <= 0 ? 1 : days) + ONE_DAY_AGO;

            } else if (delta < 365L * ONE_DAY) {
                long months = toMonths(delta);
                return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;

            } else {
                return src.substring(0, 10);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }
    
    public static Date getStatisDayTime(Date currentTime, int value){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, value);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Date getStatisLastDayTime(Date currentTime, int value){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, value);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTime();
    }
    
    public static Date getStatisticsTimeByMonth(Date time, int value){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + value);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    public static Date getStatisticsLastTimeByMonth(Date time, int value){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + value);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return cal.getTime();
    }
    
    /**
     * 获取当前日期前N天或者后N天   格式yyyy-MM-dd
     */
    public static String getStaticDayDate(int value){
    	Date date = getStatisDayTime(new Date(), value);
    	return format(date, FORMAT_DATE);
    }
    
    /**
	 * 根据年月获取上个月最后一天，和当月第一天
	 */
	public static  Date[] getTimeRange(String month){
		Date[] result = new Date[2];
		Calendar cal=Calendar.getInstance();  
		cal.setTime(parse(month, FORMAT_MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		//结束日期为当月最后一天
		result[1] = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		//开始时间为当月第一天，
		result[0] = cal.getTime();
		return result;
	}

    /**
     * 当前年
     * @return
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 计算两个时间相差的天/小时数/分钟数/秒数
     */
    public static long calculateDate(Date beginDate, Date endDate, int type) {
        long between = (endDate.getTime() - beginDate.getTime());
        if(type == TYPE_DAY)
            return between / (60 * 60 * 1000 * 24);
        else if(type == TYPE_HOUR)
            return between / (60 * 60 * 1000);
        else if(type == TYPE_MINUTE)
            return between / (60 * 1000);
        else if(type == TYPE_SECOND)
            return between / (1000);
        else
            return 0;
    }
    
}
