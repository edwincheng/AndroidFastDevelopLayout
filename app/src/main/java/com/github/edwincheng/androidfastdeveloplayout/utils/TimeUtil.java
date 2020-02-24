package com.github.edwincheng.androidfastdeveloplayout.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static boolean isStopWebsiteDatetimeThread;

    /**
     *
     * @return 当前时间戳
     */
    public static long getCurrentTimeStamp(){
        return System.currentTimeMillis();
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getCurrentFileNameTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    //获得时、分
    public static String getSysHMTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(System.currentTimeMillis());
    }

    //获得时、分
    public static String getHMTimes(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }

    //获得分、秒
    public static String getMSTimes(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(time);
    }

    //获得时、分、秒
    public static String getSMSTimes(long time) {
        long times=time/1000;
        long hours = times / (60 * 60);
        long minutes = (times - hours * ( 60 * 60)) / 60;
        long s=(times - hours * (60 * 60)-minutes*60);
        String diffTime = "";
        if (minutes < 10) {
            diffTime = hours + ":0" + minutes;
        } else {
            diffTime = hours + ":" + minutes;
        }
        if (s<10){
            diffTime= diffTime+":0" +s;
        }else {
            diffTime= diffTime+":" +s;
        }
        return diffTime;


    }

    //获得年
    public static int getYears() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = df.format(date);
        return Integer.valueOf(str.substring(0, 4));
    }
	public static String getHMSTimes(long time){
		SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
		return format.format(time);
	}

	/**
	 * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	 HH时mm分ss秒，
	 strTime的时间格式必须要与formatType的时间格式相同
	 */
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}
	/**
	 * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 data Date类型的时间
	 */
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}
	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType) {
		Date date = null; // long类型转成Date类型
		try {
			date = longToDate(currentTime, formatType);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType)
			throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

    //获得月
    public static int getMonth(long timeStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(timeStr);
        return Integer.valueOf(str.substring(5, 7));
    }

    //获得月
    public static int getDate(long timeStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(timeStr);
        return Integer.valueOf(str.substring(8, 10));
    }

	private static String format;
	/**
	 * 获取指定网站的日期时间
	 */
	public static String getWebsiteDatetime(final String webUrl){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isStopWebsiteDatetimeThread) return;
				try {
					URL url = new URL(webUrl);// 取得资源对象
					URLConnection uc = url.openConnection();// 生成连接对象
					uc.connect();// 发出连接
					long ld = uc.getDate();// 读取网站日期时间
					Date date = new Date(ld);// 转换为标准时间对象
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 输出北京时间
					format = sdf.format(date);
				}catch (IOException e){
					e.printStackTrace();
					format = null;
				}
			}
		}).start();
		return format;
	}

    public static void stopWebsiteDatetimeThread(boolean mIsStopWebsiteDatetimeThread) {
        isStopWebsiteDatetimeThread = mIsStopWebsiteDatetimeThread;
    }

    public static long getFormFormatTime(String date) {
//		String date = "2001-03-15 15-37-05";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        long time = 0;
        try {
            time = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 显示差时间
     *
     * @param sdate
     * @return
     */
    public static String haveDoneTime(String sdate) {
        if (sdate == null) {
            return "Unknown";
        }
        long formFormatTime = getFormFormatTime(sdate);
        long formFormatTime1 = getFormFormatTime(getCurrentTime());

        long l = formFormatTime1 - formFormatTime;
        Date date = new Date(l);// 转换为标准时间对象
        int l1 = (int) (l / 3600000);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return l1 + ":" + sdf.format(date);
    }

    /**
     * 把文件日期换乘unix时间戳
     *
     * @param strDate
     * @return
     */
    public static String getFileTimestamp(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Long.toString(date.getTime() / 1000);
    }
}