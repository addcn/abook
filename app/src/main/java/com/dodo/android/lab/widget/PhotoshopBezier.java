package com.dodo.android.lab.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 塞尔曲线绘制Photoshop钢笔矢量图
 *
 * TODO:
 * 1、大小比例计算适应不同尺寸
 * 2、插值器，远路径运动 http://www.jianshu.com/p/c0d7ad796cee
 * 3、动画 http://gavinliu.cn/2015/03/30/Android-Animation-%E8%B4%9D%E5%A1%9E%E5%B0%94%E6%9B%B2%E7%BA%BF%E4%B9%8B%E7%BE%8E/
 *    http://blog.csdn.net/u013831257/article/details/51281136
 *    http://blog.csdn.net/eclipsexys/article/details/51992473
 *    https://stackoverflow.com/questions/44613114/points-based-curve-transformation-bezier-curve-transform-in-android
 * 4、获取贝塞尔曲线上点的坐标
 * http://www.jianshu.com/p/c0d7ad796cee
 */



public class PhotoshopBezier extends View {

    protected Context context;
    protected Paint paint;
    protected Path path;

    private PointF start, end, control1, control2;

    List<PointF> mPoints;
    PointF startPoint, controlPoint1, controlPoint2, endPoint;

    public PhotoshopBezier(Context context) {
        super(context);
        init(context, null);
    }

    public PhotoshopBezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PhotoshopBezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        // context
        this.context = context;

        path = new Path();

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control1 = new PointF(0, 0);
        control2 = new PointF(0, 0);

        mPoints = new ArrayList<>();
        mPoints.add( new PointF(266, 80) );
        mPoints.add( new PointF(573, 128) );
        mPoints.add( new PointF(535, 466) );
        mPoints.add( new PointF(174, 439) );
    }



    /**
     * Android手写优化-更为平滑的签名效果实现
     * http://www.jianshu.com/p/49e7292a2911
     *
     * 过已知三数据点求控制点坐标
     *
     * @param s1 前点，起点左边前一点坐标，注：求p0点时可以Pn点位起点
     * @param s2 起点，曲线起点坐标
     * @param s3 末点，曲线结束点坐标
     *
     * @return 控制点pA左(0)及pB右(1)点坐标
     */
    private PointF[] calculateCurveControlPoints(PointF s1, PointF s2, PointF s3) {
        float dx1 = s1.x - s2.x;
        float dy1 = s1.y - s2.y;
        float dx2 = s2.x - s3.x;
        float dy2 = s2.y - s3.y;

        float m1X = (s1.x + s2.x) / 2.0f;
        float m1Y = (s1.y + s2.y) / 2.0f;
        float m2X = (s2.x + s3.x) / 2.0f;
        float m2Y = (s2.y + s3.y) / 2.0f;

        float l1 = (float) Math.sqrt(dx1 * dx1 + dy1 * dy1);
        float l2 = (float) Math.sqrt(dx2 * dx2 + dy2 * dy2);

        float dxm = (m1X - m2X);
        float dym = (m1Y - m2Y);
        float k = l2 / (l1 + l2); // smooth_value =0.6 是一个缩放值，取值范围为［0，1］。通过调整这个值可以控制曲线的锐度。
        if (Float.isNaN(k)) k = 0.0f;
        k = 0.6f;
        float cmX = m2X + dxm * k;
        float cmY = m2Y + dym * k;

        float tx = s2.x - cmX;
        float ty = s2.y - cmY;

        PointF pA = new PointF(m1X + tx, m1Y + ty);
        PointF pB = new PointF(m2X + tx, m2Y + ty);
        return new PointF[]{pA, pB};
    }

    /**
     * 根据已知点获取第i个控制点的坐标
     * param ps	已知曲线将经过的坐标点
     * param i	第i个坐标点
     * param a,b	可以自定义的正数
     */
    /**
     * 根据多个点使用canvas贝赛尔曲线画一条平滑的曲线（javascript）
     * https://www.zheng-hang.com/?id=43
     *
     * 根据已知点获取第i个控制点的坐标
     *
     * @return
     */
    protected PointF[] getCtrlPoint(Point[] ps, int i, float a, float b){
        if( (a<0||a>1) || b<0||b>1 ){
            a = 0.25f;
            b = 0.25f;
        }
        //处理两种极端情形
        float pAx, pAy, pBx, pBy;
        if(i<1){
            pAx = ps[0].x + (ps[1].x-ps[0].x)*a;
            pAy = ps[0].y + (ps[1].y-ps[0].y)*a;
        }else{
            pAx = ps[i].x + (ps[i+1].x-ps[i-1].x)*a;
            pAy = ps[i].y + (ps[i+1].y-ps[i-1].y)*a;
        }
        if(i>ps.length-3){
            int last = ps.length-1;
            pBx = ps[last].x - (ps[last].x-ps[last-1].x)*b;
            pBy = ps[last].y - (ps[last].y-ps[last-1].y)*b;
        }else{
            pBx = ps[i+1].x - (ps[i+2].x-ps[i].x)*b;
            pBy = ps[i+1].y - (ps[i+2].y-ps[i].y)*b;
        }
        return new PointF[]{new PointF(pAx, pAy), new PointF(pBx, pBy)};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(255, 139, 197, 186);

        //drawByPhotoshoInfo(canvas);
        drawByMidTranslation(canvas);
        drawByFormulaCalcula(canvas);

    }

    public static List<Point[]> getPoints(int width, int hight) {
        List<Point[]> points = new ArrayList<>(2);
        /*Point[] pts1 = new Point[4]; // 曲线1
        Point[] pts2 = new Point[4]; // 曲线2
        pts1[0] = new Point(0, hight / 6);
        pts1[1] = new Point((int) (width * 0.9), hight / 5);
        pts1[2] = new Point(width / 2, (int) (hight * (5.0 / 6)));
        pts1[3] = new Point(0, (int) (hight * 0.75));

        pts2[0] = new Point(width / 3, 0);
        pts2[1] = new Point(width / 2, hight / 3);
        pts2[2] = new Point(width, (int) (hight * 0.4));
        pts2[3] = new Point(width, 0);*/

        Point[] pts = new Point[4]; // 曲线0
        pts[0] = new Point(266, 80);
        pts[1] = new Point(573, 128);
        pts[2] = new Point(535, 466);
        pts[3] = new Point(174, 439);
        points.add(pts);

        //points.add(pts1);
        //points.add(pts2);
        return points;
    }
    /**
     *
     * /////////// 公式计算法 绘制 ///////////
     *
     * 自定义 View：用贝塞尔曲线绘制酷炫轮廓背景
     * http://www.jianshu.com/p/1fe0f8f0cdfa
     *
     * 想求两个点之间的控制点，你需要知道这两个点的前后点的坐标，也就是说至少需要4个点的坐标才能求两个点之间曲线的控制点。
     * https://github.com/OCNYang/ContourView/issues/2
     *
     * 根据多个点使用canvas贝赛尔曲线画一条平滑的曲线(javascript)
     * https://www.zheng-hang.com/?id=43
     *

     * @param canvas
     */
    private List<Point[]> mPointsList;
    private float mSmoothness = 0.25F;
    protected void drawByFormulaCalcula(Canvas canvas) {

        mPointsList = getPoints(canvas.getWidth(), canvas.getHeight());


        // 绘制贝塞尔曲线
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        int flag = 0;
        drawcontour:
        for (Point[] pts : mPointsList) {
            ++flag;
            int length = pts.length;
            if (length < 4) {
                continue drawcontour;
            }
            Path path = new Path();
            int x_min = 0, y_min = 0, x_max = 0, y_max = 0;
            for (int i = 0; i < length; i++) {
                Point p_i, p_1i, p_i1, p_i2;
                float ai_x, ai_y, bi_x, bi_y;

                p_i = pts[i];
                if (i == 0) {
                    path.moveTo(pts[0].x, pts[0].y);
                    x_max = x_min = pts[0].x;
                    y_min = y_max = pts[0].y;
                    p_1i = pts[length - 1];
                    p_i1 = pts[i + 1];
                    p_i2 = pts[i + 2];
                } else if (i == length - 1) {
                    p_1i = pts[i - 1];
                    p_i1 = pts[0];
                    p_i2 = pts[1];
                } else if (i == length - 2) {
                    p_1i = pts[i - 1];
                    p_i1 = pts[i + 1];
                    p_i2 = pts[0];
                } else {
                    p_1i = pts[i - 1];
                    p_i1 = pts[i + 1];
                    p_i2 = pts[i + 2];
                }

                if (p_1i == null || p_i == null || p_i1 == null || p_i2 == null) {
                    continue drawcontour;
                }

                ai_x = p_i.x + (p_i1.x - p_1i.x) * mSmoothness;
                ai_y = p_i.y + (p_i1.y - p_1i.y) * mSmoothness;

                bi_x = p_i1.x - (p_i2.x - p_i.x) * mSmoothness;
                bi_y = p_i1.y - (p_i2.y - p_i.y) * mSmoothness;

                path.cubicTo(ai_x, ai_y, bi_x, bi_y, p_i1.x, p_i1.y);

                if (pts[i].x < x_min) {
                    x_min = pts[i].x;
                }
                if (pts[i].x > x_max) {
                    x_max = pts[i].x;
                }
                if (pts[i].y < y_min) {
                    y_min = pts[i].y;
                }
                if (pts[i].y > y_max) {
                    y_max = pts[i].y;
                }
            }
            canvas.drawPath(path, paint);
        }
        flag = 0;
    }
    /**
     * /////////// 平移计算法 绘制 ///////////
     *
     * 使用贝塞尔曲线绘制多点连接曲线
     * http://www.jianshu.com/p/55099e3a2899
     *
     * Android手写优化-更为平滑的签名效果实现
     * http://www.jianshu.com/p/49e7292a2911
     *
     * 概述
     * 要想得到上图的效果，需要二阶贝塞尔和三阶贝塞尔配合。具体表现为，第一段和最后一段曲线为二阶贝塞尔，中间N段都为三阶贝塞尔曲线。
     * 思路
     * 先根据相邻点（P1，P2, P3）计算出相邻点的中点(P4， P5)，
     * 然后再计算相邻中点的中点(P6)。
     * 然后将（P4，P6, P5）组成的线段平移到经过P2的直线（P8，P2，P7）上。
     * 接着根据（P4，P6，P5，P2）的坐标计算出(P7，P8)的坐标。
     * 最后根据P7，P8等控制点画出三阶贝塞尔曲线
     * http://blog.csdn.net/qq_17250009/article/details/51027183
     */

    protected void drawByMidTranslation(Canvas canvas) {
        // P2-->P3
        PointF[] tmp = calculateCurveControlPoints(mPoints.get(0), mPoints.get(1), mPoints.get(2)); //求 P2 数据点对应的控制点--->右
        PointF c10 = tmp[0]; // 左
        PointF c11 = tmp[1]; // 右
        tmp = calculateCurveControlPoints(mPoints.get(1), mPoints.get(2), mPoints.get(3)); //求 P3 数据点对应的控制点--->左
        PointF c20 = tmp[0]; // 左
        PointF c21 = tmp[1]; // 右

        startPoint = mPoints.get(1);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(2);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);

        canvas.drawPoint(startPoint.x, startPoint.y, paint);
        canvas.drawPoint(controlPoint1.x, controlPoint1.y, paint);
        canvas.drawPoint(controlPoint2.x, controlPoint2.y, paint);
        canvas.drawPoint(endPoint.x, endPoint.y, paint);

        canvas.drawPoint(c10.x, c10.y, paint);
        canvas.drawPoint(c21.x, c21.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(c10.x, c10.y, c11.x, c11.y, paint);
        canvas.drawLine(c20.x, c20.y,c21.x, c21.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);


        // P3-->P4
        tmp = calculateCurveControlPoints(mPoints.get(1), mPoints.get(2), mPoints.get(3));//求 P3 数据点对应的控制点--->右
        c10 = tmp[0]; // 左
        c11 = tmp[1]; // 右
        tmp = calculateCurveControlPoints(mPoints.get(2), mPoints.get(3), mPoints.get(0));//求 P4 数据点对应的控制点--->左
        c20 = tmp[0]; // 左
        c21 = tmp[1]; // 右

        startPoint = mPoints.get(2);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(3);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);

        canvas.drawPoint(startPoint.x, startPoint.y, paint);
        canvas.drawPoint(controlPoint1.x, controlPoint1.y, paint);
        canvas.drawPoint(controlPoint2.x, controlPoint2.y, paint);
        canvas.drawPoint(endPoint.x, endPoint.y, paint);

        canvas.drawPoint(c10.x, c10.y, paint);
        canvas.drawPoint(c21.x, c21.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(c10.x, c10.y, c11.x, c11.y, paint);
        canvas.drawLine(c20.x, c20.y,c21.x, c21.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);

        // P4-->P1
        tmp = calculateCurveControlPoints(mPoints.get(2), mPoints.get(3), mPoints.get(0));//求 P4 数据点对应的控制点--->右
        c10 = tmp[0]; // 左
        c11 = tmp[1]; // 右
        tmp = calculateCurveControlPoints(mPoints.get(3), mPoints.get(0), mPoints.get(1));//求 P1 数据点对应的控制点--->左
        c20 = tmp[0]; // 左
        c21 = tmp[1]; // 右

        startPoint = mPoints.get(3);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(0);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);

        canvas.drawPoint(startPoint.x, startPoint.y, paint);
        canvas.drawPoint(controlPoint1.x, controlPoint1.y, paint);
        canvas.drawPoint(controlPoint2.x, controlPoint2.y, paint);
        canvas.drawPoint(endPoint.x, endPoint.y, paint);

        canvas.drawPoint(c10.x, c10.y, paint);
        canvas.drawPoint(c21.x, c21.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(c10.x, c10.y, c11.x, c11.y, paint);
        canvas.drawLine(c20.x, c20.y,c21.x, c21.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);

        // P1-->P2
        tmp = calculateCurveControlPoints(mPoints.get(3), mPoints.get(0), mPoints.get(1));//求 P1 数据点对应的控制点--->右
        c10 = tmp[0]; // 左
        c11 = tmp[1]; // 右
        tmp = calculateCurveControlPoints(mPoints.get(0), mPoints.get(1), mPoints.get(2));//求 P2 数据点对应的控制点--->左
        c20 = tmp[0]; // 左
        c21 = tmp[1]; // 右

        startPoint = mPoints.get(0);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(1);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);

        canvas.drawPoint(startPoint.x, startPoint.y, paint);
        canvas.drawPoint(controlPoint1.x, controlPoint1.y, paint);
        canvas.drawPoint(controlPoint2.x, controlPoint2.y, paint);
        canvas.drawPoint(endPoint.x, endPoint.y, paint);

        canvas.drawPoint(c10.x, c10.y, paint);
        canvas.drawPoint(c21.x, c21.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(c10.x, c10.y, c11.x, c11.y, paint);
        canvas.drawLine(c20.x, c20.y,c21.x, c21.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);
    }


    /**
     * /////////// Photoshop信息坐标法 绘制 ///////////
     *
     * 设计师教你怎么用最偷懒的方式画贝塞尔曲线 读图模式
     * http://www.guokr.com/post/695800/
     *
     * https://stackoverflow.com/questions/44613114/points-based-curve-transformation-bezier-curve-transform-in-android
     *
     *
     * @param canvas
     */
    protected void drawByPhotoshoInfo(Canvas canvas) {
        ///*

        // P1-->P2
        // 初始化
        start.x = 266;
        start.y = 80;
        end.x = 573;
        end.y = 128;
        control1.x = 359;
        control1.y = 15;
        control2.x = 503;
        control2.y = 31;

        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        //paint.setStyle(Paint.Style.FILL); //填充内部
        paint.setStrokeWidth(20);

        canvas.drawPoint(start.x, start.y, paint);
        canvas.drawPoint(end.x, end.y, paint);
        canvas.drawPoint(control1.x, control1.y, paint);
        canvas.drawPoint(control2.x, control2.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, paint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);

        path.moveTo(start.x, start.y);
        path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y);

        canvas.drawPath(path, paint);


        // P2-->P3
        // 初始化
        start.x = 573;
        start.y = 128;
        end.x = 535;
        end.y = 466;
        control1.x = 644;
        control1.y = 244;
        control2.x = 607;
        control2.y = 421;

        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, paint);
        canvas.drawPoint(end.x, end.y, paint);
        canvas.drawPoint(control1.x, control1.y, paint);
        canvas.drawPoint(control2.x, control2.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, paint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);

        path.moveTo(start.x, start.y);
        path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y);

        canvas.drawPath(path, paint);

        // P3-->P4
        // 初始化
        start.x = 535;
        start.y = 466;
        end.x = 147;
        end.y = 439;
        control1.x = 463;
        control1.y = 508;
        control2.x = 312;
        control2.y = 517;

        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, paint);
        canvas.drawPoint(end.x, end.y, paint);
        canvas.drawPoint(control1.x, control1.y, paint);
        canvas.drawPoint(control2.x, control2.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, paint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);

        path.moveTo(start.x, start.y);
        path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y);

        canvas.drawPath(path, paint);

        // P4-->P1
        // 初始化
        start.x = 147;
        start.y = 439;
        end.x = 266;
        end.y = 80;
        control1.x = 34;
        control1.y = 356;
        control2.x = 12;
        control2.y = 285;

        // 绘制数据点和控制点
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, paint);
        canvas.drawPoint(end.x, end.y, paint);
        canvas.drawPoint(control1.x, control1.y, paint);
        canvas.drawPoint(control2.x, control2.y, paint);

        // 绘制辅助线
        paint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control1.x, control1.y, paint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.RED);
        paint.setStrokeWidth(15);

        path.moveTo(start.x, start.y);
        path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y);

        canvas.drawPath(path, paint);

        //*/
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/*
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int w = 761, h = 544;
        //view支持wrap_content
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(w, h);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(w, h);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(w, h);
        } else {
            super.onMeasure(w, h);
        }*/

    }
}
