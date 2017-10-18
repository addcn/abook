package com.dodo.android.lab.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 不断缩放的圆
 * {@link MyViewGroup}
 *
 * https://github.com/shaohui10086/AndroidPractise/blob/master/app/src/main/java/me/shaohui/androidpractise/widget/SketchView.java
 *
 * http://jbcdn2.b0.upaiyun.com/2016/07/c7f309f23bcaeb3fb148567666a5b73c.gif
 */
public class MyViewPractice1 extends View {

    protected Context context;
    protected Paint paint;

    protected int mWidth;
    protected int mHeight;

    // 默认值
    private final int DEFAULT_TEXT_COLOR = Color.BLUE;
    private final int DEFAULT_TEXT_SIZE = 0;


    private float scale = 1f;


    /**
     * 用于代码创建控件
     *
     * @param context
     */
    public MyViewPractice1(Context context) {
        super(context, null);
        init(context, null);
    }

    /**
     * 用于在XML中使用，可以指定自定义属性
     *
     * @param context
     * @param attrs
     */
    public MyViewPractice1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    /**
     * 用于在XML中使用，可以指定自定义属性，并指定样式
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyViewPractice1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 用于在XML中使用，可以指定自定义属性，并指定样式及其资源
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyViewPractice1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attributeSet
     */
    private void init(Context context, AttributeSet attributeSet) {
        // context
        this.context = context;

        // paint
        paint = new Paint();
        //paint.setColor(circleColor); // 设置颜色
        paint.setDither(true); // 防抖动
        paint.setAntiAlias(true); // 抗锯齿
        paint.setStyle(Paint.Style.FILL); // 填充模式
        paint.setTextAlign(Paint.Align.CENTER); // 文字水平居中

        // attr
        //initAttrs(context, attributeSet); // 自定义属性
    }

    /**
    private void initAttrs(Context context, AttributeSet attributeSet) {
        // 自定义属性
        if (attributeSet != null) {
            TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.MyView, 0, 0);
            if (attr == null) {
                return;
            }
            try {
                text = attr.getString(R.styleable.MyView_circle_text);
                textSize = attr.getDimensionPixelSize(R.styleable.MyView_circle_text_size, DEFAULT_TEXT_SIZE);
                textColor = attr.getColor(R.styleable.MyView_circle_text_color, DEFAULT_TEXT_COLOR);
            } finally {
                attr.recycle();
            }
        }
    }
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 不要 new 对象！不要 new 对象！！不要 new 对象！！！

        canvas.drawCircle(mWidth/2, mHeight/2, 100*scale, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }



    private ValueAnimator mAnimator;
    public void startAnimation() {
        mAnimator = ValueAnimator.ofFloat(1, 2);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        mAnimator.setRepeatCount(-1);// 重复次数 -1表示无限循环
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);// 重复模式, RESTART: 重新开始 REVERSE:恢复初始状态再开始
        mAnimator.start();
    }


    /**
     * 测量自定义控件的长宽
     * 需要做的就是设置setMeasuredDimension(int width, int height)。
     * 如果想要支持wrap_content属性，就必须重写onMeasure方法，如下所示（可以当做模板代码）。
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
        MeasureSpec有三种模式：
            MeasureSpec.EXACTLY表示用户设置了具体的值，所以无论你的视图有多大，都需要设置具体的宽或高。
            MeasureSpec.AT_MOST表示该视图的尺寸为其所需的具体大小。（注：wrap_content）
            MeasureSpec.UNSPECIFIED表示会包裹整个视图的大小。使用上面计算的desiredWidth或desiredHeight。（注：match_parent）
        */

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //view支持wrap_content
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // stopLoading();
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }
}
