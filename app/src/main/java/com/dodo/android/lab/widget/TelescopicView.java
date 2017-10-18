package com.dodo.android.lab.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义伸缩视图
 */

public class TelescopicView extends View {

    // 聲明一隻畫筆
    private Paint mPaint;
    // 圖像的背景色
    private int backgroundColor = Color.parseColor("#50000000");
    // 圓的字體顏色
    private int circleTextColor = Color.WHITE;
    // 圓的字體大小
    private float circleTextSize = 24;
    // 圓角矩形的x坐標
    private float rectX = 0;
    // 圓角矩形的起始坐標
    private int startRectX = 0;
    // 動畫的持續時間,默認300毫秒
    private long duration = 300;
    // 線程休眠時間
    private long sleepTime = 16;
    // 線程停頓時間
    private long stayTime = 4000;
    // 圓角矩形X坐標移動速度，默認8像素
    private float movingSpeedX = 8;
    // 感應加速度,最多加到原速度的一半
    private float acceleration = 0;
    // 繪製圓的文字
    private String circleText = null;
    // 矩形上方的文字
    private String rectAboveText = null;
    // 矩形下方的文字
    private String rectBelowText = null;
    // 矩形上方的文字顏色
    private int rectAboveTextColor = Color.WHITE;
    // 矩形下方的文字顏色
    private int rectBelowTextColor = Color.WHITE;
    // 矩形上方的文字大小
    private float rectAboveTextSize = 24;
    // 矩形下方的文字大小
    private float rectBelowTextSize = 20;
    // 矩形上方文字與下方文字間的間距
    private float rectTextSpacing = 10;
    // 是否繪製圓
    private boolean isDrawCircle = true;
    // 聲明一個線程
    private Thread mThread;

    public TelescopicView(Context context) {
        super(context);
        init();
    }

    public TelescopicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TelescopicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        //指定填充颜色
        mPaint.setColor(Color.WHITE);
        // 防抖动
        mPaint.setDither(true);
        // 抗锯齿
        mPaint.setAntiAlias(true);
        //描边
        mPaint.setStyle(Paint.Style.STROKE);
        // 指定填充模式
        mPaint.setStyle(Paint.Style.FILL);
        // 文字水平居中
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            if (canvas != null) {
                int width = getMeasuredWidth();
                int height = getMeasuredHeight();
                startRectX = width - height;
                int radius = height / 2;
                if (isDrawCircle) {
                    // 绘制圆
                    mPaint.setColor(backgroundColor);
                    canvas.drawCircle(width - radius, height - radius, radius, mPaint);
                    // 绘制文字
                    if (!TextUtils.isEmpty(circleText)) {
                        mPaint.setColor(circleTextColor);
                        mPaint.setTextSize(circleTextSize);
                        mPaint.setTextAlign(Paint.Align.CENTER);
                        canvas.drawText(circleText, width - radius, height - radius + circleTextSize, mPaint);
                        // 繪製不規則矩形圖標
                        float rWidth = radius / 2.0f;
                        float tHeight = rWidth * 1.3f - rWidth;
                        // 繪製圓角矩形
                        RectF rectF = new RectF(width - radius - rWidth / 2, height - radius - rWidth - tHeight,
                                width - radius + rWidth / 2, height - radius - tHeight);
                        canvas.drawRoundRect(rectF, 3, 3, mPaint);
                        canvas.drawRect(rectF, mPaint);
                        // 繪製实心三角形
                        mPaint.setStyle(Paint.Style.FILL);
                        Path path;
                        path = new Path();
                        path.moveTo(width - radius - rWidth / 2, height - radius - tHeight - 1);
                        path.lineTo(width - radius, height - radius - tHeight - 1);
                        path.lineTo(width - radius - rWidth / 2, height - radius - 1);
                        path.close();
                        canvas.drawPath(path, mPaint);
                        // 右側三角形
                        path = new Path();
                        path.moveTo(width - radius + rWidth / 2, height - radius - tHeight - 1);
                        path.lineTo(width - radius, height - radius - tHeight - 1);
                        path.lineTo(width - radius + rWidth / 2, height - radius - 1);
                        path.close();
                        canvas.drawPath(path, mPaint);
                    }
                } else {
                    RectF rectF = new RectF(rectX, 0, width, height);
                    mPaint.setColor(backgroundColor);
                    mPaint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawRoundRect(rectF, radius, radius, mPaint);
                    if (!TextUtils.isEmpty(rectAboveText)) {
                        mPaint.setColor(rectAboveTextColor);
                        mPaint.setTextSize(rectAboveTextSize);
                        canvas.drawText(rectAboveText, rectX + radius, rectAboveTextSize
                                + height / 4.0f - rectTextSpacing / 2, mPaint);
                    }
                    if (!TextUtils.isEmpty(rectBelowText)) {
                        mPaint.setColor(rectBelowTextColor);
                        mPaint.setTextSize(rectBelowTextSize);
                        canvas.drawText(rectBelowText, rectX + radius, rectBelowTextSize
                                + rectAboveTextSize + height / 4.0f + rectTextSpacing / 2, mPaint);
                    }
                }
            }
        }
    }

    /**
     * 設置視圖背景顏色值
     *
     * @param backgroundColor
     */
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        postInvalidate();
    }

    /**
     * 设置圆的字体颜色
     *
     * @param textColor
     */
    public void setCircleTextColor(int textColor) {
        this.circleTextColor = textColor;
        postInvalidate();
    }

    /**
     * 設置圓的字體大小
     *
     * @param textSize
     */
    public void setCircleTextSize(float textSize) {
        this.circleTextSize = textSize;
        postInvalidate();
    }

    /**
     * 設置圓的文字
     *
     * @param text
     */
    public void setCircleText(String text) {
        circleText = text;
        postInvalidate();
    }

    /**
     * 设置矩形下方的文字
     *
     * @param rectAboveText
     */
    public void setRectAboveText(String rectAboveText) {
        this.rectAboveText = rectAboveText;
        postInvalidate();
    }

    /**
     * 设置矩形下方文字
     *
     * @param rectBelowText
     */
    public void setRectBelowText(String rectBelowText) {
        this.rectBelowText = rectBelowText;
        postInvalidate();
    }

    /**
     * 設置矩形上方的文字顏色
     *
     * @param rectAboveTextColor
     */
    public void setRectAboveTextColor(int rectAboveTextColor) {
        this.rectAboveTextColor = rectAboveTextColor;
    }

    /**
     * 設置矩形下方的文字顏色
     *
     * @param rectBelowTextColor
     */
    public void setRectBelowTextColor(int rectBelowTextColor) {
        this.rectBelowTextColor = rectBelowTextColor;
    }

    /**
     * 設置矩形下方的文字大小
     *
     * @param rectBelowTextSize
     */
    public void setRectBelowTextSize(float rectBelowTextSize) {
        this.rectBelowTextSize = rectBelowTextSize;
    }

    /**
     * 設置矩形上方的文字大小
     *
     * @param rectAboveTextSize
     */
    public void setRectAboveTextSize(float rectAboveTextSize) {
        this.rectAboveTextSize = rectAboveTextSize;
    }

    /**
     * 設置矩形上方文字和下方文字的間距
     *
     * @param rectTextSpacing
     */
    public void setRectTextSpacing(float rectTextSpacing) {
        this.rectTextSpacing = rectTextSpacing;
    }

    /**
     * 設置動畫持續時間,單位毫秒
     *
     * @param duration
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 開啟動畫
     */
    public void startAnimation() {
        if (mThread == null) {
            rectX = startRectX;
            movingSpeedX = startRectX / ((1000 / sleepTime) * (duration / 1000f));
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        isDrawCircle = false;
                        acceleration = 0;
                        // 向左移動
                        while (rectX > 0) {
                            rectX = rectX - movingSpeedX - acceleration;
                            if (acceleration < movingSpeedX / 2) {
                                acceleration++;
                            }
                            if (rectX > 0) {
                                postInvalidate();
                                Thread.sleep(sleepTime);
                            }
                        }
                        // 停止移動，停留一段時間
                        rectX = 0;
                        acceleration = 0;
                        postInvalidate();
                        Thread.sleep(stayTime);
                        // 向右移動
                        while (rectX < startRectX) {
                            rectX = rectX + movingSpeedX + acceleration;
                            if (acceleration < movingSpeedX / 2) {
                                acceleration++;
                            }
                            if (rectX < startRectX) {
                                postInvalidate();
                                Thread.sleep(sleepTime);
                            }
                        }
                        rectX = startRectX;
                        postInvalidate();
                        if (mThread != null) {
                            mThread.interrupt();
                            mThread = null;
                        }
                    } catch (Exception ex) {
                    } finally {
                        isDrawCircle = true;
                        postInvalidate();
                    }
                }
            });
            mThread.start();
        }
    }

}
