package com.dodo.android.lab.features.graphics;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dodo.android.lab.R;
import com.dodo.android.lab.widget.MyPathMeasure;
import com.dodo.android.lab.widget.spider.CircularLayout;
import com.dodo.android.lab.widget.spider.SpiderWebScoreView;


public class DrawTextFragment extends Fragment {

    private View content;
    private MyPathMeasure myView;

    public DrawTextFragment() {
        // Required empty public constructor
    }


    public static DrawTextFragment newInstance() {
        DrawTextFragment fragment = new DrawTextFragment();
        Bundle bundle = new Bundle();
        //bundle.putString("listId", listId);
        //bundle.putString("indexId", indexId);
        //fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    private static final int START_ANIM = 0;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_ANIM:
                    myView.startPathAnim(2000);
                    mHandler.sendEmptyMessageDelayed(START_ANIM, 3000);
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_drawtext, container, false);
        //initViews();
        return content;
    }

    private void initViews() {
        //setContentView(mView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        //myView = (MyPathMeasure) content.findViewById(R.id.myView);
        mHandler.sendEmptyMessage(START_ANIM);
    }

    protected void initSpider() {


        SpiderWebScoreView spiderWebScoreView1 = (SpiderWebScoreView) content.findViewById(R.id.spiderWeb_mainActivity_1);
        CircularLayout circularLayout1 = (CircularLayout) content.findViewById(R.id.layout_mainActivity_circular1);

        Score[] scores = new Score[]{
                new Score(7.0f, R.mipmap.ic_launcher),
                new Score(8.0f, R.mipmap.ic_launcher),
                new Score(5.0f, R.mipmap.ic_launcher),
                new Score(5.0f, R.mipmap.ic_launcher),
                new Score(8.0f, R.mipmap.ic_launcher),
                new Score(7.0f, R.mipmap.ic_launcher),
        };
        setup(spiderWebScoreView1, circularLayout1, scores);
    }

    private void setup(SpiderWebScoreView spiderWebScoreView, CircularLayout circularLayout, Score... scores){
        float[] scoreArray = new float[scores.length];
        for(int w = 0; w < scores.length; w++){
            scoreArray[w] = scores[w].score;
        }
        spiderWebScoreView.setScores(10f, scoreArray);

        circularLayout.removeAllViews();
        for(Score score : scores){
            TextView scoreTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.score, circularLayout, false);
            scoreTextView.setText(score.score+"");
            if(score.iconId != 0){
                scoreTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, score.iconId, 0);
            }
            circularLayout.addView(scoreTextView);
        }
    }

    public static class Score{
        public float score;
        public int iconId;

        public Score(float score, int iconId) {
            this.score = score;
            this.iconId = iconId;
        }

        public Score(float score) {
            this.score = score;
        }
    }

}
