package com.dodo.android.abook.features.events;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.android.abook.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventActivityFragment extends Fragment {

    public EventActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }
}
