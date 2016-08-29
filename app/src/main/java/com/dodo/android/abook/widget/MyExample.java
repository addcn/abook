package com.dodo.android.abook.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by dodo on 2016/8/25.
 */
public class MyExample extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    private int mWidth;
    private int mHeight;
    private float mDensity;

    /**
     * New一个View的时候调用
     * @param context
     */
    public MyExample(Context context) {
        super(context);
        init(null, 0);
    }

    /**
     * layout文件中使用的时候会调用
     * @param context
     * @param attrs
     */
    public MyExample(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyExample(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Paint
        mPaint.setAntiAlias(true);//指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.setStrokeWidth(1 * mDensity);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null); //关闭硬件加速
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //
        mWidth = canvas.getWidth();
        mHeight = canvas.getHeight();
        mDensity = getResources().getDisplayMetrics().density;
        //
        drawXYAxis(canvas);
        drawAxisText(canvas);
        drawNetLine(canvas);

        drawBase(canvas);
    }

    /**
     * 绘坐标系
     *
     * @param canvas
     */
    private void drawXYAxis(Canvas canvas) {
        // 配置参数
        int lenX = mWidth/2; //x轴长度
        int lenY = mHeight/2; //y轴长度
        int arrowW = 15; //坐标箭头张度
        int arrowL = 30; //坐标箭头长度

        // Point
        Point XStart = new Point(+lenX, 0); //x轴始点
        Point XEnd = new Point(-lenX, 0); //x轴末点
        Point XArrow1 = new Point(lenX - arrowL, -arrowW);//x轴箭头上点
        Point XArrow2 = new Point(lenX - arrowL, +arrowW);//x轴箭头下点

        Point YStart = new Point(0, +lenY);//y轴始点
        Point YEnd = new Point(0, -lenY);//y轴末点
        Point YArrow1 = new Point(-arrowW, lenY - arrowL);//y轴箭头左点
        Point YArrow2 = new Point(+arrowW, lenY - arrowL);//y轴箭头右点

        // Draw
        mPaint.reset();
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.BLACK);
        //mPaint.setColor(0xffffbfbf);
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2); // 坐标系原点移动到画布正中心
        canvas.drawLine(XStart.x, XStart.y, XEnd.x, XEnd.y, mPaint); //X轴线
        canvas.drawLine(XStart.x, XStart.y, XArrow1.x, XArrow1.y, mPaint); //X上箭头
        canvas.drawLine(XStart.x, XStart.y, XArrow2.x, XArrow2.y, mPaint);//X下箭头

        canvas.drawLine(YStart.x, YStart.y, YEnd.x, YEnd.y, mPaint);//Y轴线
        canvas.drawLine(YStart.x, YStart.y, YArrow1.x, YArrow1.y, mPaint);//Y左箭头
        canvas.drawLine(YStart.x, YStart.y, YArrow2.x, YArrow2.y, mPaint);//Y右箭头
        canvas.restore();
    }

    /**
     * 绘制网格
     *
     * @param canvas
     */
    private void drawNetLine(Canvas canvas) {
        mPaint.reset();
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setPathEffect(new DashPathEffect(new float[]{1, 2, 4, 8}, 2)) ;//虚线

        canvas.save();
        canvas.translate(0, 0); //原点
        canvas.drawLine(0, mHeight / 3 * 1, mWidth, mHeight / 3 * 1, mPaint);
        canvas.drawLine(0, mHeight / 3 * 2, mWidth, mHeight / 3 * 2, mPaint);
        canvas.drawLine(0, mHeight / 3 * 3, mWidth, mHeight / 3 * 3, mPaint);

        canvas.drawLine(mWidth / 3 * 1, 0, mWidth / 3 * 1, mHeight, mPaint);
        canvas.drawLine(mWidth / 3 * 2, 0, mWidth / 3 * 2, mHeight, mPaint);
        canvas.drawLine(mWidth / 3 * 3, 0, mWidth / 3 * 3, mHeight, mPaint);
        canvas.restore();
    }

    /**
     * 四格线文字
     *
     * @param canvas
     */
    private void drawAxisText(Canvas canvas)
    {
        String text = "中文：AaGg:1234";

        //1/6
        Point pos = new Point(mWidth / 6 * 1, mHeight / 6 * 1); //写字点，y为四线格的top

        /**
         * 辅助网格
         */
        //网格线画笔
        mPaint.reset();
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.RED);

        //网格线绘制
        canvas.save();
        canvas.translate(0, 0); //原点
        canvas.drawLine(0, pos.y, mWidth, pos.y, mPaint);//X轴线
        canvas.drawLine(pos.x, 0, pos.x, mHeight, mPaint);//Y轴线

        /**
         * 内容文字
         */
        //文字画笔
        mPaint.reset();
        //mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(24 * mDensity); //以px为单位
        mPaint.setTextAlign(Paint.Align.LEFT);//默认为左对齐

        //计算各线在位置
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();//FontMetricsInt对象
        int baseLineY = pos.y - fm.top;
        float ascent = baseLineY + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;

        //绘制文字
        canvas.translate(0, 0); //原点
        canvas.drawText(text, pos.x, baseLineY, mPaint);

        /**
         * 四格线
         */
        //网格线画笔
        mPaint.reset();
        mPaint.setStrokeWidth(5);

        //画基线
        mPaint.setColor(Color.RED);
        canvas.drawLine(pos.x, baseLineY, mWidth, baseLineY, mPaint);

        //画top
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(pos.x, top, mWidth, top, mPaint);

        //画ascent
        mPaint.setColor(0xff0c76c0);
        canvas.drawLine(pos.x, ascent, mWidth, ascent, mPaint);

        //画descent
        mPaint.setColor(0xfffd8403);
        canvas.drawLine(pos.x, descent, mWidth, descent, mPaint);

        //画bottom
        mPaint.setColor(0xff007f57);
        canvas.drawLine(pos.x, bottom, mWidth, bottom, mPaint);

        canvas.restore();
    }

    /**
     * 基本图形
     *
     * @param canvas
     */
    private void drawBase(Canvas canvas)
    {
        //绘制点
        //1/12
        Point pos = new Point(mWidth / 12 * 1, mHeight / 12 * 1);

        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStrokeWidth(3 * mDensity);//设置线宽，如果不设置线宽，无法绘制点
        mPaint.setStrokeCap(Paint.Cap.BUTT);

        canvas.save();
        canvas.translate(0, 0); //原点
        canvas.drawPoint(pos.x, pos.y, mPaint);
        canvas.restore();

        //绘制线段

        canvas.save();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(5);
        canvas.translate(0, 0);
        canvas.drawLine(5, mWidth/6 - 8 - 10*mDensity, mWidth/6 - 5, mWidth/6 - 8 - 10*mDensity, mPaint);//直线

        float[] pts = {
                5, mWidth/6 - 8, mWidth/6 - 5 , mWidth/6 - 8
        };
        mPaint.setColor(Color.BLUE);
        canvas.drawLines(pts, mPaint);//折线
        canvas.restore();

        //画圆
        canvas.save();
        canvas.drawCircle(mWidth / 12 * 3, mWidth / 12 * 1, 20, mPaint);
        canvas.restore();

        //矩形
        canvas.save();
        canvas.drawRect(mWidth / 12 * 4 +5* mDensity, 5* mDensity, mWidth / 12 * 6 - 5* mDensity, mHeight / 12 * 2 -5* mDensity, mPaint);
        canvas.restore();

    }

}