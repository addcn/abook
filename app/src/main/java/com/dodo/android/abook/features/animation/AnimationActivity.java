package com.dodo.android.abook.features.animation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dodo.android.abook.R;
import com.dodo.android.abook.features.graphics.ExampleFragment;

public class AnimationActivity extends AppCompatActivity {

    private AnimationFragment animationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the fragment
        animationFragment = (AnimationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (animationFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            animationFragment = new AnimationFragment();
            transaction.add(R.id.fragment, animationFragment);
            transaction.commit();
        }

        initViews();
    }

    /**
     * 初始視圖
     */
    private void initViews() {

    }

}
