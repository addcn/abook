package com.dodo.android.abook.features.graphics;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dodo.android.abook.R;

/**
 * Android自定义视图
 *
 * @author <a href="mailto:lhuibo@gmail.com">dodo</a> 2016-09-07
 * @version ${Id}
 */
public class GraphicsActivity extends AppCompatActivity {

    private DrawTextFragment drawTextFragment;
    private CanvasFragment canvasFragment;
    private ExampleFragment exampleFragment;

    private TextView canvasTv, drawTextTv, exampleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the fragment
        exampleFragment = (ExampleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (drawTextFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            exampleFragment = new ExampleFragment();
            transaction.add(R.id.fragment, exampleFragment);
            transaction.commit();
        }

        initViews();
    }

    /**
     * 初始視圖
     */
    private void initViews() {
        //
        canvasTv = (TextView) findViewById(R.id.tv_canvas);
        drawTextTv = (TextView) findViewById(R.id.tv_draw_text);
        exampleTv = (TextView) findViewById(R.id.tv_example);

        //
        canvasTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                canvasFragment = new CanvasFragment();
                //transaction.add(R.id.fragment,canvasFragment);
                transaction.replace(R.id.fragment, canvasFragment);
                transaction.commit();

                //
                hghlightSel(v);
            }
        });

        //
        drawTextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                drawTextFragment = DrawTextFragment.newInstance();
                //transaction.add(R.id.fragment,drawTextFragment);
                transaction.replace(R.id.fragment, drawTextFragment);
                transaction.commit();

                //
                hghlightSel(v);
            }
        });

        //
        exampleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                exampleFragment = new ExampleFragment();
                //transaction.add(R.id.fragment,drawTextFragment);
                transaction.replace(R.id.fragment, exampleFragment);
                transaction.commit();

                //
                hghlightSel(v);
            }
        });
    }


    private void hghlightSel(View view) {
        canvasTv.setBackgroundColor(0xffdddddd);
        drawTextTv.setBackgroundColor(0xffdddddd);
        exampleTv.setBackgroundColor(0xffdddddd);

        ((TextView) view).setBackgroundColor(0xffffffff);
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
