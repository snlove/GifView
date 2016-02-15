package com.aacer.gifview.customView;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.aacer.gifview.R;

/**
 * Created by acer on 2016/2/13.
 */
public class LoveText extends TextView {

    private static final String TAG = "TAG";
    private int contentId;
    private String content;
    private Resources rs;
    private Paint myPaint;
    private  Path path;
  

    public LoveText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoveText);
        contentId = typedArray.getResourceId(R.styleable.LoveText_lovetext,-1);
        rs = context.getResources();
        setContent(contentId);
        typedArray.recycle();
        myPaint = new Paint();
        path = new Path();
    }

    public LoveText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(int contentId) {
       if (contentId == -1) {
           Log.d(TAG, "setContent: the conten is null");
           return;
       }
        content = rs.getString(R.string.first_show);
        Log.d(TAG, "setContent: " + content);
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }
        path.addCircle(getWidth()/2, getHeight()/2, 10.0f, Path.Direction.CW);
        myPaint.setColor(Color.RED);
        canvas.drawTextOnPath(content, path, 0, 0, myPaint);
        invalidate();
    }
}
