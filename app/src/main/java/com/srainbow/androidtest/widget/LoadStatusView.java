package com.srainbow.androidtest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.srainbow.androidtest.R;


/**
 * Created by SRainbow on 2018/4/23.
 * 数据加载状态显示View
 */
public class LoadStatusView extends RelativeLayout {
    private Context mContext; //上下文对象
    private SparseArray<View> mStatusWithView; //保存状态对应的布局
    private SparseIntArray mStatusWithImg; //保存状态对应的图片
    private ImageView mImage; //显示图片的View
    private int currentStatus;

    public LoadStatusView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LoadStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoadStatusView);
        int noData = ta.getResourceId(R.styleable.LoadStatusView_view_no_data, R.layout.layout_no_data);
        addStatusAndView(R.id.STATUS_NO_DATA, noData);
        int noNet = ta.getResourceId(R.styleable.LoadStatusView_view_no_net, R.layout.layout_no_net);
        addStatusAndView(R.id.STATUS_NO_NET, noNet);
        int loadError = ta.getResourceId(R.styleable.LoadStatusView_view_load_error, R.layout.layout_load_error);
        addStatusAndView(R.id.STATUS_LOAD_ERROR, loadError);
        noData = ta.getResourceId(R.styleable.LoadStatusView_img_no_data, R.mipmap.ic_no_data);
        addStatusAndImg(R.id.STATUS_NO_DATA, noData);
        noNet = ta.getResourceId(R.styleable.LoadStatusView_img_no_net, R.mipmap.ic_no_net);
        addStatusAndImg(R.id.STATUS_NO_NET, noNet);
        loadError = ta.getResourceId(R.styleable.LoadStatusView_img_load_error, R.mipmap.ic_load_error);
        addStatusAndImg(R.id.STATUS_LOAD_ERROR, loadError);
        ta.recycle();
    }

    //初始化
    private void init(Context context) {
        mContext = context;
        initViewData(context);
        initImgData(context);
        this.setVisibility(GONE);
    }

    //初始化View数据
    public void initViewData(Context context) {
        View noDataView = LayoutInflater.from(context).inflate(R.layout.layout_no_data, null);
        addFullParams(noDataView);
        View noNetView = LayoutInflater.from(context).inflate(R.layout.layout_no_net, null);
        addFullParams(noNetView);
        View loadErrorView = LayoutInflater.from(context).inflate(R.layout.layout_load_error, null);
        addFullParams(loadErrorView);
        noDataView.setVisibility(GONE);
        noNetView.setVisibility(GONE);
        loadErrorView.setVisibility(GONE);
        addStatusAndView(R.id.STATUS_NO_DATA, noDataView);
        addStatusAndView(R.id.STATUS_NO_NET, noNetView);
        addStatusAndView(R.id.STATUS_LOAD_ERROR, loadErrorView);
    }

    //初始化图片数据
    public void initImgData(Context context) {
        mImage = new ImageView(context);
        addWrapParams(mImage);
        addStatusAndImg(R.id.STATUS_NO_DATA, R.mipmap.ic_no_data);
        addStatusAndImg(R.id.STATUS_NO_NET, R.mipmap.ic_no_net);
        addStatusAndImg(R.id.STATUS_LOAD_ERROR, R.mipmap.ic_load_error);
        mImage.setVisibility(GONE);
        addView(mImage);
    }

    //添加全屏的LayoutParams
    private void addFullParams(View view) {
        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        view.setLayoutParams(params);
    }

    //添加自适应的LayoutParams
    private void addWrapParams(View view) {
        RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        view.setLayoutParams(params);
    }

    //添加对应显示状态和对应状态显示的View
    public void addStatusAndView(@IdRes int status, View show) {
        if (mStatusWithView == null) {
            mStatusWithView = new SparseArray<>();
        }
        if (show != null) {
            show.setVisibility(GONE);
            mStatusWithView.put(status, show);
            addView(show);
        }
    }

    //添加对应显示状态和对应状态显示的View
    public void addStatusAndView(@IdRes int status, int resId) {
        //为了防止传入的资源ID不为布局id时加载出错
        try {
            if (mStatusWithView == null) {
                mStatusWithView = new SparseArray<>();
            }
            View show = LayoutInflater.from(mContext).inflate(resId, null);
            if (show != null) {
                addFullParams(show);
                show.setVisibility(GONE);
                mStatusWithView.put(status, show);
                addView(show);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加状态和对应的图片
    public void addStatusAndImg(@IdRes int status, @DrawableRes int resource) {
        if (mStatusWithImg == null) {
            mStatusWithImg = new SparseIntArray();
        }
        mStatusWithImg.put(status, resource);
    }

    //根据状态显示对应的View
    public void showViewByStatus(@IdRes int status) {
        View findView = mStatusWithView.get(status);
        if (findView != null) {
            View findLastView = mStatusWithView.get(currentStatus);
            if (findLastView != null) {
                //如果上次状态和这次状态相同，且该状态View已经显示则直接返回
                if (currentStatus == status && findLastView.getVisibility() == VISIBLE) {
                    return;
                }
                findLastView.setVisibility(GONE);
            }
            hideImg();
            hideView();
            findView.setVisibility(VISIBLE);
            this.setVisibility(VISIBLE);
        }
        currentStatus = status;
    }

    //根据状态显示图片
    public void showImgByStatus(@IdRes int status) {
        if (mImage != null) {
            if (mStatusWithImg.get(status) != 0) {
                //如果上次状态和这次状态相同，且该状态View已经显示则直接返回
                if (currentStatus == status && mImage.getVisibility() == VISIBLE) {
                    return;
                }
                hideView();
                mImage.setVisibility(VISIBLE);
                //为了防止传入的资源ID不为图片资源id时加载出错
                try {
                    mImage.setImageResource(mStatusWithImg.get(status));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.setVisibility(VISIBLE);
            }
        }
        currentStatus = status;
    }

    //隐藏加载状态View
    public void hideLoadStatus() {
        hideImg();
        hideView();
        this.setVisibility(GONE);
    }

    //隐藏View列表
    private void hideView() {
        for (int i = 0; i < mStatusWithView.size(); i ++) {
            mStatusWithView.get(mStatusWithView.keyAt(i)).setVisibility(GONE);
        }
    }

    //隐藏ImageView
    private void hideImg() {
        mImage.setVisibility(GONE);
    }
}
