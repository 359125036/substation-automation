package com.substation.utils;

import com.substation.hk_sdk.HCNetSDK;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * @Title: getTime
     * @Description:获取转换后的时间
     * @param time
     * @return String
     **/
    public static String getTime(String time) {
        String timestamp = null;
        try {
            timestamp = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * @Title: getStarttime
     * @Description:获取回放开始时间
     * @param time
     * @return starttime
     **/
    public static String getStarttime(String time) {
        String starttime = null;
        try {
            starttime = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime() - 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return starttime;
    }

    /**
     * @Title: getEndtime
     * @Description:获取回放结束时间
     * @param time
     * @return endString
     **/
    public static String getEndtime(String time) {
        String endString = null;
        try {
            endString = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime() + 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return endString;
    }

    /**
     * 获取海康录像机格式的时间
     * @param time
     * @return
     */
    public static HCNetSDK.NET_DVR_TIME getNvrTime(String time) {
        HCNetSDK.NET_DVR_TIME structTime = new HCNetSDK.NET_DVR_TIME();
        String str = null;
        try {
            str = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime() + 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] times = str.split("-");
        structTime.dwYear = Integer.parseInt(times[0]);
        structTime.dwMonth = Integer.parseInt(times[1]);
        structTime.dwDay = Integer.parseInt(times[2]);
        structTime.dwHour = Integer.parseInt(times[3]);
        structTime.dwMinute =Integer.parseInt(times[4]);
        structTime.dwSecond = Integer.parseInt(times[5]);
        return structTime;
    }

    /**
     * 时间类型数据转时间戳
     * @param date
     * @return
     */
    public static Long stringToTime(String date){
        Long sdf = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf;
    }
    public static void main(String[] args) {
//        HCNetSDK.NET_DVR_TIME hkTime = getNvrTime("2021-03-01 11:11:11");
//        System.out.println(hkTime);
        Long aLong = stringToTime("2021-04-27 14:07:56");
        System.out.println(aLong);
    }
}
