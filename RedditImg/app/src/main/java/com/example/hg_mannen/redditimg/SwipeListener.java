package com.example.hg_mannen.redditimg;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hg-mannen on 2015-11-01.
 */
public class SwipeListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
    public final GestureDetector gestureDetector;

    public SwipeListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent motion) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent firstpoint, MotionEvent secondpoint, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = secondpoint.getY() - firstpoint.getY();
                float diffX = secondpoint.getX() - firstpoint.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }

    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

}
