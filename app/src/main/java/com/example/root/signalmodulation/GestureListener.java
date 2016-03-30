package com.example.root.signalmodulation;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.root.signalmodulation.elements.SignalRenderer;

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private final static int SWIPE_LEFT_RIGHT = 0;
    private final static int SWIPE_RIGHT_LEFT = 1;
    private final static int SWIPE_TOP_BOTTOM = 2;
    private final static int SWIPE_BOTTOM_TOP = 3;
    private final static int SWIPE_DIAGONAL = 4;
    private final static int SWIPE_THRESHOLD = 50;

    SignalRenderer signalRenderer;

    public GestureListener(SignalRenderer signalRenderer) {
        this.signalRenderer = signalRenderer;
    }

    public static int getSwipeType(float x1, float y1, float x2, float y2){
        int swipeType = SWIPE_DIAGONAL;
        float diffX = x2 - x1;
        float diffY = y1 - y2; // Android Screen Coordinates (0 to +Y) from top left corner

        if(Math.abs(Math.abs(diffX) - Math.abs(diffY)) > SWIPE_THRESHOLD) {

            if (Math.abs(diffY) > Math.abs(diffX)) {
                //Vertical Swipe
                if (diffY > 0) {
                    swipeType = SWIPE_BOTTOM_TOP;
                } else {
                    swipeType = SWIPE_TOP_BOTTOM;
                }
            } else {
                // Horizontal Swipe
                if (diffX > 0) {
                    swipeType = SWIPE_LEFT_RIGHT;

                } else {
                    swipeType = SWIPE_RIGHT_LEFT;
                }
            }
        }
        return swipeType;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                signalRenderer.handleModeChange();
            }
        }).start();
        return super.onDoubleTap(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        final int swipeType = getSwipeType(e1.getX(), e1.getY(), e2.getX(), e2.getY());

        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (swipeType){
                    case SWIPE_BOTTOM_TOP : signalRenderer.handleAmplitudeChange(true);break;
                    case SWIPE_TOP_BOTTOM : signalRenderer.handleAmplitudeChange(false);break;
                    case SWIPE_LEFT_RIGHT : signalRenderer.handleFrequencyChange(false);break;
                    case SWIPE_RIGHT_LEFT : signalRenderer.handleFrequencyChange(true);break;
                };
            }
        }).start();
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                signalRenderer.handleColorChange();
            }
        }).start();
        super.onLongPress(e);
    }
}
