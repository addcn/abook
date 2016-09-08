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
import com.dodo.android.abook.features.interfaces.InterfacesActivity;

/**
 * 项目主页
 *
 * @author <a href="mailto:lhuibo@gmail.com">dodo</a> 2016-09-07
 * @version ${Id}
 */

public class MainActivity extends AppCompatActivity {

    private TextView tab1, tab2,tab3,tab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(new MyView(this));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    /**
     * 初始視圖
     */
    public void initViews() {
        //
        tab1 = (TextView) findViewById(R.id.tab1);
        tab1.setOnClickListener(new View.OnClickListener() {
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
        tab2 = (TextView) findViewById(R.id.tab2);
        tab2.setOnClickListener(new View.OnClickListener() {
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
        tab3 = (TextView) findViewById(R.id.tab3);
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, InterfacesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("channelId", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                //
                hghlightSel(v);
            }
        });
        //
        tab4 = (TextView) findViewById(R.id.tab4);
        tab4.setOnClickListener(new View.OnClickListener() {
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
        tab1.setBackgroundColor(0xffdddddd);
        tab2.setBackgroundColor(0xffdddddd);
        tab3.setBackgroundColor(0xffdddddd);
        tab4.setBackgroundColor(0xffdddddd);

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
