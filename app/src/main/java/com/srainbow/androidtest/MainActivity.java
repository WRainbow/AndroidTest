package com.srainbow.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.srainbow.androidtest.activity.LoadActivity;
import com.srainbow.androidtest.activity.NewActivity;
import com.srainbow.androidtest.base.BaseActivity;
import com.srainbow.androidtest.inter.IProgressListener;
import com.srainbow.androidtest.util.MathUtil;
import com.srainbow.androidtest.widget.VolumeView;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("AndroidDemo测试");
        }

        findViewById(R.id.bn_main_test1).setOnClickListener(this);
        findViewById(R.id.bn_main_test2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_main_test1:
                startActivity(new Intent(MainActivity.this, NewActivity.class));
                break;
            case R.id.bn_main_test2:
                startActivity(new Intent(MainActivity.this, LoadActivity.class));
                break;
        }
    }
}
