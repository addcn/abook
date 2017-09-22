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

/**
 * Created by 10113 on 2017/9/22.
 */

public class PhotoshopBezier extends View {

    protected Context context;
    protected Paint paint;

    private PointF start, end, control1, control2;

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

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(60);

        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control1 = new PointF(0, 0);
        control2 = new PointF(0, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(255, 139, 197, 186);

        // 初始化数据点和控制点的位置
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
        canvas.drawLine(control1.x, control1.y,control2.x, control2.y, paint);
        canvas.drawLine(control2.x, control2.y,end.x, end.y, paint);

        // 绘制贝塞尔曲线
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);

        Path path = new Path();

        path.moveTo(start.x, start.y);
        path.cubicTo(control1.x, control1.y, control2.x,control2.y, end.x, end.y);

        canvas.drawPath(path, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

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
        }

    }
}
