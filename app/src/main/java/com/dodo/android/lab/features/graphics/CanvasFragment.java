package com.dodo.android.lab.features.graphics;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.dodo.android.lab.R;
import com.dodo.android.lab.widget.DragBubbleView;

/**
 * A placeholder fragment containing a simple view.
 */
public class CanvasFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {

    CheckBox mCbFill;
    CheckBox mCbCircle;
    SeekBar mPbRadio;
    DragBubbleView mBubble;

    public CanvasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_canvas, container, false);

        mCbFill = (CheckBox) rootView.findViewById(R.id.cb_fill);
        mCbCircle = (CheckBox) rootView.findViewById(R.id.cb_circle);
        mPbRadio = (SeekBar) rootView.findViewById(R.id.pb_cirRadius);
        mBubble = (DragBubbleView) rootView.findViewById(R.id.dbv);

        mCbFill.setChecked(mBubble.getFillDraw());
        mCbCircle.setChecked(mBubble.isShowCircle());
//        mBubble.setEnabled(false);
        mCbFill.setOnCheckedChangeListener(this);
        mPbRadio.setOnSeekBarChangeListener(this);
        mCbCircle.setOnCheckedChangeListener(this);


        return rootView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_fill:
                mBubble.setFillDraw(isChecked);
                break;
            case R.id.cb_circle:
                mBubble.setShowCircle(isChecked);
                break;

        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mBubble.setOriginR(progress + 30);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
