package com.dodo.android.abook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dodo.android.abook.features.animation.AnimationActivity;
import com.dodo.android.abook.features.events.EventActivity;
import com.dodo.android.abook.features.graphics.GraphicsActivity;

public class MainActivity extends AppCompatActivity {

    private TextView canvasTv, animTv, eventTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(new MyView(this));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    public void initViews() {
        //
        canvasTv = (TextView) findViewById(R.id.tv_canvas);
        canvasTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GraphicsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("channelId", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                //
                hghlightSel(v);
            }
        });
        //
        animTv = (TextView) findViewById(R.id.tv_anim);
        animTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AnimationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("channelId", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                //
                hghlightSel(v);
            }
        });
        //
        eventTv = (TextView) findViewById(R.id.tv_event);
        eventTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, EventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("channelId", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                //
                hghlightSel(v);
            }
        });


    }

    private void hghlightSel(View view) {
        canvasTv.setBackgroundColor(0xffdddddd);
        animTv.setBackgroundColor(0xffdddddd);
        eventTv.setBackgroundColor(0xffdddddd);

        ((TextView) view).setBackgroundColor(0xffffffff);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
