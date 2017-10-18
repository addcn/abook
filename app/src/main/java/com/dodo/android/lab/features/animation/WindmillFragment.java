package com.dodo.android.lab.features.animation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dodo.android.lab.R;
import com.dodo.android.lab.widget.windmill.WindPath;

/**
 * http://blog.csdn.net/yy497078141/article/details/52215622
 * 自定义视图+动画
 */
public class WindmillFragment extends Fragment {

    private View content;
    private WindPath mBigWindMill, mSmallWindMill;

    private SeekBar mSeekBar;
    private TextView mSpeedText;

    public WindmillFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_windmill, container, false);

        initViews();

        return content;
    }


    private void initViews() {
        mBigWindMill = (WindPath) content.findViewById(R.id.id_wind);
        mSmallWindMill = (WindPath) content.findViewById(R.id.id_windsmall);
        mSeekBar = (SeekBar) content.findViewById(R.id.id_seekbar);
        mSpeedText = (TextView) content.findViewById(R.id.speed);
        mSpeedText.setText(mSeekBar.getProgress() + "/" + mSeekBar.getMax());
        mBigWindMill.startAnim();
        mSmallWindMill.startAnim();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mSpeedText.setText(seekBar.getProgress() + "/" + mSeekBar.getMax());

                mBigWindMill.setWindvelocity(seekBar.getProgress());
                mSmallWindMill.setWindvelocity(seekBar.getProgress());
                mBigWindMill.startAnim();
                mSmallWindMill.startAnim();
            }
        });

    }


}
