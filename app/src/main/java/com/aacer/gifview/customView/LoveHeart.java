package com.aacer.gifview.customView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by acer on 2016/2/12.
 */
public class LoveHeart extends SurfaceView  implements SurfaceHolder.Callback
{

    //    定义画笔和画布
    private Canvas canvas;
    private Paint myPaint;
    private MyThread myThread;
    private SurfaceHolder surfaceHolder;

    private static final String TAG = "TAG";


    public LoveHeart(Context context) {
        super(context);
        initHeart();

        myThread = new MyThread(surfaceHolder,context);
    }


    private void initHeart() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        myPaint = new Paint();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

           myThread.setRunning(true);
           myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
              myThread.setSurfaceSize(width,height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        myThread.setRunning(false);
        while (retry) {
            try {
                myThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }



    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            myThread.onPause();
        }
    }

    public MyThread getMyThread() {
    return myThread;
}

//官方建议开另一个线程进行绘画
    public class MyThread extends  Thread {

        private SurfaceHolder holder;
        private Context context;
        private Handler handler;

        /*
         * 绘画线程的状态
         */

        public static final int STATE_PAUSE = 2;
        public static final int STATE_READY = 3;
        public static final int STATE_RUNNING = 4;

        private final Object mRunLock = new Object();
        /*
        定义相关的变量
         */
        private Resources resources;
        private Paint myPaint;
        private int mode;
        private boolean mRun = false;
        private long mLastTime;
        private int mcount = 0;

        /**
         *
         * @param holder
         * @param context
         * @param handler
         */
        public MyThread(SurfaceHolder holder,Context context,Handler handler) {
            this.holder = holder;
            this.context = context;
            this.handler = handler;

            resources = context.getResources();
            myPaint = new Paint();
            myPaint.setColor(Color.RED);
        }

        public MyThread(SurfaceHolder holder,Context context) {
            this.holder = holder;
            this.context = context;
            resources = context.getResources();
            myPaint = new Paint();

        }



        /**
        *暂停方法
        *@author sn
        * 2016/2/13  11:38
        *
        */
        public void onPause() {
                synchronized (surfaceHolder) {
                    if(mode == STATE_RUNNING) {
                        setState(STATE_PAUSE);
                    }
                }
        }

        /**
         * 保存相关的数据
         *
         * @author sn
         * 2016/2/13  11:51
         */

        public void saveInstance(Bundle map) {
            synchronized (surfaceHolder) {
                if(map != null) {
                    //保存相关的数据信息
                }
            }
        }

        /**
        *得到相关的数据信息
        *@author sn
        * 2016/2/13  11:53
        *
        */
        public synchronized  void restoreSavaInstance(Bundle saveState) {
            synchronized (surfaceHolder) {
                setState(STATE_PAUSE);
            }
        }

        /**
         * Resumes from a pause.
         */
        public void wakeCanvas() {
            // Move the real time clock up to now
            synchronized (surfaceHolder) {
                mLastTime = System.currentTimeMillis() + 100;
            }
            setState(STATE_RUNNING);
        }


        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (surfaceHolder) {
               // fixed the size
            }
        }


        private void doDraw(Canvas canvas) {
            try {
                if (canvas == null || surfaceHolder == null) {
                    return;
                }

                //define the number count, to show diffent color
                if(mcount <= 100) {
                    mcount++;
                } else {
                    mcount = 0;
                }
                myPaint.setAntiAlias(true);
                myPaint.setColor(Color.BLACK);
                canvas.drawRect(0, 0, 320, 480, myPaint);
                //随时间发生改变
                switch (mcount%6) {
                    case 1 :
                        myPaint.setColor(Color.CYAN);
                        break;
                    case 2 :
                        myPaint.setColor(Color.GREEN);
                        break;
                    case 3:
                        myPaint.setColor(Color.YELLOW);
                        break;
                    case 4 :
                        myPaint.setColor(Color.BLUE);
                        break;
                    case 5:
                        myPaint.setColor(Color.RED);
                        break;
                    case 6 :
                        myPaint.setColor(Color.argb(255, 255, 181, 216));
                        break;
                    default:
                        myPaint.setColor(Color.WHITE);
                        break;
                }

                //根据函数画圆
                int i, j;
                 double x, y, r;
                for (i = 0; i <= 90; i++)
                for (j = 0; j<= 90; j++) {
                    r = Math.PI / 45 * i * (1 - Math.sin(Math.PI / 45 * j)) * 20;
                    x = r * Math.cos(Math.PI / 45 * j) * Math.sin(Math.PI / 45 * i) + getWidth()/2;
                    y = -r * Math.sin(Math.PI / 45 * j) + getHeight()/2 - 100;
                    canvas.drawPoint((float) x, (float) y, myPaint);
                }

                myPaint.setTextSize(32);
                myPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.ITALIC));
                canvas.drawText("小婷，我爱你直到永远", getWidth()/2 -140, getHeight() - 400, myPaint);

            } catch (Exception e) {

            }




        }





        @Override
        public void run() {
            while (mRun) {
                Log.d(TAG, "run: join the run");
                Canvas c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        if (mode == STATE_RUNNING)
                            Log.d(TAG, "run: the state is running");
                        // Critical section. Do not allow mRun to be set false until
                        // we are sure all canvas draw operations are complete.
                        //
                        // If mRun has been toggled false, inhibit canvas operations.
                        Thread.sleep(200);
                        synchronized (mRunLock) {
                                doDraw(c);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // do this in a finally so that if an exception is thrown
                    // during the above, we don't leave the Surface in an
                    // inconsistent state
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }

        public void setRunning(boolean b) {
            // Do not allow mRun to be modified while any canvas operations
            // are potentially in-flight. See doDraw().
            synchronized (mRunLock) {
                mRun = b;
            }
        }
        public void setState(int state) {
            this.mode = state;
        }
    }
}
