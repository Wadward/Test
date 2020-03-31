package com.example.linetvtest.util.listener;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener
{
    private GestureDetector mGestureDetector;
    private ClickListener mClickListener;

    public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener)
    {
        mClickListener = clickListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if ((child != null) && (clickListener != null))
                {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event)
    {
        View child = recyclerView.findChildViewUnder(event.getX(), event.getY());

        if ((child != null) && (mClickListener != null) && (mGestureDetector.onTouchEvent(event)))
        {
            mClickListener.onClick(child, recyclerView.getChildPosition(child));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent event)
    {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
    {
    }

    public interface ClickListener
    {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
}