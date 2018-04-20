package com.srainbow.androidtest.util;

import android.content.Context;

/**
 * Created by SRainbow on 2018/3/22.
 * 屏幕工具类
 */

public class ScreenUtil extends BaseUtil
{
    private volatile static ScreenUtil instance;

    public static ScreenUtil getInstance()
    {
        if (instance == null)
        {
            synchronized (ScreenUtil.class)
            {
                if (instance == null)
                {
                    instance = new ScreenUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 获取状态栏的高度 （如果没有状态栏返回0）
     * @param context 上下文参数
     * @return 状态栏的高度
     * getIdentifier(String name, String defType, String defPackage): 获取资源对象的ID
     *     name: 资源对象的名称
     *     defType: 需要的对应属性
     *     defPackage: 系统内容
     *
     * getDimensionPixelSize(int id): 根据资源ID获取资源像素尺寸
     *     resourceId: 资源ID
     */
    public int getStatusBarHeightA(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取状态栏的高度 （使用反射）
     * @param context 上下文对象
     * @return 状态栏高度
     */
    public int getStatusBarHeightInflate(Context context)
    {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int id = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * dip 转成 像素
     * 返回值+0.5是为了四舍五入，和Math.round()效果一样：<br>
     *     int型数据：
     *     1.1 结果为 1；1.9 结果也为 1，所以在结果上 + 0.5 ==》  1.1 + 0.5 = 1.5 结果为 1；
     *     1.9 + 0.5 = 2.4 结果为 2
     * @param context 上下文对象
     * @param dipValue dp大小
     * @return 转换的px大小
     */
    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 像素 转成 dip
     * 返回值+0.5是为了四舍五入，和Math.round()效果一样：<br>
     *     int型数据：
     *     1.1 结果为 1；1.9 结果也为 1，所以在结果上 + 0.5 ==》  1.1 + 0.5 = 1.5 结果为 1；
     *     1.9 + 0.5 = 2.4 结果为 2
     * @param context 上下文对象
     * @param pxValue px大小
     * @return 转换的dp大小
     */
    public int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px 转成 sp
     * 返回值+0.5是为了四舍五入，和Math.round()效果一样：<br>
     *     int型数据：
     *     1.1 结果为 1；1.9 结果也为 1，所以在结果上 + 0.5 ==》  1.1 + 0.5 = 1.5 结果为 1；
     *     1.9 + 0.5 = 2.4 结果为 2
     * @param context 上下文对象
     * @param pxValue px大小
     * @return 转换的sp大小
     */
    public int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * 返回值+0.5是为了四舍五入，和Math.round()效果一样：<br>
     *     int型数据：
     *     1.1 结果为 1；1.9 结果也为 1，所以在结果上 + 0.5 ==》  1.1 + 0.5 = 1.5 结果为 1；
     *     1.9 + 0.5 = 2.4 结果为 2
     * @param context 上下文对象
     * @param spValue  sp大小（DisplayMetrics类中属性scaledDensity）
     * @return 转换的px大小
     */
    public int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
