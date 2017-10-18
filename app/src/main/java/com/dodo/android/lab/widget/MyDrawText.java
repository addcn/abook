package com.dodo.android.lab.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义控件之绘图篇（ 五）：drawText()详解
 * http://blog.csdn.net/harvic880925/article/details/50423762
 *
 * Created by qijian on 15/12/21.
 */
public class MyDrawText extends View{

    public MyDrawText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 一、概述
         */
//        drawBaseLine(canvas);

        /**
         * 二、drawText的四线格与FontMetrics
         */
//        drawAllLines(canvas);

        /**
         * 三、所绘文字宽度、高度和最小矩形获取
         */
//        drawTextRect(canvas);


        /**
         * 四.1、给定左上顶点绘图
         */
//        drawTextBaseTop(canvas);

        /**
         * 四.2、给定中间线位置绘图
         */
        drawTextBaseCenter(canvas);
    }

    private void drawBaseLine(Canvas canvas){
        int baseLineY = 200;
        int baseLineX = 0 ;

        //画基线
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("harvic\'s blog", baseLineX, baseLineY, paint);

//        canvas.drawText("A", baseLineX, baseLineY, paint);
    }


    private void drawAllLines(Canvas canvas){
        int baseLineY = 200;
        int baseLineX = 0 ;

        Paint paint = new Paint();

        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("harvic\'s blog", baseLineX, baseLineY, paint);

        //计算各线在位置
        Paint.FontMetrics fm = paint.getFontMetrics();
        float ascent = baseLineY + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //画top
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //画ascent
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent, 3000, descent, paint);

        //画bottom
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);
    }

    private void drawTextRect(Canvas canvas){
        String text = "harvic\'s blog";
        int baseLineY = 200;
        int baseLineX = 0 ;

        //设置paint
        Paint paint = new Paint();
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);

        //画text所占的区域
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int top = baseLineY + fm.top;
        int bottom = baseLineY + fm.bottom;
        int width = (int)paint.measureText(text);
        Rect rect = new Rect(baseLineX,top,baseLineX+width,bottom);

        paint.setColor(Color.GREEN);
        canvas.drawRect(rect,paint);

        //画最小矩形
        Rect minRect = new Rect();
        paint.getTextBounds(text,0,text.length(),minRect);
        minRect.top = baseLineY + minRect.top;
        minRect.bottom = baseLineY + minRect.bottom;
        paint.setColor(Color.RED);
        canvas.drawRect(minRect,paint);

        //写文字
        paint.setColor(Color.BLACK);
        canvas.drawText(text, baseLineX, baseLineY, paint);

    }


    private void drawTextBaseTop(Canvas canvas){

        String text = "harvic\'s blog";
        int top = 200;
        int baseLineX = 0 ;

        //设置paint
        Paint paint = new Paint();
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);

        //画top线
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //得到FontMetricsInt对象
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();

        //计算出baseLine位置
        int baseLineY = top - fm.top;
        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        canvas.drawText(text, baseLineX, baseLineY, paint);
    }

    private void drawTextBaseCenter(Canvas canvas){
        String text = "harvic\'s blog";
        int center = 200;
        int baseLineX = 0 ;

        //设置paint
        Paint paint = new Paint();
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);

        //画center线
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, center, 3000, center, paint);

        //得到FontMetricsInt对象
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();

        //计算出baseLine位置
        int baseLineY = center + (fm.bottom - fm.top)/2 - fm.bottom;
        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        canvas.drawText(text, baseLineX, baseLineY, paint);
    }
}
