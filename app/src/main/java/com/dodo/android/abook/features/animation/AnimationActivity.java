package com.dodo.android.abook.features.animation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.dodo.android.abook.R;
import com.dodo.android.abook.features.graphics.ExampleFragment;

/**
 * Android动画
 *
 * @author <a href="mailto:lhuibo@gmail.com">dodo</a> 2016-09-07
 * @version ${Id}
 */

public class AnimationActivity extends AppCompatActivity {

    private AnimationFragment animationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        return true;
    }

}
