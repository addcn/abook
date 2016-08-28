package com.dodo.android.abook.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dodo on 2016/8/25.
 */
public class MyView extends View {

    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private float density = getResources().getDisplayMetrics().density;


    private Bitmap bitmap;

    //New一个View的时候调用
    public MyView(Context context) {
        super(context);
    }

    //layout文件中使用的时候会调用
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
       /* mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色

        //drawARGB(canvas); //画布
        //drawXYAxis(canvas); //坐标系
        //drawPoint(canvas); //点
        //drawLine(canvas); //线
        //drawRect(canvas); //矩形
        //(canvas);//圆
        //drawOval(canvas);//椭圆
        //drawArc(canvas);//弧
        //drawPath(canvas);//路径

        drawText(canvas); //文字

        //drawBitmap(canvas); //图
        //drawXYAxis(canvas); //坐标系
    }

    /**
     * 绘制画布
     * http://blog.csdn.net/iispring/article/details/49770651
     *
     * @param canvas
     */
    private void drawARGB(Canvas canvas) {
        canvas.drawARGB(255, 139, 197, 186);
    }

    /**
     * 当前坐标绘坐标系
     *
     * @param canvas
     */
    private void drawXYAxis(Canvas canvas) {
        // 配置参数
        int LEN_X = 500; //x轴长度
        int LEN_Y = 800; //y轴长度
        int LINE_WIDTH = 5; //线宽
        int LINE_COLOR = Color.GRAY; //线颜色
        int Arrow_Width = 15; //坐标箭头张度
        int Arrow_Length = 30; //坐标箭头长度

        // Paint
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(LINE_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(LINE_WIDTH);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        // Canvas
        int mWidth = canvas.getWidth();
        int mHeight = canvas.getHeight();
        //LEN_X = mWidth; //x轴长度
        //LEN_Y = mHeight; //y轴长度
        //canvas.translate(0,0);
        canvas.translate(mWidth / 2, mHeight / 2); // 坐标系原点移动到画布正中心

        // Point
        Point XStart = new Point(+LEN_X, 0); //x轴始点
        Point XEnd = new Point(-LEN_X, 0); //x轴末点
        Point XArrow1 = new Point(LEN_X - Arrow_Length, -Arrow_Width);//x轴箭头上点
        Point XArrow2 = new Point(LEN_X - Arrow_Length, +Arrow_Width);//x轴箭头下点

        Point YStart = new Point(0, +LEN_Y);//y轴始点
        Point YEnd = new Point(0, -LEN_Y);//y轴末点
        Point YArrow1 = new Point(-Arrow_Width, LEN_Y - Arrow_Length);//y轴箭头左点
        Point YArrow2 = new Point(+Arrow_Width, LEN_Y - Arrow_Length);//y轴箭头右点

        // Draw
        canvas.save();
        canvas.drawLine(XStart.x, XStart.y, XEnd.x, XEnd.y, mPaint); //X轴线
        canvas.drawLine(XStart.x, XStart.y, XArrow1.x, XArrow1.y, mPaint); //X上箭头
        canvas.drawLine(XStart.x, XStart.y, XArrow2.x, XArrow2.y, mPaint);//X下箭头

        canvas.drawLine(YStart.x, YStart.y, YEnd.x, YEnd.y, mPaint);//Y轴线
        canvas.drawLine(YStart.x, YStart.y, YArrow1.x, YArrow1.y, mPaint);//Y左箭头
        canvas.drawLine(YStart.x, YStart.y, YArrow2.x, YArrow2.y, mPaint);//Y右箭头
        canvas.restore();
    }


    /**
     * 绘制坐标系
     *
     * @param canvas
     */
    private void drawAxis(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6 * density);

        //用绿色画x轴，用蓝色画y轴

        //第一次绘制坐标轴
        mPaint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, mPaint);//绘制x轴
        mPaint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, mPaint);//绘制y轴

        //对坐标系平移后，第二次绘制坐标轴
        canvas.translate(canvasWidth / 4, canvasWidth / 4);//把坐标系向右下角平移
        mPaint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, mPaint);//绘制x轴
        mPaint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, mPaint);//绘制y轴

        //再次平移坐标系并在此基础上旋转坐标系，第三次绘制坐标轴
        canvas.translate(canvasWidth / 4, canvasWidth / 4);//在上次平移的基础上再把坐标系向右下角平移
        canvas.rotate(30);//基于当前绘图坐标系的原点旋转坐标系
        mPaint.setColor(0xff00ff00);//绿色
        canvas.drawLine(0, 0, canvasWidth, 0, mPaint);//绘制x轴
        mPaint.setColor(0xff0000ff);//蓝色
        canvas.drawLine(0, 0, 0, canvasHeight, mPaint);//绘制y轴
    }

    /**
     * 绘制点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int x = canvasWidth / 2;
        int deltaY = canvasHeight / 3;
        int y = deltaY / 2;
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStrokeWidth(50 * density);//设置线宽，如果不设置线宽，无法绘制点

        //绘制Cap为BUTT的点
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawPoint(x, y, mPaint);

        //绘制Cap为ROUND的点
        canvas.translate(0, deltaY);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(x, y, mPaint);

        //绘制Cap为SQUARE的点
        canvas.translate(0, deltaY);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(x, y, mPaint);
    }

    /**
     * 绘制线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        int canvasWidth = canvas.getWidth();
        int halfWidth = canvasWidth / 2;
        int deltaY = canvas.getHeight() / 5;
        int halfDeltaY = deltaY / 2;
        float[] pts = {
                50, 0, halfWidth, halfDeltaY,
                halfWidth, halfDeltaY, canvasWidth - 50, 0
        };

        //绘制一条线段
        canvas.drawLine(5, 0, canvasWidth - 50, deltaY / 2, mPaint);

        //绘制折线
        canvas.save();
        canvas.translate(0, deltaY);
        canvas.drawLines(pts, mPaint);
        canvas.restore();

        //设置线宽
        mPaint.setStrokeWidth(10 * density);

        //输出默认Cap
        Paint.Cap defaultCap = mPaint.getStrokeCap();
        Log.i("DemoLog", "默认Cap:" + defaultCap);

        //用BUTT作为Cap
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.save();
        canvas.translate(0, deltaY * 2);
        canvas.drawLine(50, 0, halfWidth, 0, mPaint);
        canvas.restore();

        //用ROUND作为Cap
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.save();
        canvas.translate(0, deltaY * 2 + 20 * density);
        canvas.drawLine(50, 0, halfWidth, 0, mPaint);
        canvas.restore();

        //用SQUARE作为Cap
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.save();
        canvas.translate(0, deltaY * 2 + 40 * density);
        canvas.drawLine(50, 0, halfWidth, 0, mPaint);
        canvas.restore();

        //恢复为默认的Cap
        mPaint.setStrokeCap(defaultCap);
    }

    /**
     * 绘制矩形
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        //默认画笔的填充色是黑色
        int left1 = 10;
        int top1 = 10;
        int right1 = canvasWidth / 3;
        int bottom1 = canvasHeight / 3;
        canvas.drawRect(left1, top1, right1, bottom1, mPaint);

        //修改画笔颜色
        mPaint.setColor(0xff8bc5ba);//A:ff,R:8b,G:c5,B:ba
        int left2 = canvasWidth / 3 * 2;
        int top2 = 10;
        int right2 = canvasWidth - 10;
        int bottom2 = canvasHeight / 3;
        canvas.drawRect(left2, top2, right2, bottom2, mPaint);
    }

    /**
     * 绘制圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//默认绘图为填充模式

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int halfCanvasWidth = canvasWidth / 2;
        int count = 3;
        int D = canvasHeight / (count + 1);
        int R = D / 2;

        //绘制圆
        canvas.translate(0, D / (count + 1));
        canvas.drawCircle(halfCanvasWidth, R, R, mPaint);

        //通过绘制两个圆形成圆环
        //1. 首先绘制大圆
        canvas.translate(0, D + D / (count + 1));
        canvas.drawCircle(halfCanvasWidth, R, R, mPaint);
        //2. 然后绘制小圆，让小圆覆盖大圆，形成圆环效果
        int r = (int) (R * 0.75);
        mPaint.setColor(0xffffffff);//将画笔设置为白色，画小圆
        canvas.drawCircle(halfCanvasWidth, R, r, mPaint);

        //通过线条绘图模式绘制圆环
        canvas.translate(0, D + D / (count + 1));
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStyle(Paint.Style.STROKE);//绘图为线条模式
        float strokeWidth = (float) (R * 0.25);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(halfCanvasWidth, R, R, mPaint);
    }

    /**
     * 绘制椭圆
     *
     * @param canvas
     */
    private void drawOval(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        float quarter = canvasHeight / 4;
        float left = 10 * density;
        float top = 0;
        float right = canvasWidth - left;
        float bottom = quarter;
        RectF rectF = new RectF(left, top, right, bottom);

        //绘制椭圆形轮廓线
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为画线条模式
        mPaint.setStrokeWidth(2 * density);//设置线宽
        mPaint.setColor(0xff8bc5ba);//设置线条颜色
        canvas.translate(0, quarter / 4);
        canvas.drawOval(rectF, mPaint);

        //绘制椭圆形填充面
        mPaint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.translate(0, (quarter + quarter / 4));
        canvas.drawOval(rectF, mPaint);

        //画两个椭圆，形成轮廓线和填充色不同的效果
        canvas.translate(0, (quarter + quarter / 4));
        //1. 首先绘制填充色
        mPaint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.drawOval(rectF, mPaint);//绘制椭圆形的填充效果
        //2. 将线条颜色设置为蓝色，绘制轮廓线
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        mPaint.setColor(0xff0000ff);//设置填充色为蓝色
        canvas.drawOval(rectF, mPaint);//设置椭圆的轮廓线
    }

    /**
     * 绘制弧
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int count = 5;
        float ovalHeight = canvasHeight / (count + 1);
        float left = 10 * density;
        float top = 0;
        float right = canvasWidth - left;
        float bottom = ovalHeight;
        RectF rectF = new RectF(left, top, right, bottom);

        mPaint.setStrokeWidth(2 * density);//设置线宽
        mPaint.setColor(0xff8bc5ba);//设置颜色
        mPaint.setStyle(Paint.Style.FILL);//默认设置画笔为填充模式

        //绘制用drawArc绘制完整的椭圆
        canvas.translate(0, ovalHeight / count);
        canvas.drawArc(rectF, 0, 360, true, mPaint);

        //绘制椭圆的四分之一,起点是钟表的3点位置，从3点绘制到6点的位置
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, mPaint);

        //绘制椭圆的四分之一,将useCenter设置为false
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, false, mPaint);

        //绘制椭圆的四分之一，只绘制轮廓线
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, mPaint);

        //绘制带有轮廓线的椭圆的四分之一
        //1. 先绘制椭圆的填充部分
        mPaint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        canvas.translate(0, (ovalHeight + ovalHeight / count));
        canvas.drawArc(rectF, 0, 90, true, mPaint);
        //2. 再绘制椭圆的轮廓线部分
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        mPaint.setColor(0xff0000ff);//设置轮廓线条为蓝色
        canvas.drawArc(rectF, 0, 90, true, mPaint);
    }

    /**
     * 绘制路径
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int deltaX = canvasWidth / 4;
        int deltaY = (int) (deltaX * 0.75);

        mPaint.setColor(0xff8bc5ba);//设置画笔颜色
        mPaint.setStrokeWidth(4);//设置线宽

        /*--------------------------用Path画填充面-----------------------------*/
        mPaint.setStyle(Paint.Style.FILL);//设置画笔为填充模式
        Path path = new Path();
        //向Path中加入Arc
        RectF arcRecF = new RectF(0, 0, deltaX, deltaY);
        path.addArc(arcRecF, 0, 135);
        //向Path中加入Oval
        RectF ovalRecF = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path.addOval(ovalRecF, Path.Direction.CCW);
        //向Path中添加Circle
        path.addCircle((float) (deltaX * 2.5), deltaY / 2, deltaY / 2, Path.Direction.CCW);
        //向Path中添加Rect
        RectF rectF = new RectF(deltaX * 3, 0, deltaX * 4, deltaY);
        path.addRect(rectF, Path.Direction.CCW);
        canvas.drawPath(path, mPaint);

        /*--------------------------用Path画线--------------------------------*/
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path2 = path;
        canvas.drawPath(path2, mPaint);

        /*-----------------使用lineTo、arcTo、quadTo、cubicTo画线--------------*/
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为线条模式
        canvas.translate(0, deltaY * 2);
        Path path3 = new Path();
        //用pointList记录不同的path的各处的连接点
        List<Point> pointList = new ArrayList<Point>();
        //1. 第一部分，绘制线段
        path3.moveTo(0, 0);
        path3.lineTo(deltaX / 2, 0);//绘制线段
        pointList.add(new Point(0, 0));
        pointList.add(new Point(deltaX / 2, 0));
        //2. 第二部分，绘制椭圆右上角的四分之一的弧线
        RectF arcRecF1 = new RectF(0, 0, deltaX, deltaY);
        path3.arcTo(arcRecF1, 270, 90);//绘制圆弧
        pointList.add(new Point(deltaX, deltaY / 2));
        //3. 第三部分，绘制椭圆左下角的四分之一的弧线
        //注意，我们此处调用了path的moveTo方法，将画笔的移动到我们下一处要绘制arc的起点上
        path3.moveTo(deltaX * 1.5f, deltaY);
        RectF arcRecF2 = new RectF(deltaX, 0, deltaX * 2, deltaY);
        path3.arcTo(arcRecF2, 90, 90);//绘制圆弧
        pointList.add(new Point((int) (deltaX * 1.5), deltaY));
        //4. 第四部分，绘制二阶贝塞尔曲线
        //二阶贝塞尔曲线的起点就是当前画笔的位置，然后需要添加一个控制点，以及一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 1.5f, deltaY);
        //绘制二阶贝塞尔曲线
        path3.quadTo(deltaX * 2, 0, deltaX * 2.5f, deltaY / 2);
        pointList.add(new Point((int) (deltaX * 2.5), deltaY / 2));
        //5. 第五部分，绘制三阶贝塞尔曲线，三阶贝塞尔曲线的起点也是当前画笔的位置
        //其需要两个控制点，即比二阶贝赛尔曲线多一个控制点，最后也需要一个终点
        //再次通过调用path的moveTo方法，移动画笔
        path3.moveTo(deltaX * 2.5f, deltaY / 2);
        //绘制三阶贝塞尔曲线
        path3.cubicTo(deltaX * 3, 0, deltaX * 3.5f, 0, deltaX * 4, deltaY);
        pointList.add(new Point(deltaX * 4, deltaY));

        //Path准备就绪后，真正将Path绘制到Canvas上
        canvas.drawPath(path3, mPaint);

        //最后绘制Path的连接点，方便我们大家对比观察
        mPaint.setStrokeWidth(10);//将点的strokeWidth要设置的比画path时要大
        mPaint.setStrokeCap(Paint.Cap.ROUND);//将点设置为圆点状
        mPaint.setColor(0xff0000ff);//设置圆点为蓝色
        for (Point p : pointList) {
            //遍历pointList，绘制连接点
            canvas.drawPoint(p.x, p.y, mPaint);
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);//设置为抗锯齿
        mPaint.setTextSize(50.0f);//设置字体大小

        //画点
        //Android利用canvas画各种图形(点、直线、弧、圆、椭圆、文字、矩形、多边形、曲线、圆角矩形)
        //http://blog.csdn.net/rhljiayou/article/details/7212620
        canvas.save();
        mPaint.setStrokeWidth(50);
        mPaint.setColor(0xff0000ff); //蓝色
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPoint(canvas.getWidth() / 2, (canvas.getHeight() / 3) * 2 + 100, mPaint);//画一个点
        //canvas.drawPoints(new float[]{60, 400, 65, 400, 70, 400}, mPaint);//画多个点
        canvas.restore();

        //原点画圆
        canvas.save();
        canvas.translate(0, 0);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(10, 10, 10, mPaint);//x轴正向下，y轴正向右
        canvas.restore();

        //画圆2
        canvas.save();
        canvas.drawCircle(canvas.getWidth() / 2, (canvas.getHeight() / 3) * 2, 8, mPaint);
        canvas.restore();

        //网格
        canvas.save();
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.GRAY);
        //设置画直线格式
        mPaint.setStyle(Paint.Style.FILL);
        //设置虚线效果
        PathEffect effects = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        //mPaint.setPathEffect(effects) ;
        canvas.drawLine(0, canvas.getHeight() / 3 * 1, canvas.getWidth(), canvas.getHeight() / 3 * 1, mPaint);
        canvas.drawLine(0, canvas.getHeight() / 3 * 2, canvas.getWidth(), canvas.getHeight() / 3 * 2, mPaint);
        canvas.drawLine(0, canvas.getHeight() / 3 * 3, canvas.getWidth(), canvas.getHeight() / 3 * 3, mPaint);

        canvas.drawLine(canvas.getWidth() / 3 * 1, 0, canvas.getWidth() / 3 * 1, canvas.getHeight(), mPaint);
        canvas.drawLine(canvas.getWidth() / 3 * 2, 0, canvas.getWidth() / 3 * 2, canvas.getHeight(), mPaint);
        canvas.drawLine(canvas.getWidth() / 3 * 3, 0, canvas.getWidth() / 3 * 3, canvas.getHeight(), mPaint);

        canvas.restore();


        // Canvas
        int mWidth = canvas.getWidth();
        int mHeight = canvas.getHeight();
        //canvas.translate(mWidth / 2, mHeight / 2); // 坐标系原点移动到画布正中心

        // Paint
        //android中canvas.drawText参数的介绍以及绘制一个文本居中的案例
        // http://blog.csdn.net/lovexieyuan520/article/details/43153275
       /* String testString = "测试中文：abcdefghijk:1234567890";
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.RED);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect bounds = new Rect();
        mPaint.getTextBounds(testString, 0, testString.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(testString,getMeasuredWidth() / 2 - bounds.width() / 2, baseline, mPaint);*/
        //(getMeasuredHeight()/2 + (fontMetrics.descent- fontMetrics.ascent)/2 - fontMetrics.descent，经测试这个会比你那个更精确一些


        //绘制文本
        /*canvas.save();
        canvas.translate(0, 0);
        mPaint.setStrokeWidth(10);
        mPaint.setTextSize(60.0f);
        canvas.drawText("文本绘制", 0, 0, mPaint);
        canvas.restore();*/


        //---------------------------------------------------------------------
        float textHeight = 50.0f;
        int canvasWidth = canvas.getWidth();
        int halfCanvasWidth = canvasWidth / 2;
        float translateY = 350.0f; //导航栏高度

        //绘制正常文本
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("正常绘制文本", 0, 0, mPaint);
        canvas.restore();
        translateY += textHeight * 2;


        //绘制绿色文本
        mPaint.setColor(0xff00ff00);//设置字体为绿色
        canvas.save();
        canvas.translate(0, translateY);//将画笔向下移动
        canvas.drawText("绘制绿色文本", 0, 0, mPaint);
        canvas.restore();
        mPaint.setColor(0xff000000);//重新设置为黑色
        translateY += textHeight * 2;

        //设置左对齐
        mPaint.setTextAlign(Paint.Align.LEFT);//设置左对齐
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("左对齐文本", 0, 0, mPaint);
        canvas.restore();
        translateY += textHeight * 2;

        //设置居中对齐
        mPaint.setTextAlign(Paint.Align.CENTER);//设置居中对齐
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("居中对齐文本", 0, 0, mPaint);
        canvas.restore();
        translateY += textHeight * 2;

        //设置右对齐
        mPaint.setTextAlign(Paint.Align.RIGHT);//设置右对齐
        canvas.save();
        canvas.translate(halfCanvasWidth, translateY);
        canvas.drawText("右对齐文本", 0, 0, mPaint);
        canvas.restore();
        mPaint.setTextAlign(Paint.Align.LEFT);//重新设置为左对齐
        translateY += textHeight * 2;

        //设置下划线
        mPaint.setUnderlineText(true);//设置具有下划线
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("下划线文本", 0, 0, mPaint);
        canvas.restore();
        mPaint.setUnderlineText(false);//重新设置为没有下划线
        translateY += textHeight * 2;

        //绘制加粗文字
        mPaint.setFakeBoldText(true);//将画笔设置为粗体
        canvas.save();
        canvas.translate(0, translateY);
        canvas.drawText("粗体文本", 0, 0, mPaint);
        canvas.restore();
        mPaint.setFakeBoldText(false);//重新将画笔设置为非粗体状态
        translateY += textHeight * 2;

        //文本绕绘制起点顺时针旋转
        canvas.save();
        canvas.translate(0, translateY);
        canvas.rotate(20);
        canvas.drawText("文本绕绘制起点旋转20度", 0, 0, mPaint);
        canvas.restore();
    }

    /**
     * 绘制图
     *
     * @param canvas
     */
    private void drawBitmap(Canvas canvas) {
        //如果bitmap不存在，那么就不执行下面的绘制代码
        if (bitmap == null) {
            return;
        }

        //直接完全绘制Bitmap
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

        //绘制Bitmap的一部分，并对其拉伸
        //srcRect定义了要绘制Bitmap的哪一部分
        Rect srcRect = new Rect();
        srcRect.left = 0;
        srcRect.right = bitmap.getWidth();
        srcRect.top = 0;
        srcRect.bottom = (int) (0.33 * bitmap.getHeight());
        float radio = (float) (srcRect.bottom - srcRect.top) / bitmap.getWidth();
        //dstRecF定义了要将绘制的Bitmap拉伸到哪里
        RectF dstRecF = new RectF();
        dstRecF.left = 0;
        dstRecF.right = canvas.getWidth();
        dstRecF.top = bitmap.getHeight();
        float dstHeight = (dstRecF.right - dstRecF.left) * radio;
        dstRecF.bottom = dstRecF.top + dstHeight;
        canvas.drawBitmap(bitmap, srcRect, dstRecF, mPaint);
    }

    public void setBitmap(Bitmap bm) {
        releaseBitmap();
        bitmap = bm;
    }

    private void releaseBitmap() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = null;
    }

    public void destroy() {
        releaseBitmap();
    }
}