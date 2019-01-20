package com.example.arsalan.kavosh.model;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView {

    int height = 0;//= getChildAt(0).getHeight() + 1;
    /*    public MyListView  (Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyListView  (Context context) {
            super(context);
        }

        public MyListView  (Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
        }*/
    private android.view.ViewGroup.LayoutParams params;
    private int prevCount = 0;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getCount() != prevCount) {
            for (int h = 0; h < getCount(); h++) {
                if (getChildAt(h) != null)
                    height += getChildAt(h).getHeight() + 1;
            }
            prevCount = getCount();
            params = getLayoutParams();
            if (getOverscrollHeader() != null) {
                height += getOverscrollHeader().getBounds().height();
            }
            params.height = height;// getCount() * height;
            setLayoutParams(params);
        }

        super.onDraw(canvas);
    }
}