package com.hundaol.ethiopic

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.views.CalendarView

/**
 * Created by john.pirie on 2017-06-06.
 */

class MainPagerAdapter(val context : Context, val display : Display) : PagerAdapter() {

    override fun getPageWidth(position: Int): Float {
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        return if (height >= width) 1.0f else 0.5f
    }

    override fun getCount(): Int {
        return 2
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (position == 0 || position == 1) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_calendar, null, false) as CalendarView
            //CalendarViewGestureDetector(view, dateModel)
            //view.viewModel =
            container.addView(view)
            return view
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
