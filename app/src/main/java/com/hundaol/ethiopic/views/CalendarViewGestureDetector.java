package com.hundaol.ethiopic.views;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import timber.log.Timber;

/**
 * Created by john.pirie on 2017-04-29.
 */

public class CalendarViewGestureDetector {

    private final CalendarView view;
    private final GestureDetector gestureDetector;

    public CalendarViewGestureDetector(CalendarView view) {
        this.view = view;

        this.gestureDetector = new GestureDetector(view.getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float cellWidth = view.getViewModel().getCellWidth();
                float jdv = view.getViewModel().getJdv();
                view.getViewModel().setJdv(jdv + 7.0f * distanceY / cellWidth);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Math.abs(velocityY) > Math.abs(velocityX)) {
                    Handler handler = new Handler();
                    Scroller scroller = new Scroller(view.getContext());
                    scroller.setFriction(4 * 0.015f);
                    scroller.fling(0, 0, 0, (int) velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    final float anchorJdv = view.getViewModel().getJdv();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (scroller.computeScrollOffset()) {
                                view.getViewModel().setJdv(anchorJdv + scroller.getCurrY() * 7 / view.getViewModel().getCellWidth());
                                handler.postDelayed(this, 100);
                            }
                        }
                    }, 100);
                    return true;
                } else {
                    return false;
                }
            }
        });

        view.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }
}
