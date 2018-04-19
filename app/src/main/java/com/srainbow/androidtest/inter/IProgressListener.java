package com.srainbow.androidtest.inter;

/**
 * Created by SRainbow on 2018/4/19.
 * 进度条监听
 */
public interface IProgressListener {
    //当前进度(0 - 100)
    void progress(int percent);
}
