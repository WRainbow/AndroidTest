package com.srainbow.androidtest.util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by SRainbow on 2018/3/27.
 * 计算工具类
 */

public class MathUtil extends BaseUtil {
    private volatile static MathUtil instance;

    public static MathUtil getInstance() {
        if (instance == null) {
            synchronized(MathUtil.class) {
                if (instance == null) {
                    instance = new MathUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 获取随机十六进制的颜色代码.例如  "#6E36B4"
     * @return String
     */
    public String getRandColorString() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return "#" + r + g + b;
    }

    //获取随机颜色值
    public int getRandColor() {
        return Color.parseColor(getRandColorString());
    }
}
