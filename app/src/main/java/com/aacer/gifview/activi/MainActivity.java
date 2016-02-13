package com.aacer.gifview.activi;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.aacer.gifview.customView.LoveHeart;

/**
 * Created by acer on 2016/2/12.
 */
public class MainActivity extends Activity {

    private LoveHeart loveHeart;
    private LoveHeart.MyThread myThread;
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
        setContentView(loveHeart);
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