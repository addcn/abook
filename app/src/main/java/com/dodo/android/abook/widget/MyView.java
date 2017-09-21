package com.dodo.android.abook.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
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

    protected Context context;
    protected Paint paint;

    protected int mWidth;
    protected int mHeight;

    /**
     * 用于代码创建控件
     *
     * @param context
     */
    public MyView(Context context) {
        super(context, null);
        init(context, null);
    }

    /**
     * 用于在XML中使用，可以指定自定义属性
     *
     * @param context
     * @param attrs
     */
    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    /**
     * 用于在XML中使用，可以指定自定义属性，并指定样式
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 用于在XML中使用，可以指定自定义属性，并指定样式及其资源
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        // 设置颜色
        //paint.setColor(circleColor);
        // 防抖动
        paint.setDither(true);
        // 抗锯齿
        paint.setAntiAlias(true);
        // 描边
        paint.setStyle(Paint.Style.STROKE);
        // 指定填充模式
        paint.setStyle(Paint.Style.FILL);
        // 文字水平居中
        paint.setTextAlign(Paint.Align.CENTER);
        /*if (attributeSet != null) {
            TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.CirclePoint, 0, 0);
            if (attr == null) {
                return;
            }
            try {
                circleWidth = attr.getDimensionPixelSize(R.styleable.CirclePoint_circle_width, 0);
                circleColor = attr.getColor(R.styleable.CirclePoint_circle_color, Color.WHITE);
                text = attr.getString(R.styleable.CirclePoint_circle_text);
                textSize = attr.getDimensionPixelSize(R.styleable.CirclePoint_circle_text_size, 0);
                textColor = attr.getColor(R.styleable.CirclePoint_circle_text_color, Color.WHITE);
            } finally {
                attr.recycle();
            }
        }*/
    }

    /**
     * 自定义View会在这个阶段获取其自身的大小。
     * 如果想要支持wrap_content属性，就必须重写onMeasure方法，如下所示（可以当做模板代码）。需要做的就是设置setMeasuredDimension(int width, int height)
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));

        /**
        MeasureSpec有三种模式：
            MeasureSpec.EXACTLY表示用户设置了具体的值，所以无论你的视图有多大，都需要设置具体的宽或高。
            MeasureSpec.AT_MOST表示该视图的尺寸为其所需的具体大小。（注：wrap_content）
            MeasureSpec.UNSPECIFIED表示会包裹整个视图的大小。使用上面计算的desiredWidth或desiredHeight。（注：match_parent）
        */

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //view支持wrap_content
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //支持padding，不然padding属性无效
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        //canvas.drawText(emptyText, (float) ((width - textRect.width()) * 0.5), (float) (height * 0.5), paint);
        //canvas.drawBitmap(bitmap, (float) ((width - bitmap.getWidth()) * 0.5), (float) (height * 0.5 - bitmap.getHeight() - 100), mPaint);


        int r = getMeasuredWidth() / 2;//也可以是getMeasuredHeight()/2,本例中我们已经将宽高设置相等了
        //圆心的横坐标为当前的View的左边起始位置+半径
        int centerX = getLeft() + r;
        //圆心的纵坐标为当前的View的顶部起始位置+半径
        int centerY = getTop() + r;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        //开始绘制
        canvas.drawCircle(centerX, centerY, r, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


}
