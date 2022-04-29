package com.theking.pokemonnormal;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;



public class MySwipeListener implements View.OnTouchListener {
    private GestureDetector gestureDetector;
    Context context;
    public MySwipeListener(Context context) {
        this.context =context;
        gestureDetector = new GestureDetector(context,new GestureListener());
    }
    public void OnSwipe() {
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        View view = v.getRootView();
        float ix=0,fx;
        switch (event.getAction()) {
            case  MotionEvent.ACTION_DOWN :
                ix=event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                fx=event.getX()-ix;
                if(fx>0) {
                    v.setLeft(v.getLeft()+(int)(fx/context.getResources().getDisplayMetrics().density));
                    view.invalidate();
                }
                else
                {
                    v.setRight((int)-(fx/context.getResources().getDisplayMetrics().density));
                    view.invalidate();
                }
                break;
            case MotionEvent.ACTION_UP :
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e1.getX()-e2.getX();
            if(Math.abs(x)>SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX)>SWIPE_VELOCITY_THRESHOLD ) {
                OnSwipe();
                return true;
            }
            return false;
        }


    }

}
