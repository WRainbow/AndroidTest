package com.srainbow.androidtest.util;

public class LogUtil
{
    private static String LOG_TAG = DateUtil.getInstance().getDate2String();
    private static final int Level = 0;
    private static final int V = 1;
    private static final int D = 2;
    private static final int I = 3;
    private static final int W = 4;
    private static final int E = 5;
    private static final int A = 6;

    public static void v(String msg)
    {
        if (Level < V)
            LogUtil.v(LOG_TAG, msg);
    }

    public static void v(String tag, String msg)
    {
        if (Level < V)
            try
            {
                android.util.Log.v(tag, msg);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void v(String tag, String msg, Throwable t)
    {
        if (Level < V)
            try
            {
                android.util.Log.v(tag, msg, t);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void d(String msg)
    {
        if (Level < D)
        LogUtil.d(LOG_TAG, msg);
    }

    public static void d(String tag, String msg)
    {
        if (Level < D)
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable t)
    {
        if (Level < D)
            try
            {
                android.util.Log.d(tag, msg, t);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void i(String msg)
    {
        if (Level < I)
        LogUtil.i(LOG_TAG, msg);
    }

    public static void i(String tag, String msg)
    {
        if (Level < I)
            try
            {
                android.util.Log.i(tag, msg);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void i(String tag, String msg, Throwable t)
    {
        if (Level < I)
            try
            {
                android.util.Log.i(tag, msg, t);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void w(String msg)
    {
        if (Level < W)
        LogUtil.w(LOG_TAG, msg);
    }

    public static void w(String tag, String msg)
    {
        if (Level < W)
            try
            {
                android.util.Log.w(tag, msg);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void w(String tag, String msg, Throwable t)
    {
        if (Level < W)
            try
            {
                android.util.Log.w(tag, msg, t);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void e(String msg)
    {
        if (Level < E)
        LogUtil.e(LOG_TAG, msg);
    }

    public static void e(String tag, String msg)
    {
        if (Level < E)
            try
            {
                android.util.Log.e(tag, msg);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public static void e(String tag, String msg, Throwable t)
    {
        if (Level < E)
            try
            {
                android.util.Log.e(tag, msg, t);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }
}
