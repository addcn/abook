package com.dodo.android.lab.features.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dodo.android.lab.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AnimationFragment extends Fragment {

    private View content;


    public AnimationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_animation, container, false);

        initViews();

        return content;
    }



    private void initViews() {
        final Button bt = (Button) content.findViewById(R.id.button);
        final ImageView iv = (ImageView) content.findViewById(R.id.imageView);
        final LinearLayout root = (LinearLayout) content.findViewById(R.id.wp);//根布局

        //改变背景属性
        //http://www.jianshu.com/p/6a53ce436fd9#
        final ValueAnimator colorAnim = ObjectAnimator.ofInt(iv, "backgroundColor", Color.parseColor("#FF4081"), Color.CYAN);
        colorAnim.setRepeatCount(2);
        colorAnim.setRepeatMode(ObjectAnimator.REVERSE);
        colorAnim.setDuration(1000);
        colorAnim.setEvaluator(new ArgbEvaluator());//估值器

        //动画集合
        final AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(iv, "rotationX", 0, 360),//绕x轴旋转360度
                ObjectAnimator.ofFloat(iv, "rotation", 0, -90),//逆时针旋转90度
                ObjectAnimator.ofFloat(iv, "translationX", 0, 90),//右移
                ObjectAnimator.ofFloat(iv, "scaleY", 1, 0.5f),//y轴缩放到一半
                ObjectAnimator.ofFloat(iv, "alpha", 1, 0.25f, 1)//透明度变换
        );
        //延迟一秒开始
        set.setStartDelay(1000);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //改变属性 位置  向下移动iv高的二分之一
                ObjectAnimator.ofFloat(iv, "translationY", iv.getHeight() / 2).start();
                //背景属性改变开始
                colorAnim.start();
                //集合动画
                set.setDuration(3000).start();

            }
        });

        cc();

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                byPropertyValuesHolder(iv);

                play();

            }
        });


    }

    /**
     * 帧动画
     * http://www.jianshu.com/p/c621abccf898
     */
    private void framesPlay() {
       /* ImageView iv = (ImageView) findViewById(R.id.iv_frames_animation_activity);
        Button bt_start= (Button) findViewById(R.id.bt_start_frames_animation_activity);
        Button bt_stop= (Button) findViewById(R.id.bt_stop_frames_animation_activity);

        iv.setBackgroundResource(R.drawable.frames_animation);
        AnimationDrawable animation = (AnimationDrawable) iv.getBackground();

        bt_start.setOnClickListener((v)-> animation.start());
        bt_stop.setOnClickListener((v)->animation.stop());*/
    }



    /**
     * 补间动画
     * http://www.jianshu.com/p/c621abccf898
     */
    public void play() {
        final Button bt = (Button) content.findViewById(R.id.button);
        final ImageView iv = (ImageView) content.findViewById(R.id.imageView);
        final LinearLayout root = (LinearLayout) content.findViewById(R.id.wp);//根布局

        //初始化动画
        final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_animation);
        //点击按钮开始动画
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.startAnimation(animation);
            }
        });
    }



    /**
     * ObjectAnimator类
     * @param iv
     */
    public void byPropertyValuesHolder(ImageView iv) {
        PropertyValuesHolder pvh_1 = PropertyValuesHolder.ofFloat("rotationX", 0, 360);
        PropertyValuesHolder pvh_2 = PropertyValuesHolder.ofFloat("rotation", 0, -90);
        PropertyValuesHolder pvh_3 = PropertyValuesHolder.ofFloat("alpha", 1, 0.25f, 1);
        ObjectAnimator.ofPropertyValuesHolder(iv, pvh_1, pvh_2, pvh_3).setDuration(1000).start();
    }

    /**
     * 插值器和估值器
     */
    public void cc() {

        final Button bt = (Button) content.findViewById(R.id.button);
        final ImageView iv = (ImageView) content.findViewById(R.id.imageView);
        final LinearLayout root = (LinearLayout) content.findViewById(R.id.wp);//根布局

        final AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(3000);
        //利用View的post方法拿到根布局的高度
        root.post(new Runnable() {
            @Override
            public void run() {
                //计算下降高度
                int height = root.getHeight() - iv.getHeight() - bt.getHeight();
                //设置动画
                set.play(ObjectAnimator.ofFloat(iv, "translationY", height));
            }
        });
    }


}
