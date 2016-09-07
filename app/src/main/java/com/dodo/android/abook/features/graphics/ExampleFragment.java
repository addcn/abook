package com.dodo.android.abook.features.graphics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.android.abook.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExampleFragment extends Fragment {

    public ExampleFragment() {
    }

    public static ExampleFragment newInstance() {
        ExampleFragment fragment = new ExampleFragment();
        Bundle bundle = new Bundle();
        //bundle.putString("listId", listId);
        //bundle.putString("indexId", indexId);
        //fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_example, container, false);
    }
}
