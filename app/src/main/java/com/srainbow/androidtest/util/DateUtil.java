package com.srainbow.androidtest.util;

import java.util.Calendar;

/**
 * Created by SRainbow on 2018/3/27.
 * 时间工具类
 */

public class DateUtil extends BaseUtil
{
    private volatile static DateUtil instance;

    public static DateUtil getInstance()
    {
        if (instance == null)
        {
            synchronized (DateUtil.class)
            {
                if (instance == null)
                {
                    instance = new DateUtil();
                }
            }
        }
        return instance;
    }

    //获取当前年月日字符串
    public String getDate2String()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "" + (calendar.get(Calendar.MONTH) + 1)
                + "" + calendar.get(Calendar.DAY_OF_MONTH);
    }
}
