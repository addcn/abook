package com.dodo.android.abook.widget;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * eclipse_xu
 * http://www.jianshu.com/p/3efa5341abcc
 * <p>
 * 路径动画 PathMeasure
 * <p/>
 * Created by xuyisheng on 16/7/15.
 */
public class PathMeasurePractice1 extends View {

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLength;

    public PathMeasurePractice1(Context context) {
        super(context);
    }

    public PathMeasurePractice1(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPathMeasure = new PathMeasure();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPath = new Path();
        mPath.addCircle(400, 400, 100, Path.Direction.CW);
        mPathMeasure.setPath(mPath, true);
        mLength = mPathMeasure.getLength();
        mDst = new Path();

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    public PathMeasurePractice1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0, 0);
        float stop = mLength * mAnimatorValue;
        mPathMeasure.getSegment(0, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }
}