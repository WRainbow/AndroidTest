package com.srainbow.androidtest.util;

/**
 * Created by SRainbow on 2018/3/27.
 * 工具类
 */

public class SampleUtil extends BaseUtil
{
    private volatile static SampleUtil instance;

    public static SampleUtil getInstance()
    {
        if (instance == null)
        {
            synchronized (SampleUtil.class)
            {
                if (instance == null)
                {
                    instance = new SampleUtil();
                }
            }
        }
        return instance;
    }
}
