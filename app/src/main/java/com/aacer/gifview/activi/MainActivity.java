package com.aacer.gifview.activi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aacer.gifview.R;
import com.aacer.gifview.customView.GifView;
import com.aacer.gifview.customView.LoveHeart;

/**
 * Created by acer on 2016/2/12.
 */
public class MainActivity extends Activity {

    private LoveHeart loveHeart;
    private LoveHeart.MyThread myThread;
    private GifView gv1;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loveHeart = new LoveHeart(this);
        myThread = loveHeart.getMyThread();

        if (savedInstanceState == null) {
            Log.w(this.getClass().getName(), "SIS is null");
        } else {
            // we are being restored: resume a previous game
            myThread.restoreSavaInstance(savedInstanceState);
            Log.w(this.getClass().getName(), "SIS is nonnull");
        }
        setContentView(R.layout.first_show_layout);
        gv1 = (GifView) findViewById(R.id.glFireHeart);
        textView = (TextView) findViewById(R.id.tvLove);
        gv1.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        textView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myThread.saveInstance(outState);
    }

    @Override
    protected void onPause() {
        myThread.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myThread.wakeCanvas();
    }
}