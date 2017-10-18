package com.dodo.android.lab.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 *
 * Android小练习——Path绘制不规则图形的点击
 * http://www.jianshu.com/p/772621ca71f9
 *
 */
public class TaiJiView extends View {
    //黑色区域
    private Paint mPaint;
    private Path blackPath;
    private Region blackRegion;
    //白色区域
    private Path whitePath;
    private Region whiteRegion;
    private float center;//中心点
    private final int BLACK_FLAG = 0;
    private final int WRITE_FLAG = 1;
    private int touchFlag = -1;

    private TaiJiListener mListener;

    public TaiJiView(Context context) {
        super(context, null);
        initPaint();
    }

    public TaiJiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        //黑色路径
        blackPath = new Path();
        //黑色区域
        blackRegion = new Region();
        //白色路径
        whitePath = new Path();
        //白色区域
        whiteRegion = new Region();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int side = w > h ? h : w;//取小的值
        Region totalRegion = new Region(0, 0, w, h);
        RectF bigRectF = new RectF(0, 0, side, side);
        center = side / 2;
        RectF smallTopRectF = new RectF(center / 2, 0, (center + center / 2), center);
        RectF smallBottomRectF = new RectF(center / 2, center, (center + center / 2), h);
        //添加黑色路径信息
        blackPath.addArc(bigRectF, 90, 180);
        blackPath.moveTo(center, 0);
        blackPath.arcTo(smallTopRectF, 270, 180);
        blackPath.moveTo(center, center);
        blackPath.arcTo(smallBottomRectF, 270, -180);
        //将黑色路径信息存入Region
        blackRegion.setPath(blackPath, totalRegion);
        //添加白色路径信息
        whitePath.addArc(bigRectF, 270, 180);
        whitePath.moveTo(center, 0);
        whitePath.arcTo(smallTopRectF, -270, -180);
        whitePath.moveTo(center, center);
        whitePath.arcTo(smallBottomRectF, 90, 180);
        //将白色路径信息存入Region
        whiteRegion.setPath(whitePath, totalRegion);
    }


    /**
     * 触摸消费事件
     * 需要考虑当落下的点和抬起的点是否在同一个有效区域内
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int currentFlag = -1;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = getTouchFlag(x, y);
                currentFlag = touchFlag;
                break;
            case MotionEvent.ACTION_MOVE:
                currentFlag = getTouchFlag(x, y);
                break;
            case MotionEvent.ACTION_UP:
                currentFlag = getTouchFlag(x, y);
                if (null != mListener && currentFlag == touchFlag && currentFlag != -1) {
                    if (currentFlag == BLACK_FLAG) {
                        mListener.onBlackClick();
                    } else if (currentFlag == WRITE_FLAG) {
                        mListener.onWriteClick();
                    }
                }
                touchFlag = currentFlag = -1;
                break;
            case MotionEvent.ACTION_CANCEL:
                touchFlag = currentFlag = -1;
                break;
        }
        Log.e("currentFlag", "-->" + currentFlag);
        return true;
    }

    /**
     * 判断落在哪个区域
     */
    private int getTouchFlag(int x, int y) {
        if (blackRegion.contains(x, y)) {
            return BLACK_FLAG;
        } else if (whiteRegion.contains(x, y)) {
            return WRITE_FLAG;
        }
        return -1;
    }

    public void setListener(TaiJiListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 设置触摸监听
     */
    public interface TaiJiListener {
        void onBlackClick();

        void onWriteClick();
    }

    /**
     * 绘制方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制黑色路径
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(blackPath, mPaint);

        //绘制白色路径
        mPaint.setColor(Color.WHITE);
        canvas.drawPath(whitePath, mPaint);
        //绘制白色小圆
        canvas.drawCircle(center, center / 2, 60, mPaint);

        //绘制黑色小圆
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(center, (center + center / 2), 60, mPaint);
    }

    /**
     * 测量方法
     * 当宽高都为 wrap_content 时，图形最小为 300 * 300
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }
}

