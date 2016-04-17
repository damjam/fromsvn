package flink.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 */
public abstract class DateUtil {
	public static Date addDays(Date date, int num) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, num);
		
		return c.getTime();
	}

	public static String addDays(String date, int num, String pattern) {
		return DateUtil.getDate(DateUtil.addDays(DateUtil.string2Date(date, pattern), num), pattern);
	}
    
    public static Date addMins(Date date, int num) {
		if (date == null) {
			throw new IllegalArgumentException("The date must not be null");
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, num);
		return c.getTime();
	}
    public static Date addMonths(Date date, int num) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        return c.getTime();
    }
	
	public static Date addYears(Date date, int num) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, num);
        return c.getTime();
    }

	private static Date clearTime(Date date) {
		if (date == null) {
			return null;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
	/**
	 * getDate
	 * 
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static String formatDate(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}
	/**
	 * getDate
	 * 
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static String formatDate(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}
	public static Date getCurrent() {
    	return new Date();
    }
	/**
     * 获取当前日期字符串，格式为yyyyMMdd
     * @return String
     */
    public static String getCurrentDate()
    {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
	/**
	 * yyyyMMddHHmmss
	 * @return
	 */
	public static String getCurrentDateTime()
	{
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	/**
	 * 获取当前日期字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @return String
	 */
	public static String getCurrentPrettyDateTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	public static String getPrettyDateTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 获取当前日期字符串，格式为HH:mm:ss
	 * @return String
	 */
	public static String getCurrentPrettyTime()
	{
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	/**
	 * 获取当前日期字符串，格式为Hmmss
	 * @return String
	 */
	public static String getCurrentTime()
	{
		return new SimpleDateFormat("HHmmss").format(new Date());
	}

	/**
     * 获取当前日期字符串，格式为yyy-MM-dd
     * @return String
     */
    public static String getCurrentToDate()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

	/**
	 * 获取对应Date的日期字符串，格式为yyyy-MM-dd
	 * 
	 * @return String
	 */
	public static String getDate() {
		return DateUtil.formatDate("yyyy-MM-dd");
	}

	/**
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return DateUtil.getDate(date, "yyyy-MM-dd");
	}
	/**
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	public static Date getDateByYYYMMDD(String dateString) {
		if(dateString.length()<8){
			return null;
		}else{
			try {
				return new SimpleDateFormat("yyyyMMdd").parse(dateString); 
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
	
    /**
	 * getDateDiffDays
	 * 
	 * @param curDate
	 * @param oriDate
	 * @return int
	 * @since 2006-12-28
	 */
	public static int getDateDiffDays(Date curDate, Date oriDate) {
		final int MS_PER_DAY = 1000 * 60 * 60 * 24;
		Calendar cal = Calendar.getInstance();    
        cal.setTime(curDate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(oriDate);    
        long time2 = cal.getTimeInMillis();         
        long interval = (time1-time2)/(MS_PER_DAY);  
        return (int)interval;
	}
    
    public static String getDateYYYYMM(Date date)
	{
		return new SimpleDateFormat("yyyyMM").format(date);
	}
    
	/**
	 * 获取对应Date的日期字符串，格式为yyyyMMdd
	 * @param date 婧怐ate
	 * @return
	 */
	public static String getDateYYYYMMDD(Date date)
	{
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}
	
	/**
	 * 把YYYYMMDD的日期格式转换成java.util.Date
	 * @param dateString
	 * @return
	 */
	public static Date getDayEndByYYYMMDD(String dateString) {
		if(dateString.length()<8){
			return null;
		}else{
			Calendar calendar=Calendar.getInstance();
			calendar.clear();
			calendar.set(Calendar.YEAR, Integer.parseInt(dateString.substring(0,4)));
			calendar.set(Calendar.MONTH,Integer.parseInt(dateString.substring(4,6))-1);
			calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateString.substring(6)));
			calendar.set(Calendar.HOUR, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			return calendar.getTime();
		}
		
	}
	
	public static Date getEndDate(Date endDate){
		Date date = clearTime(endDate);
		
		return date == null ? null : addDays(date, 1);
	}
	
	//取某个月份的月底
    public static String getMaxMonthDate(int offset) {
    	 Date date = DateUtil.addMonths(DateUtil.getCurrent(), offset);
    	 Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
         return DateUtil.formatDate("yyyyMMdd", calendar.getTime()); 
	}
	
	//取某个月份的月初
    public static String getMinMonthDate(int offset) {
    	 Date date = DateUtil.addMonths(DateUtil.getCurrent(), offset);
    	 Calendar calendar = Calendar.getInstance();  
         calendar.setTime(date);  
         calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
         return DateUtil.formatDate("yyyyMMdd", calendar.getTime()); 
	}
	public static Date getNextDate(String dateString, String pattern) {
    	Date nextDate = null;
    	if ("yyyy".equals(pattern)) {
			nextDate = DateUtil.addYears(DateUtil.string2Date(dateString, pattern), 1);
		}else if ("yyyyMM".equals(pattern)) {
			nextDate = DateUtil.addMonths(DateUtil.string2Date(dateString, pattern), 1);
		}else if("yyyyMMdd".equals(pattern)){
			nextDate = DateUtil.addDays(DateUtil.string2Date(dateString, pattern), 1);
		}
		return nextDate;
	}
	
	public static Date getStartDate(Date startDate) {
		return clearTime(startDate);
	}
	
	public static String getTimeMicroseconds(){
		
		return new SimpleDateFormat("HHmmssssssss").format(new Date());
	}

    public static Date getTomorrow(String date, String pattern){
		Date today = string2Date(date, pattern);
		
		return today == null ? null : addDays(today, 1);
	}
    /**
	 * 获取指定日期段的周末日期
	 * @param startDate yyyyMMdd
	 * @param endDate yyyyMMdd
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	public static List getWeekendDate(String startDate,String endDate) throws ParseException{
		SimpleDateFormat sf=new SimpleDateFormat();
		sf.applyPattern("yyyyMMdd");
		Date d = sf.parse(startDate);
		Calendar startCalendar=Calendar.getInstance();
		startCalendar.setTime(d);
		
		d=sf.parse(endDate);
		Calendar endCalendar=Calendar.getInstance();
		endCalendar.setTime(d);
		
		int startDay=startCalendar.get(Calendar.DAY_OF_MONTH);
		int endDay=endCalendar.get(Calendar.DAY_OF_MONTH);
		 
		int startMonth=startCalendar.get(Calendar.MONTH);
		int endMonth=endCalendar.get(Calendar.MONTH);
		
		int startYear=startCalendar.get(Calendar.YEAR);
		int endYear=endCalendar.get(Calendar.YEAR);
	
		
		ArrayList list=new ArrayList();
		Calendar weekCal=Calendar.getInstance();
		
		int iMonth=0;
		int iday=0;
		int day=1;
	
		boolean isFirsIn=true;
		
		for(;startYear<=endYear;startYear++){
			weekCal.set(Calendar.YEAR, startYear);
			if(endYear-startYear>0){
				iMonth=11;
			}else{
				iMonth=endMonth;
			}
			
			for(;startMonth<=iMonth;startMonth++){
				weekCal.set(Calendar.MONTH, startMonth);
				
				iday=weekCal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				if(isFirsIn){
					day=startDay;
					isFirsIn=false;
				}
				else{
					day=1;
					if(startYear==endYear && startMonth==endMonth){
						iday=endDay;
					}
				}
				
				for(int i=day;i<iday;i++){
					weekCal.set(Calendar.DAY_OF_MONTH, i);
					 if( weekCal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ||
							 weekCal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
						 list.add(sf.format(weekCal.getTime()));
					 }
				}
				
			}
			
			startMonth=0;
		}
		
		return list;
	}
  /**
     * 判断给定日期是否为月末的一天
     * 
     * @param date
     * @return true:是|false:不是
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
    	String expireDate = "20141130";
    	Date endDate = DateUtil.string2Date(expireDate, "yyyyMMdd");
		Date today = DateUtil.getCurrent();
		System.err.println(getDateDiffDays(today, endDate));
	}
    /**
	 * getDate
	 * 
	 * @param pattern
	 *            String
	 * @return String
	 */
	public static Date string2Date(String date, String pattern) {
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		
		try {
			return new SimpleDateFormat(pattern).parse(date); 
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
}

