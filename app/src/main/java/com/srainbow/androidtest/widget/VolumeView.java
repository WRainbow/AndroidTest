package com.srainbow.androidtest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.srainbow.androidtest.inter.IProgressListener;

/**
 * Created by SRainbow on 2018/4/18.<br>
 * 音量进度条
 */
public class VolumeView extends View {
    /**
     * 是否扩大点击范围（默认为false）<br>
     * true - 点击位置在整个View范围内都会响应<br>
     * false - 点击位置只有在绘制范围内才会响应
     */
    private boolean mTouchExpandable = false;
    /**
     * 是否响应点击事件（默认为false）<br>
     * true - 点击时可以立即定位到点击位置<br>
     * false - 点击不定位到点击位置
     */
    private boolean mClickValid = false;
    /**
     * 是否宽度占满控件（默认为false）<br>
     * true - 宽度占满控件<br>
     * false - 左右留白
     */
    private boolean mFullWidth = false;
    /**
     * 是否立即响应（默认为true）<br>
     * true - 当手动设置进度时也会进行{@link IProgressListener#progress}回调<br>
     * false - 手动设置进度时不会回调
     */
    private boolean mPromptly = true;

    /**
     * 进度条状态<br>
     * SILENCE - 静止<br>
     * READY - 准备就绪<br>
     * MOVE - 移动
     */
    private enum State {
        SILENCE,
        READY,
        MOVE
    }

    private final static int sDefaultViewHeight = 25; //默认绘制高度（dp）
    private final static int sDefaultColorBackground = Color.parseColor("#021838");
    private final static int sDefaultColorForeground = Color.parseColor("#91B73D");
    private final static int sDefaultColorBall = Color.parseColor("#3C8FF3");
    private static float mDensity;
    private int mPointX = 0;
    private float[] mLastPoint;
    private boolean isM = false; //是否小球坐标x已经到达最小或最大值
    private State state = State.SILENCE;
    private int mViewGravity = Gravity.BOTTOM;
    private Context mContext; //上下文对象
    private int mWidth; //控件宽度
    private int mHeight; //控件高度
    private int mViewWidth; //绘制宽度
    private int mViewHeight; //绘制高度
    private int mRadius; //小球半径
    private int mBlank; //留白距离
    private Paint mPaintBackground; //背景画笔
    private int mColorBackground; //背景色
    private Path mPathBackground; //背景Path
    private Paint mPaintForeground; //前景画笔
    private int mColorForeground; //前景色
    private Path mPathForeground; //前景Path
    private Paint mPaintBall; //小球画笔
    private int mColorBall; //小球颜色
    private Path mPathBall; //小球path
    private Region mRegionGlobal; //全局Region
    private Region mRegionView; //小球Region
    /**
     * 由于在进行绘制时进行了画布的平移，所以需要使用Matrix进行坐标变换，
     * 在进行绘制时赋值（将平移后的画布Matrix进行逆变换）。<br><br>
     * 在判断点击位置是否在指定区域时需要获取点击的屏幕位置（getRawX()、getRawY()），
     * 然后使用此Matrix进行转换为平移后的坐标。
     */
    private Matrix mInvertMatrix;
    private IProgressListener mProgressListener;

    public VolumeView(Context context) {
        super(context);
        init(context);
    }

    public VolumeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    //初始化
    private void init(Context context) {
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        this.mContext = context;
        initData();
        initPath();
        initPaint();
        mRegionView = new Region();
        mInvertMatrix = new Matrix();
    }

    //初始化数据
    private void initData() {
        mDensity = mContext.getResources().getDisplayMetrics().density;
        mColorBackground = sDefaultColorBackground;
        mColorForeground = sDefaultColorForeground;
        mColorBall = sDefaultColorBall;

        mViewHeight = (int) (sDefaultViewHeight * mDensity);
        mViewWidth = 0;
        mLastPoint = new float[2];
    }

    //初始化画笔
    private void initPaint() {
        mPaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBackground.setColor(mColorBackground);
        mPaintBackground.setStyle(Paint.Style.FILL);

        mPaintForeground = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintForeground.setColor(mColorForeground);
        mPaintForeground.setStyle(Paint.Style.FILL);

        mPaintBall = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBall.setColor(mColorBall);
        mPaintBall.setStyle(Paint.Style.FILL);

    }

    //初始化Path
    private void initPath() {
        mPathBackground = new Path();
        mPathForeground = new Path();
        mPathBall = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mViewWidth = w;
        //如果绘制高度比控件高度大，则改为控件高度
        if (mHeight < mViewHeight) mViewHeight = mHeight;
        mRegionGlobal = new Region(-w, -h, w, h);
        mRadius = mViewHeight;
        mBlank = mRadius;

        mInvertMatrix.reset();
        setBackgroundPath();
    }

    private void setBackgroundPath() {
        mPathBackground.reset();
        if (mFullWidth) {
            mPathBackground.moveTo(0, 0);
            mPathBackground.lineTo(mViewWidth, 0);
            mPathBackground.lineTo(mViewWidth, -mViewHeight);
        } else {
            mPathBackground.moveTo(mBlank, 0);
            mPathBackground.lineTo(mViewWidth - mBlank, 0);
            mPathBackground.lineTo(mViewWidth - mBlank, -mViewHeight);
        }
        mPathBackground.close();
        mRegionView.setPath(mPathBackground, mRegionGlobal);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mViewWidth - mViewHeight * 2 <= 10) {
            return;
        }
        switch (mViewGravity) {
            case Gravity.TOP:
                canvas.translate(0, mViewHeight);
                break;
            case Gravity.CENTER:
                canvas.translate(0, mHeight / 2);
                break;
            case Gravity.BOTTOM:
                canvas.translate(0, mHeight);
                break;
        }
        // 获取测量矩阵(逆矩阵)
        if (mInvertMatrix.isIdentity()) {
            mInvertMatrix.reset();
            canvas.getMatrix().invert(mInvertMatrix);
        }
        drawBackGround(canvas);
        if (mFullWidth) {
            drawForeground(canvas, mPointX);
        } else {
            if (mPointX > mRadius) {
                drawForeground(canvas, mPointX);
            }
        }
        drawBall(canvas, mPointX);
    }

    //画背景色
    private void drawBackGround(Canvas canvas) {
        canvas.drawPath(mPathBackground, mPaintBackground);
    }

    //画小球
    private void drawBall(Canvas canvas, int x) {
        mPathBall.reset();
        if (!mFullWidth) {
            if (x < mBlank) x = mBlank;
            if (x > mViewWidth - mBlank) x = mViewWidth - mBlank;
        }
        float y = Math.abs(getPointFOnLine(x).y * 2);
        if (y < 5) {
            y = 15;
            mPathBall.addCircle(x, 0, y / 4, Path.Direction.CCW);
        } else {
            mPathBall.addCircle(x, -y / 4, y / 4, Path.Direction.CCW);
        }
        canvas.drawPath(mPathBall, mPaintBall);
    }

    //画前景色
    private void drawForeground(Canvas canvas, int x) {
        mPathForeground.reset();
        if (mFullWidth) {
            mPathForeground.moveTo(0, 0);
            mPathForeground.lineTo(x, 0);
            mPathForeground.lineTo(x, -Math.abs(getPointFOnLine(x).y));
        } else {
            if (x < mBlank) x = mBlank;
            if (x > mViewWidth - mBlank) x = mViewWidth - mBlank;
            mPathForeground.moveTo(mBlank, 0);
            mPathForeground.lineTo(x, 0);
            mPathForeground.lineTo(x, -Math.abs(getPointFOnLine(x).y));
        }
        mPathForeground.close();
        canvas.drawPath(mPathForeground, mPaintForeground);
    }

    /**
     * 计算斜线上点的坐标<br>
     * 左右留白时根据已知点 (mRadius, 0) 和 (mViewWidth - mRadius, mViewHeight)计算<br>
     * 全屏时根据已知点 (0, 0) 和 (mViewWidth, mViewHeight)计算<br>
     * 直线一般方程：(y2 - y1) * x + (x1 - x2) * y + (x2 * y1 - x1 * y2) = 0;
     *
     * @param x 点的横坐标
     * @return 点的坐标
     */
    private PointF getPointFOnLine(float x) {
        float x1 = mFullWidth ? 0 : mBlank;
        float y1 = 0;
        float x2 = mFullWidth ? mViewWidth : mViewWidth - mBlank;
        float y2 = mViewHeight;
        float y = ((x1 * y2 - x2 * y1) - (y2 - y1) * x) / (x1 - x2);
        return new PointF(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (state != State.SILENCE) {
                    state = State.SILENCE;
                    return false;
                }
                mLastPoint[0] = x;
                mLastPoint[1] = y;
                mInvertMatrix.mapPoints(mLastPoint);
                //如果点击的位置在显示区域才响应
                boolean isInGlobal = isInRegion(mRegionGlobal, mLastPoint);
                boolean isInView = isInRegion(mRegionView, mLastPoint);
                if (mTouchExpandable) {
                    if (isInGlobal && mClickValid) {
                        state = State.READY;
                    }
                    return isInGlobal;
                } else {
                    if (isInView && mClickValid) {
                        state = State.READY;
                    }
                    return isInView;
                }
            case MotionEvent.ACTION_MOVE:
                state = State.MOVE;
                mPointX += x - mLastPoint[0];
                mLastPoint[0] = x;
                mLastPoint[1] = y;
                mInvertMatrix.mapPoints(mLastPoint);
                if (formatPointX()) {
                    return false;
                }
                if (mProgressListener != null)
                    mProgressListener.progress(getPercent(getNow(), getSum()));
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (state == State.READY) {
                    mPointX = (int) mLastPoint[0];
                    if (!formatPointX()) {
                        if (mProgressListener != null)
                            mProgressListener.progress(getPercent(getNow(), getSum()));
                        invalidate();
                    }
                }
                state = State.SILENCE;
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 修正mPointX数据，第一次修正后会执行重绘<br>
     * 小于最小值、大于最大值都需要修正
     *
     * @return 是否进行了修正
     */
    public boolean formatPointX() {
        int blank = mFullWidth ? 0 : mBlank;
        if (mPointX < blank || mPointX > mViewWidth - blank) {
            //如果mPointX坐标小于最小值，则保持为最小值
            if (mPointX < blank) mPointX = blank;
            //如果mPointX坐标大于最大值，则保持为最大值
            if (mPointX > mViewWidth - blank) mPointX = mViewWidth - blank;
            //如果上一次mPointX坐标已经达到了最小值或者最大值，则不进行重绘（不知道算不算提高性能QAQ...）
            if (!isM) {
                if (mProgressListener != null)
                    mProgressListener.progress(getPercent(getNow(), getSum()));
                invalidate();
            }
            isM = true;
            return true;
        } else {
            isM = false;
            return false;
        }
    }

    private int getPercent(int now, int sum) {
        return (int) Math.round(now * 1.0 / sum * 100);
    }

    //获取当前绘制宽度
    private int getSum() {
        return mFullWidth ? mViewWidth : mViewWidth - mBlank * 2;
    }

    private int getNow() {
        int now;
        if (mFullWidth) {
            now = mPointX;
        } else {
            now = mPointX - mBlank;
            if (now < 0) now = 0;
            if (now > mViewWidth - mBlank * 2) now = mViewWidth - mBlank * 2;
        }
        return now;
    }

    //判断点击位置在不在指定范围内
    private boolean isInRegion(Region region, int x, int y) {
        return region.contains(x, y);
    }

    //判断点击位置在不在指定范围内
    private boolean isInRegion(Region region, int[] point) {
        return region.contains(point[0], point[1]);
    }

    //判断点击位置在不在指定范围内
    private boolean isInRegion(Region region, float[] point) {
        return region.contains((int) point[0], (int) point[1]);
    }

    //设置点击范围是整个View区域还是绘制View区域
    public void setTouchExpandable(boolean touchExpandable) {
        this.mTouchExpandable = touchExpandable;
    }

    //设置点击是否立即定位到指定位置
    public void setClickValid(boolean clickValid) {
        this.mClickValid = clickValid;
    }

    //设置在控件中的显示位置
    public void setViewGravity(int gravity) {
        this.mViewGravity = gravity;
        //重新设置好显示位置后需要重置矩阵
        mInvertMatrix.reset();
    }

    //设置绘制区域是否占满宽度
    public void setFullWidth(boolean fullWidth) {
        this.mFullWidth = fullWidth;
        setBackgroundPath();
    }

    //显示View（在使用代码设置属性后需要调用此方法生效）
    public void show() {
        if (mProgressListener != null) mProgressListener.progress(getPercent(getNow(), getSum()));
        invalidate();
    }

    //设置进度条背景色
    public void setColorBackground(@ColorInt int mColorBackground) {
        this.mPaintBackground.setColor(mColorBackground);
    }

    //设置进度条前景色
    public void setColorForeground(@ColorInt int mColorForeground) {
        this.mPaintForeground.setColor(mColorForeground);
    }

    //设置进度条小球颜色
    public void setColorBall(@ColorInt int mColorBall) {
        this.mPaintBall.setColor(mColorBall);
    }

    //设置当前百分比
    public void setPercent(int percent) {
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;
        if (mFullWidth) {
            mPointX = (int) (percent * 1.0 / 100 * mViewWidth);
        } else {
            mPointX = mBlank + (int) (percent * 1.0 / 100 * (mViewWidth - mBlank * 2));
            if (mPointX < mBlank) mPointX = mBlank;
            if (mPointX > mViewWidth - mBlank) mPointX = mViewWidth - mBlank;
        }
        if (mPromptly && mProgressListener != null) {
//            mProgressListener.progress(getPercent(getNow(), getSum()));
            mProgressListener.progress(percent);
        }
        invalidate();
    }

    //设置进度条监听
    public void setProgressListener(IProgressListener mProgressListener) {
        this.mProgressListener = mProgressListener;
    }

    //手动设置进度时是否进行回调
    public void setPromptly(boolean promptly) {
        this.mPromptly = promptly;
    }
}
