package com.dodo.android.abook.features.graphics;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.android.abook.R;
import com.dodo.android.abook.widget.MyPathMeasure;


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
}
