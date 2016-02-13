package com.aacer.gifview.customView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;


import android.util.AttributeSet;
import android.view.View;

public class MyRecyclerView extends RecyclerView {

    public View getmCurrentView() {
        return mCurrentView;
    }

    /**
     * 记录当前第一个View
     */
    private View mCurrentView;

    public OnItemScrollChangeListener getmItemScrollChangeListener() {
        return mItemScrollChangeListener;
    }

    private OnItemScrollChangeListener mItemScrollChangeListener;

    public void setOnItemScrollChangeListener(
            OnItemScrollChangeListener mItemScrollChangeListener) {
        this.mItemScrollChangeListener = mItemScrollChangeListener;
    }

    public interface OnItemScrollChangeListener {
        void onChange(View view, int position);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnScrollListener(new OnScrollListener() {
                                @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
               View newView = getChildAt(0);

              if (mItemScrollChangeListener != null) {
                    if (newView != null && newView != mCurrentView) {
                        mCurrentView = newView;
                        mItemScrollChangeListener.onChange(mCurrentView,
                                getChildPosition(mCurrentView));

                    }
              }}


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mCurrentView = getChildAt(0);
            }
        }
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mCurrentView = getChildAt(0);

        if (mItemScrollChangeListener != null) {
            mItemScrollChangeListener.onChange(mCurrentView,
                    getChildPosition(mCurrentView));
        }
    }


}
