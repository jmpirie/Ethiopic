package com.hundaol.ethiopic.views;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.hundaol.ethiopic.domain.DateModel;

/**
 * Created by john.pirie on 2017-04-29.
 */

public class CalendarViewGestureDetector {

    private final GestureDetector gestureDetector;

    public CalendarViewGestureDetector(CalendarView view, DateModel dateModel) {
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
                jdv = jdv + 7.0f * distanceY / cellWidth;
                dateModel.setJdv(jdv);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Handler handler = new Handler();
                Scroller scroller = new Scroller(view.getContext());
                scroller.setFriction(4 * 0.015f);
                scroller.fling(0, 0, 0, (int) velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                final float anchorJdv = view.getViewModel().getJdv();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (scroller.computeScrollOffset()) {
                            dateModel.setJdv(anchorJdv - scroller.getCurrY() * 7 / view.getViewModel().getCellWidth());
                            handler.postDelayed(this, 100);
                        }
                    }
                }, 100);
                return true;
            }
        });

        view.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }
}
