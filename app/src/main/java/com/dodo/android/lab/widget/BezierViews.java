package com.dodo.android.lab.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;


public class BezierViews extends View {

    protected Context context;
    protected Paint paint;
    protected Path path;

    private PointF start, end, control1, control2;

    List<PointF> mPoints;

    public BezierViews(Context context) {
        super(context);
        init(context, null);
    }

    public BezierViews(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BezierViews(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawARGB(255, 139, 197, 186);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
