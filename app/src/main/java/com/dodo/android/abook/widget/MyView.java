package com.dodo.android.abook.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View模板
 * Andorid官方文档中将该过程概况成了六步：
 * Draw the background
 * If necessary, save the canvas’ layers to prepare for fading
 * Draw view’s content
 * Draw children
 * If necessary, draw the fading edges and restore layers
 * Draw decorations (scrollbars for instance)

 * 参考文章：
 * http://gold.xitu.io/entry/57465c88c4c971005d6e4422
 *
 * Created by 10113 on 2016/9/2.
 */
public class MyView extends View {

    protected Context mContext;

    protected int mWidth;
    protected int mHeight;
    protected Paint mPaint = new Paint();


    public MyView(Context context) {
        super(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


}
