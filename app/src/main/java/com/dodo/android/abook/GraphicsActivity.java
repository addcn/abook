package com.dodo.android.abook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class GraphicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the fragment
        DrawTextFragment drawTextFragment = (DrawTextFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (drawTextFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            drawTextFragment = DrawTextFragment.newInstance();
            transaction.add(R.id.fragment,drawTextFragment);
            transaction.commit();
        }

        initViews();
    }

    public void initViews(){
        TextView drawTextTv = (TextView)findViewById(R.id.tv_draw_text);
        drawTextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(GraphicsActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("channelId", "1");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
}
