package com.sk.xjwd.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tencent.smtt.sdk.WebChromeClient;


/**
 * Created by mayn on 2018/9/3.
 */

public class MyWebView extends WebView {

    private Activity act;
    private boolean isMove;
    // 手指向右滑动时的最小速度
    private static final int XSPEED_MIN = 200;

    // 手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 400;

    // 记录手指按下时的横坐标。
    private float xDown;

    // 记录手指移动时的横坐标。
    private float xMove;

    // 用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;

    Context mContext;


    public MyWebView(Context context) {
        super(context);
        this.mContext = context;
        init();

    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);// 设置滚动条的宽度
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(false);
        webSettings.setSavePassword(false);
        webSettings.setSupportZoom(false);
        webSettings.setGeolocationEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                // 活动的距离
                int distanceX = (int) (xMove - xDown);
                // 获取顺时速度
                int xSpeed = getScrollVelocity();
                // 当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
                if (distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
                    if (isMove) {
                        act.finish();
                    }
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.resumeTimers();
    }

}
