package com.dodo.android.abook.features.animation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.android.abook.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AnimationFragment extends Fragment {

    public AnimationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animation, container, false);
    }
}
