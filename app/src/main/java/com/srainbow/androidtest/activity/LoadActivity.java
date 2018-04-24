package com.srainbow.androidtest.activity;

import android.os.Bundle;
import android.view.View;

import com.srainbow.androidtest.R;
import com.srainbow.androidtest.base.BaseActivity;
import com.srainbow.androidtest.widget.LoadStatusView;

public class LoadActivity extends BaseActivity implements View.OnClickListener {

    private LoadStatusView mLoadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        mLoadView = findViewById(R.id.lsv_load);
        findViewById(R.id.bn_load_test1).setOnClickListener(this);
        findViewById(R.id.bn_load_test2).setOnClickListener(this);
        findViewById(R.id.bn_load_test3).setOnClickListener(this);
        findViewById(R.id.bn_load_test4).setOnClickListener(this);
        findViewById(R.id.bn_load_test5).setOnClickListener(this);
        findViewById(R.id.bn_load_test6).setOnClickListener(this);
        findViewById(R.id.bn_load_test7).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bn_load_test1:
                mLoadView.showViewByStatus(R.id.STATUS_NO_DATA);
                break;
            case R.id.bn_load_test2:
                mLoadView.showViewByStatus(R.id.STATUS_NO_NET);
                break;
            case R.id.bn_load_test3:
                mLoadView.showViewByStatus(R.id.STATUS_LOAD_ERROR);
                break;
            case R.id.bn_load_test4:
                mLoadView.showImgByStatus(R.id.STATUS_NO_DATA);
                break;
            case R.id.bn_load_test5:
                mLoadView.showImgByStatus(R.id.STATUS_NO_NET);
                break;
            case R.id.bn_load_test6:
                mLoadView.showImgByStatus(R.id.STATUS_LOAD_ERROR);
                break;
            case R.id.bn_load_test7:
                mLoadView.hideLoadStatus();
                break;
        }
    }
}
