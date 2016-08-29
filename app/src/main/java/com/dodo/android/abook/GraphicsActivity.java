package com.dodo.android.abook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class GraphicsActivity extends AppCompatActivity {

    private DrawTextFragment drawTextFragment;
    private CanvasFragment canvasFragment;
    private ExampleFragment exampleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the fragment
        drawTextFragment = (DrawTextFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (drawTextFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            canvasFragment = new CanvasFragment();
            transaction.add(R.id.fragment,canvasFragment);
            transaction.commit();
        }

        initViews();
    }

    /**
     * 初始視圖
     */
    private void initViews() {
        //
        TextView canvasTv = (TextView)findViewById(R.id.tv_canvas);
        canvasTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                canvasFragment = new CanvasFragment();
                //transaction.add(R.id.fragment,canvasFragment);
                transaction.replace(R.id.fragment,canvasFragment);
                transaction.commit();
            }
        });
        //
        TextView drawTextTv = (TextView)findViewById(R.id.tv_draw_text);
        drawTextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                drawTextFragment = DrawTextFragment.newInstance();
                //transaction.add(R.id.fragment,drawTextFragment);
                transaction.replace(R.id.fragment,drawTextFragment);
                transaction.commit();
            }
        });
        //
        TextView exampleTv = (TextView)findViewById(R.id.tv_example);
        exampleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                exampleFragment = new ExampleFragment();
                //transaction.add(R.id.fragment,drawTextFragment);
                transaction.replace(R.id.fragment,exampleFragment);
                transaction.commit();
            }
        });



    }
}
