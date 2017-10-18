package com.dodo.android.lab.features.interfaces;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodo.android.lab.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class InterfacesFragment extends Fragment {

    public InterfacesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interfaces, container, false);
    }
}
