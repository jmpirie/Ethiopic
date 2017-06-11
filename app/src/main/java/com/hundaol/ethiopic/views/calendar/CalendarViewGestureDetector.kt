package com.hundaol.ethiopic.views.calendar

import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Scroller
import com.hundaol.ethiopic.App
import com.hundaol.ethiopic.views.calendar.CalendarView

/**
 * Created by john.pirie on 2017-04-29.
 */

class CalendarViewGestureDetector(val view: CalendarView) {

    private val gestureDetector: GestureDetector

    init {
        this.gestureDetector = GestureDetector(view.context, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onShowPress(e: MotionEvent) {

            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return false
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                val cellWidth = view.viewModel.cellWidth
                var jdv = view.dateModel.jdv
                jdv = jdv + 7.0f * distanceY / cellWidth
                App.Companion.setJdv(jdv)
                return true
            }

            override fun onLongPress(e: MotionEvent) {}

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                val handler = Handler()
                val scroller = Scroller(view.context)
                scroller.setFriction(4 * 0.015f)
                scroller.fling(0, 0, 0, velocityY.toInt(), Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE)
                val anchorJdv = view.dateModel.jdv
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        if (scroller.computeScrollOffset()) {
                            App.Companion.setJdv(anchorJdv - scroller.currY * 7 / view.viewModel.cellWidth)
                            handler.postDelayed(this, 100)
                        }
                    }
                }, 100)
                return true
            }
        })

        view.setOnTouchListener { v, event -> gestureDetector.onTouchEvent(event) }
    }
}
