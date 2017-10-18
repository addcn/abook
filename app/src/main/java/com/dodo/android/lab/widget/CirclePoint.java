package com.dodo.android.lab.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 10113 on 2017/9/21.
 */

public class CirclePoint extends View {

    // 圆的画笔
    private Paint paint;
    // 圆的直径
    private float circleWidth = 100;
    // 圆的颜色
    private int circleColor = Color.BLUE;

    // 视图文字
    private String text = null;
    // 字体大小
    private float textSize = 24;
    // 字体颜色
    private int textColor = Color.WHITE;

    public CirclePoint(Context context) {
        super(context);
        init(context, null);
    }

    public CirclePoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CirclePoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attributeSet
     */
    private void init(Context context, AttributeSet attributeSet) {
        paint = new Paint();
        // 设置颜色
        paint.setColor(circleColor);
        // 防抖动
        paint.setDither(true);
        // 抗锯齿
        paint.setAntiAlias(true);
        // 描边
        paint.setStyle(Paint.Style.STROKE);
        // 指定填充模式
        paint.setStyle(Paint.Style.FILL);
        // 文字水平居中
        paint.setTextAlign(Paint.Align.CENTER);
        /*if (attributeSet != null) {
            TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.CirclePoint, 0, 0);
            if (attr == null) {
                return;
            }
            try {
                circleWidth = attr.getDimensionPixelSize(R.styleable.CirclePoint_circle_width, 0);
                circleColor = attr.getColor(R.styleable.CirclePoint_circle_color, Color.WHITE);
                text = attr.getString(R.styleable.CirclePoint_circle_text);
                textSize = attr.getDimensionPixelSize(R.styleable.CirclePoint_circle_text_size, 0);
                textColor = attr.getColor(R.styleable.CirclePoint_circle_text_color, Color.WHITE);
            } finally {
                attr.recycle();
            }
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * cx：圆心的x坐标。
         * cy：圆心的y坐标。
         * radius：圆的半径。
         * paint：绘制时所使用的画笔。
         */
        if (canvas != null) {
            if (paint != null) {
                float radius = circleWidth / 2;
                paint.setColor(circleColor);
                canvas.drawCircle(radius, radius, radius, paint);
                // 传了文字则绘制文字
                if (!TextUtils.isEmpty(text)) {
                    paint.setTextSize(textSize);
                    paint.setColor(textColor);
                    Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                    int baseline = (int) (circleWidth - fontMetrics.bottom - fontMetrics.top) / 2;
                    canvas.drawText(text, radius, baseline, paint);
                }
            }
        }
    }

    /**
     * 设置圆的直径
     *
     * @param circleWidth 视图宽度
     */
    public void setCircleWidth(float circleWidth) {
        this.circleWidth = circleWidth;
        postInvalidate();
    }

    /**
     * 设置圆的颜色
     *
     * @param circleColor
     */
    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        postInvalidate();
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        postInvalidate();
    }

    /**
     * 设置字体大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

    /**
     * 设置字体颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }

}

