package com.dodo.android.abook.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
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
        drawAxis(canvas);
        drawNetLine(canvas);
        drawText(canvas);
        drawBaseShape(canvas);
    }

    /**
     * 绘坐标系
     *
     * @param canvas
     */
    private void drawAxis(Canvas canvas) {
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
        //画笔
        mPaint.reset();
        mPaint.setColor(Color.GRAY);//设置颜色
        mPaint.setStrokeWidth(1);//设置线宽
        mPaint.setStyle(Paint.Style.STROKE);//设置填充
        mPaint.setStrokeCap(Paint.Cap.BUTT);//设置笔帽
        //mPaint.setPathEffect(new DashPathEffect(new float[]{1, 2, 4, 8}, 2)) ;//设置虚线

        //画布
        canvas.save();
        canvas.translate(0, 0); //原点
        //X轴线(6等分)
        canvas.drawLine(0, mHeight / 6 * 1, mWidth, mHeight / 6 * 1, mPaint);
        canvas.drawLine(0, mHeight / 6 * 2, mWidth, mHeight / 6 * 2, mPaint);
        canvas.drawLine(0, mHeight / 6 * 3, mWidth, mHeight / 6 * 3, mPaint);
        canvas.drawLine(0, mHeight / 6 * 4, mWidth, mHeight / 6 * 4, mPaint);
        canvas.drawLine(0, mHeight / 6 * 5, mWidth, mHeight / 6 * 5, mPaint);
        //Y轴线(6等分)
        canvas.drawLine(mWidth / 6 * 1, 0, mWidth / 6 * 1, mHeight, mPaint);
        canvas.drawLine(mWidth / 6 * 2, 0, mWidth / 6 * 2, mHeight, mPaint);
        canvas.drawLine(mWidth / 6 * 3, 0, mWidth / 6 * 3, mHeight, mPaint);
        canvas.drawLine(mWidth / 6 * 4, 0, mWidth / 6 * 4, mHeight, mPaint);
        canvas.drawLine(mWidth / 6 * 5, 0, mWidth / 6 * 5, mHeight, mPaint);
        canvas.restore();
    }

    /**
     * 四格线文字
     * 默认坐标Y为基线坐标，通过计算，转换为文字左上角坐标点
     *
     * @param canvas
     */
    private void drawText(Canvas canvas)
    {
        String text = "中文：AaGg:1234";

        //6等分
        Point pos = new Point(mWidth / 6 * 1, mHeight / 6 * 5); //写字点，y为四线格的top

        /**
         * 内容文字
         */
        //画笔
        mPaint.reset();
        mPaint.setColor(Color.BLACK);//设置颜色
        //mPaint.setStrokeWidth(1);//设置线宽
        //mPaint.setStyle(Paint.Style.STROKE);//设置填充
        //mPaint.setStrokeCap(Paint.Cap.BUTT);//设置笔帽
        mPaint.setTextSize(35 * mDensity); //以px为单位
        mPaint.setTextAlign(Paint.Align.LEFT);//默认为左对齐

        //计算各线位置
        Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();//FontMetricsInt对象
        int baseLineY = pos.y - fm.top;
        float ascent = baseLineY + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;

        //画布
        canvas.save();
        canvas.translate(0, 0); //原点
        canvas.drawText(text, pos.x, baseLineY, mPaint);

        /**
         * 辅助点（圆）
         */
        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽
        canvas.drawCircle(pos.x, pos.y, 8, mPaint);

        /**
         * 四格线
         */
        //画笔
        mPaint.reset();
        mPaint.setStrokeWidth(5);//设置线宽

        //画基线
        mPaint.setColor(Color.RED);//设置颜色
        canvas.drawLine(pos.x, baseLineY, mWidth, baseLineY, mPaint);

        //画top
        mPaint.setColor(Color.BLUE);//设置颜色
        canvas.drawLine(pos.x, top, mWidth, top, mPaint);

        //画ascent
        mPaint.setColor(0xff0c76c0);//设置颜色
        canvas.drawLine(pos.x, ascent, mWidth, ascent, mPaint);

        //画descent
        mPaint.setColor(0xfffd8403);//设置颜色
        canvas.drawLine(pos.x, descent, mWidth, descent, mPaint);

        //画bottom
        mPaint.setColor(0xff007f57);//设置颜色
        canvas.drawLine(pos.x, bottom, mWidth, bottom, mPaint);

        canvas.restore();
    }

    /**
     * 基本图形
     *
     * @param canvas
     */
    private void drawBaseShape(Canvas canvas)
    {
        drawPoint(canvas); //1/6格子
        drawLine(canvas); //2/6格子
        drawCircle(canvas); //3/6格子
        drawEllipse(canvas); //4/6格子
        drawRectangle(canvas); //5/6格子
        drawRectangle2(canvas); //6/6格子

        drawArc(canvas); //1/6格子
        drawArc2(canvas); //2/6格子
    }
    /**
     * 点（1/6格子）
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas)
    {
        //1/18
        Point pos = new Point(mWidth / 18 * 1 + mWidth / 18 * 1/2, mHeight / 18 * 1);

        //画笔
        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStrokeWidth(5 * mDensity);//设置线宽，如果不设置线宽，无法绘制点
        mPaint.setStrokeCap(Paint.Cap.BUTT);//设置笔帽

        //画布
        canvas.save();
        canvas.translate(0, 0); //原点
        canvas.drawPoint(pos.x, pos.y, mPaint);
        canvas.restore();
    }

    /**
     * 线段（2/6格子）
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas)
    {
        //画笔
        mPaint.reset();
        mPaint.setColor(Color.GRAY);//设置颜色
        mPaint.setStrokeWidth(10);//设置线宽

        /**
         * 线段1
         */
        int padding = (int)(5*mDensity);
        Point pos = new Point(mWidth / 18 * 3 + padding, mHeight / 18 * 1);
        Point pos1 = new Point(mWidth / 18 * 6 - padding, mHeight / 18 * 1);

        //画布
        canvas.save();
        canvas.translate(0, 0);
        canvas.drawLine(pos.x, pos.y, pos1.x, pos1.y, mPaint);//直线

        /**
         * 线段2
         */
        mPaint.setColor(Color.RED);//设置颜色
        Point pos11 = new Point(mWidth / 18 * 3 + padding, mHeight / 18 * 2);
        Point pos12 = new Point(mWidth / 18 * 6 - padding, mHeight / 18 * 2);
        Point pos13 = new Point(mWidth / 18 * 4 + mWidth / 18 * 1/2, mHeight / 18 * 1 + padding);
        Point pos14 = new Point(mWidth / 18 * 4 + mWidth / 18 * 1/2, mHeight / 18 * 3 - padding);

        float[] pts = {pos11.x, pos11.y, pos12.x , pos12.y, pos13.x , pos13.y, pos14.x , pos14.y}; //两两连成一条直线
        canvas.drawLines(pts, mPaint);//折线
        canvas.restore();
    }

    /**
     * 圆（3/6格子）
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas)
    {
        /**
         * 圆1
         */
        Point pos = new Point(mWidth / 18 * 7 + mWidth / 18 * 1/2, mHeight / 18 * 1);

        //画笔
        mPaint.reset();
        mPaint.setColor(Color.GRAY);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽

        //画布
        canvas.save();
        canvas.drawCircle(pos.x, pos.y, 30, mPaint);

        //辅助点（圆）
        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽
        canvas.drawCircle(pos.x, pos.y, 3, mPaint);

        /**
         * 圆2
         */
        Point pos1 = new Point(mWidth / 18 * 7 + mWidth / 18 * 1/2, mHeight / 18 * 2);

        mPaint.setColor(Color.GRAY);//设置颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置填充
        canvas.drawCircle(pos1.x, pos1.y, 70, mPaint);

        //辅助点（圆）
        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽
        canvas.drawCircle(pos1.x, pos1.y, 3, mPaint);

        canvas.restore();
    }

    /**
     * 椭圆（4/6格子）
     *
     * @param canvas
     */
    private void drawEllipse(Canvas canvas)
    {
        float left,top,right,bottom;
        float padding = 5*mDensity;

        //画笔
        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置填充
        mPaint.setStrokeWidth(5);//设置宽度
        //画布
        canvas.save();
        left = mWidth / 6 * 3 + padding;
        top = 5 * mDensity;
        right = mWidth / 6 * 4 - padding;
        bottom = mHeight / 6 * 1 - padding;

        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRect(rect, mPaint);//画矩形

        mPaint.setColor(Color.GRAY);//更改画笔颜色
        canvas.drawOval(rect, mPaint);//同一个矩形画椭圆
        canvas.restore();
    }

    /**
     * 矩形（5/6格子）
     *
     * @param canvas
     */
    private void drawRectangle(Canvas canvas)
    {
        float left,top,right,bottom;
        float padding = 5*mDensity;

        //画笔
        mPaint.reset();
        mPaint.setColor(Color.GRAY);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充

        //画布
        canvas.save();
        left = mWidth / 6 * 4 + padding;
        top = 5 * mDensity;
        right = mWidth / 6 * 5 - padding;
        bottom = mHeight / 6 * 1 - padding;
        canvas.drawRect(left, top, right, bottom, mPaint);
        canvas.restore();
    }

    /**
     * 圆角矩形（6/6格子）
     *
     * @param canvas
     */
    private void drawRectangle2(Canvas canvas)
    {
        float left,top,right,bottom;
        float padding = 5*mDensity;

        //画笔
        mPaint.reset();
        mPaint.setColor(Color.GRAY);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽

        //画布
        canvas.save();
        left = mWidth / 6 * 5 + padding;
        top = 5 * mDensity;
        right = mWidth / 6 * 6 - padding;
        bottom = mHeight / 6 * 1 - padding;
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(rect, 20, 20, mPaint);
        canvas.restore();
    }

    /**
     * 弧1（1/6格子）
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas)
    {
        float left,top,right,bottom;
        float padding = 5*mDensity;

        //画笔
        mPaint.setColor(Color.GRAY);  //设置颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽

        //画布
        canvas.save();
        //弧1
        left = padding;
        top = mWidth / 6 * 1 + padding;
        right = mWidth / 6 * 1 - padding;
        bottom = mHeight / 6 * 2 - padding;
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawArc(rect, 0, 90, true, mPaint);

        canvas.restore();
    }

    /**
     * 弧2（2/6格子）
     *
     * @param canvas
     */
    private void drawArc2(Canvas canvas)
    {
        float left,top,right,bottom;
        float padding = 5*mDensity;

        //画笔
        mPaint.setColor(Color.GRAY);  //设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充，差异点！
        mPaint.setStrokeWidth(5);//设置线宽

        //画布
        canvas.save();
        left = mWidth / 6 * 1 + padding;
        top = mWidth / 6 * 1 + padding;
        right = mWidth / 6 * 2 - padding;
        bottom = mHeight / 6 * 2 - padding;
        RectF rect = new RectF(left, top, right, bottom);
        canvas.drawArc(rect, 0, 180, false, mPaint); //差异点！

        canvas.restore();
    }

}