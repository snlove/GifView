package com.aacer.gifview.customView;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.aacer.gifview.R;

import java.io.InputStream;


/**
 * Created by acer on 2016/2/9.
 */
public class GifView extends View {

    private Resources rs;
    private InputStream is;
    private static final boolean DECODE_STREAM = true;
    private Movie mMovie;
    private long mMovieStart;
    public static final String TAG = "TAG";
    private float rationWidth;
    private float rationHeight;

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        rs = context.getResources();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GifView);
        int resId = ta.getResourceId(R.styleable.GifView_src, -1);
        Log.d(TAG, "GifView: run this contrctor");
        setResouceId(resId);
        ta.recycle();
    }

    public void setResouceId(int resId) {
        if (resId == -1) {
            Log.d(TAG, "setResouceId: ");
            return;
        }

        is = rs.openRawResource(resId);
        Log.d(TAG, "setInputGif: " + (is == null));
        mMovie = Movie.decodeStream(is);
        requestLayout();

    }

    public void setInputGif(InputStream is) {
        Log.d(TAG, "setInputGif: " + (is == null));
        mMovie = Movie.decodeStream(is);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w;
        int h;

        if (mMovie == null) {
            // If no drawable, its intrinsic size is 0.
            w = h = 0;
        } else {
            w = mMovie.width();
            h = mMovie.height();
            if (w <= 0) w = 1;
            if (h <= 0) h = 1;
        }

        int pleft = getPaddingLeft();
        int pright = getPaddingRight();
        int ptop = getPaddingTop();
        int pbottom = getPaddingBottom();

        int widthSize;
        int heightSize;


        w += pleft + pright;
        h += ptop + pbottom;

        w = Math.max(w, getSuggestedMinimumWidth());
        h = Math.max(h, getSuggestedMinimumHeight());
        widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

        rationWidth = (float)widthSize / w;
        rationHeight = (float)heightSize / h;
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {   // first time
            mMovieStart = now;
        }

        if (mMovie != null) {
            int dur = mMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }
            int relTime = (int) ((now - mMovieStart) % dur);
            mMovie.setTime(relTime);
//            mMovie.draw(canvas, getWidth() - mMovie.width(),
//                    getHeight() - mMovie.height());
            canvas.scale(Math.max(rationHeight,rationWidth),Math.min(rationHeight,rationWidth));
            mMovie.draw(canvas,0,0);
            invalidate();
        }
    }
}
