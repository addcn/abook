package com.dodo.android.lab.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


/**
 * Path特效之PathMeasure打造万能路径动效
 * http://blog.csdn.net/tianjian4592/article/details/47067161
 *
 */

public class MyPathMeasure extends View {

    private static final String TAG = "MyPathMeasure";
    private static final int PATH_WIDTH = 2;
    // 起始点
    private static final int[] START_POINT = new int[] {
            300, 270
    };
    // 爱心下端点
    private static final int[] BOTTOM_POINT = new int[] {
            300, 400
    };
    // 左侧控制点
    private static final int[] LEFT_CONTROL_POINT = new int[] {
            450, 200
    };
    // 右侧控制点
    private static final int[] RIGHT_CONTROL_POINT = new int[] {
            150, 200
    };

    private PathMeasure mPathMeasure;
    private Paint mPaint;
    private Path mPath;
    private float[] mCurrentPosition = new float[2];

    /**
     * New一个View的时候调用
     *
     * @param context
     */
    public MyPathMeasure(Context context) {
        super(context);
        init();
    }

    /**
     * layout文件中使用的时候会调用
     *
     * @param context
     * @param attrs
     */
    public MyPathMeasure(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyPathMeasure(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.STROKE);
        mPaint.setStrokeWidth(PATH_WIDTH);
        mPaint.setColor(Color.RED);

        mPath = new Path();
        mPath.moveTo(START_POINT[0], START_POINT[1]);
        mPath.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0],BOTTOM_POINT[1]);
        mPath.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);

        mPathMeasure = new PathMeasure(mPath, true);
        mCurrentPosition = new float[2];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null); //关闭硬件加速
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);

        canvas.drawCircle(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], 5, mPaint);
        canvas.drawCircle(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], 5, mPaint);

        // 绘制对应目标
        canvas.drawCircle(mCurrentPosition[0], mCurrentPosition[1], 10, mPaint);

       startPathAnim(2000);
    }

    // 开启路径动画
    public void startPathAnim(long duration) {
        // 0 － getLength()
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        Log.i(TAG, "measure length = " + mPathMeasure.getLength());
        valueAnimator.setDuration(duration);
        // 减速插值器
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}
