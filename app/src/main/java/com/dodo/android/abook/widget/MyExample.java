package com.dodo.android.abook.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.dodo.android.abook.R;

import java.util.ArrayList;
import java.util.List;

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
        drawTextOnPath(canvas);
        drawBaseShape(canvas);
        drawBitmap(canvas);
        drawPath(canvas);
        drawBezier(canvas);
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
     * 文字
     *
     * @param canvas
     */
    private void drawTextOnPath(Canvas canvas)
    {
        String text = "风萧萧兮易水寒，壮士一去兮不复返";

        //画笔
        mPaint.reset();
        mPaint.setColor(Color.RED);  //设置颜色
        mPaint.setStrokeWidth(5);//设置宽度
        mPaint.setAntiAlias(true); //抗锯齿功能，如果使用，会使绘图速度变慢
        mPaint.setStyle(Paint.Style.STROKE);//设置填充

        //画布
        canvas.save();
        canvas.translate(0, 0); //原点

        //先创建两个相同的圆形路径，并先画出两个路径原图
        Path circlePath = new Path();
        Point pos = new Point(mWidth / 6 * 2, mHeight / 6 * 5 - mHeight / 6 * 1/2);
        circlePath.addCircle(pos.x, pos.y, 120, Path.Direction.CCW);//逆向绘制
        canvas.drawPath(circlePath, mPaint);//绘制出路径原形

        Path circlePath2 = new Path();
        Point pos2 = new Point(mWidth / 6 * 4, mHeight / 6 * 5 - mHeight / 6 * 1/2);
        circlePath2.addCircle(pos2.x, pos2.y, 120, Path.Direction.CCW);
        canvas.drawPath(circlePath2, mPaint);//绘制出路径原形

        //辅助点（圆）
        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽
        canvas.drawCircle(pos2.x, pos2.y, 3, mPaint);

        //画笔
        mPaint.reset();
        mPaint.setColor(Color.BLACK);//设置颜色
        mPaint.setTextSize(45);//设置大小
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        //hoffset、voffset参数值全部设为0，看原始状态是怎样的
        canvas.drawTextOnPath(text, circlePath, 0, 0, mPaint);
        //第二个路径，改变hoffset、voffset参数值
        canvas.drawTextOnPath(text, circlePath2, 80, 30, mPaint);

        //辅助点（圆）
        mPaint.reset();
        mPaint.setColor(Color.RED);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//设置填充
        mPaint.setStrokeWidth(5);//设置线宽
        canvas.drawCircle(pos.x, pos.y, 3, mPaint);

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
     * 弧（1/6格子）
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

    /**
     * 绘制图
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas)
    {
        float left,top,right,bottom;
        int padding = (int)(5*mDensity);

        Point pos = new Point(mWidth / 6 * 2 + padding, mHeight / 6 * 1 + padding);

        //bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, pos.x, pos.y, mPaint);//完全绘制Bitmap

        //绘制Bitmap的一部分，并对其拉伸
        //srcRect定义了要绘制Bitmap的哪一部分
        Rect srcRect = new Rect();
        srcRect.left = 0;
        srcRect.right = bitmap.getWidth();
        srcRect.top = 0;
        srcRect.bottom = (int) (0.66 * bitmap.getHeight());

        float radio = (float) (srcRect.bottom - srcRect.top) / bitmap.getWidth();

        //dstRecF定义了要将绘制的Bitmap拉伸到哪里
        left = mWidth / 6 * 3 + padding;
        top = mHeight / 6 * 1 + padding;
        right = mWidth / 6 * 4 - padding;
        float dstHeight = (right - left)*radio - padding;
        bottom = top + dstHeight;
        RectF dstRecF = new RectF(left, top, right, bottom);
        canvas.drawBitmap(bitmap, srcRect, dstRecF, mPaint);

        bitmap = null;
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 绘制路径
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas)
    {
        float left,top,right,bottom;
        int padding = (int)(5*mDensity);

        //画笔
        mPaint.reset();
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStrokeWidth(4);//设置线宽
        mPaint.setStyle(Paint.Style.STROKE);//设置填充

        //向Path中加入Arc
        left = mWidth / 6 * 4 + padding;
        top = mHeight / 6 * 1 + padding;
        right = mWidth / 6 * 5;
        bottom = mHeight / 6 * 2 - padding;
        RectF arcRecF = new RectF(left, top, right, bottom);
        mPath.addArc(arcRecF, 0, 135);
        //向Path中加入Oval
        left = mWidth / 6 * 5;
        top = mHeight / 6 * 1 + padding;
        right = mWidth / 6 * 6 - padding;
        bottom = mHeight / 6 * 2 - padding;
        RectF ovalRecF = new RectF(left, top, right, bottom);
        mPath.addOval(ovalRecF, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 绘制贝塞尔曲线
     *
     * @param canvas
     */
    private void drawBezier(Canvas canvas)
    {
        float left,top,right,bottom;
        int padding = (int)(5*mDensity);
        Point pos = new Point(padding, mHeight / 6 * 2 + padding);

        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStrokeWidth(4);//设置线宽


        /*-----------------使用lineTo、arcTo、quadTo、cubicTo画线--------------*/
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(pos.x, pos.y);
        Path path = new Path();
        //用pointList记录不同的path的各处的连接点
        List<Point> pointList = new ArrayList<Point>();
        //1. 第一部分，绘制线段
        path.moveTo(0, 0);
        path.lineTo(pos.x / 2, 0);//绘制线段
        pointList.add(new Point(0, 0));
        pointList.add(new Point(pos.x / 2, 0));

        //2. 第二部分，绘制椭圆右上角的四分之一的弧线
        RectF arcRecF1 = new RectF(0, 0, pos.x, pos.y);
        path.arcTo(arcRecF1, 270, 90);//绘制圆弧
        pointList.add(new Point(pos.x, pos.y / 2));

        //3. 第三部分，绘制椭圆左下角的四分之一的弧线
        //注意，我们此处调用了path的moveTo方法，将画笔的移动到我们下一处要绘制arc的起点上
        path.moveTo(pos.x * 1.5f, pos.y);
        RectF arcRecF2 = new RectF(pos.x, 0, pos.x * 2, pos.y);
        path.arcTo(arcRecF2, 90, 90);//绘制圆弧
        pointList.add(new Point((int) (pos.x * 1.5), pos.y));

        //4. 第四部分，绘制二阶贝塞尔曲线
        //二阶贝塞尔曲线的起点就是当前画笔的位置，然后需要添加一个控制点，以及一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path.moveTo(pos.x * 1.5f, pos.y);
        //绘制二阶贝塞尔曲线
        path.quadTo(pos.x * 2, 0, pos.x * 2.5f, pos.y / 2);
        pointList.add(new Point((int) (pos.x * 2.5), pos.y / 2));

        //5. 第五部分，绘制三阶贝塞尔曲线，三阶贝塞尔曲线的起点也是当前画笔的位置
        //其需要两个控制点，即比二阶贝赛尔曲线多一个控制点，最后也需要一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path.moveTo(pos.x * 2.5f, pos.y / 2);
        //绘制三阶贝塞尔曲线
        path.cubicTo(pos.x * 3, 0, pos.x * 3.5f, 0, pos.x * 4, pos.y);
        pointList.add(new Point(pos.x * 4, pos.y));

        //Path准备就绪后，真正将Path绘制到Canvas上
        canvas.drawPath(path, mPaint);

        //最后绘制Path的连接点，方便我们大家对比观察
        mPaint.setStrokeWidth(10);//将点的strokeWidth要设置的比画path时要大
        mPaint.setStrokeCap(Paint.Cap.ROUND);//将点设置为圆点状
        mPaint.setColor(0xff0000ff);//设置圆点为蓝色
        for (Point p : pointList) {
            //遍历pointList，绘制连接点
            canvas.drawPoint(p.x, p.y, mPaint);
        }
    }
}