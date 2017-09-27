package com.dodo.android.abook.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

    private PointF getNewPoint(float x, float y) {

        return new PointF(x, y);
    }

    /**
     * 过已知三数据点求控制点坐标
     *
     * @param s1 前点，起点左边前一点坐标，注：求p0点时可以Pn点位起点
     * @param s2 起点，曲线起点坐标
     * @param s3 末点，曲线结束点坐标
     *
     * @return 控制点A左(0)及B右(1)点坐标
     */
    private List<PointF> calculateCurveControlPoints(PointF s1, PointF s2, PointF s3) {
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

        List<PointF> cPoints = new ArrayList<>();
        cPoints.add(getNewPoint(m1X + tx, m1Y + ty));
        cPoints.add(getNewPoint(m2X + tx, m2Y + ty));

        return cPoints;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(255, 139, 197, 186);
        
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



        /////////////////
        /**
         * 想求两个点之间的控制点，你需要知道这两个点的前后点的坐标，也就是说至少需要4个点的坐标才能求两个点之间曲线的控制点。
         * https://github.com/OCNYang/ContourView/issues/2
         *
         *
         * 使用贝塞尔曲线绘制多点连接曲线
         * http://www.jianshu.com/p/55099e3a2899
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

        // P2-->P3
        List<PointF> tmp = calculateCurveControlPoints(mPoints.get(0), mPoints.get(1), mPoints.get(2)); //求 P2 数据点对应的控制点--->右
        PointF c10 = tmp.get(0); // 左
        PointF c11 = tmp.get(1); // 右
        tmp = calculateCurveControlPoints(mPoints.get(1), mPoints.get(2), mPoints.get(3)); //求 P3 数据点对应的控制点--->左
        PointF c20 = tmp.get(0); // 左
        PointF c21 = tmp.get(1); // 右

        startPoint = mPoints.get(1);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(2);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);
        canvas.drawPoint(c11.x, c11.y, paint);
        canvas.drawPoint(c20.x, c20.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);


        // P3-->P4
        tmp = calculateCurveControlPoints(mPoints.get(1), mPoints.get(2), mPoints.get(3));//求 P3 数据点对应的控制点--->右
        c10 = tmp.get(0); // 左
        c11 = tmp.get(1); // 右
        tmp = calculateCurveControlPoints(mPoints.get(2), mPoints.get(3), mPoints.get(0));//求 P4 数据点对应的控制点--->左
        c20 = tmp.get(0); // 左
        c21 = tmp.get(1); // 右

        startPoint = mPoints.get(2);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(3);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);
        canvas.drawPoint(c11.x, c11.y, paint);
        canvas.drawPoint(c20.x, c20.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);

        // P4-->P1
        tmp = calculateCurveControlPoints(mPoints.get(2), mPoints.get(3), mPoints.get(0));//求 P4 数据点对应的控制点--->右
        c10 = tmp.get(0); // 左
        c11 = tmp.get(1); // 右
        tmp = calculateCurveControlPoints(mPoints.get(3), mPoints.get(0), mPoints.get(1));//求 P1 数据点对应的控制点--->左
        c20 = tmp.get(0); // 左
        c21 = tmp.get(1); // 右

        startPoint = mPoints.get(2);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(3);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);
        canvas.drawPoint(c11.x, c11.y, paint);
        canvas.drawPoint(c20.x, c20.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);

        // P1-->P2
        tmp = calculateCurveControlPoints(mPoints.get(3), mPoints.get(0), mPoints.get(1));//求 P1 数据点对应的控制点--->右
        c10 = tmp.get(0); // 左
        c11 = tmp.get(1); // 右
        tmp = calculateCurveControlPoints(mPoints.get(0), mPoints.get(1), mPoints.get(2));//求 P2 数据点对应的控制点--->左
        c20 = tmp.get(0); // 左
        c21 = tmp.get(1); // 右

        startPoint = mPoints.get(2);
        controlPoint1 = c11;
        controlPoint2 = c20;
        endPoint = mPoints.get(3);

        // 绘制数据点和控制点
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);
        canvas.drawPoint(c11.x, c11.y, paint);
        canvas.drawPoint(c20.x, c20.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(8);

        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x,controlPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, paint);
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
