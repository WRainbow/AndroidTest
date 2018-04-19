package com.srainbow.androidtest;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.srainbow.androidtest.base.BaseActivity;
import com.srainbow.androidtest.inter.IProgressListener;
import com.srainbow.androidtest.util.MathUtil;
import com.srainbow.androidtest.widget.VolumeView;

public class MainActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, IProgressListener {
    private VolumeView mVolume;
    private SeekBar mSeekBar;
    private TextView mTvShowSeekBar;
    private TextView mTvShowVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mVolume = findViewById(R.id.vl_new_volume);
        mSeekBar = findViewById(R.id.sb_new_test1);
        mTvShowSeekBar = findViewById(R.id.tv_new_test1);
        mTvShowVolume = findViewById(R.id.tv_new_test2);
        mSeekBar.setOnSeekBarChangeListener(this);
        mVolume.setProgressListener(this);
        findViewById(R.id.bn_new_test1).setOnClickListener(this);
        findViewById(R.id.bn_new_test2).setOnClickListener(this);
        findViewById(R.id.bn_new_test3).setOnClickListener(this);
        findViewById(R.id.bn_new_test4).setOnClickListener(this);
        findViewById(R.id.bn_new_test5).setOnClickListener(this);
        findViewById(R.id.bn_new_test6).setOnClickListener(this);
        findViewById(R.id.bn_new_test7).setOnClickListener(this);
        findViewById(R.id.bn_new_test9).setOnClickListener(this);
        findViewById(R.id.bn_new_test10).setOnClickListener(this);
        findViewById(R.id.bn_new_test11).setOnClickListener(this);
        findViewById(R.id.bn_new_test12).setOnClickListener(this);
        findViewById(R.id.bn_new_test13).setOnClickListener(this);
        findViewById(R.id.bn_new_test14).setOnClickListener(this);
        findViewById(R.id.bn_new_test15).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_new_test1:
                mVolume.setTouchExpandable(false);
                break;
            case R.id.bn_new_test2:
                mVolume.setTouchExpandable(true);
                break;
            case R.id.bn_new_test3:
                mVolume.setClickValid(false);
                break;
            case R.id.bn_new_test4:
                mVolume.setClickValid(true);
                break;
            case R.id.bn_new_test5:
                mVolume.setViewGravity(Gravity.TOP);
                break;
            case R.id.bn_new_test6:
                mVolume.setViewGravity(Gravity.CENTER);
                break;
            case R.id.bn_new_test7:
                mVolume.setViewGravity(Gravity.BOTTOM);
                break;
            case R.id.bn_new_test9:
                mVolume.setFullWidth(false);
                break;
            case R.id.bn_new_test10:
                mVolume.setFullWidth(true);
                break;
            case R.id.bn_new_test11:
                mVolume.setColorBackground(MathUtil.getInstance().getRandColor());
                break;
            case R.id.bn_new_test12:
                mVolume.setColorForeground(MathUtil.getInstance().getRandColor());
                break;
            case R.id.bn_new_test13:
                mVolume.setColorBall(MathUtil.getInstance().getRandColor());
                break;
            case R.id.bn_new_test14:
                mVolume.setPromptly(false);
                break;
            case R.id.bn_new_test15:
                mVolume.setPromptly(true);
                break;
        }
        mVolume.show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mTvShowSeekBar.setText(String.format("SeekBar: %s", String.valueOf(progress)));
        mVolume.setPercent(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void progress(int percent) {
        mTvShowVolume.setText(String.format("Volume: %s", String.valueOf(percent)));
    }
}
