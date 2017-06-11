package com.hundaol.ethiopic

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.cal.EthiopicCal
import com.hundaol.ethiopic.cal.GregorianCal
import com.hundaol.ethiopic.views.calendar.CalendarView
import com.hundaol.ethiopic.views.calendar.CalendarViewGestureDetector
import com.hundaol.ethiopic.views.date.DateView

/**
 * Created by john.pirie on 2017-06-06.
 */

class MainPagerAdapter(val context: Context, val display: Display) : PagerAdapter() {

    override fun getPageWidth(position: Int): Float {
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        return if (height >= width) 1.0f else 0.5f
    }

    override fun getCount(): Int {
        return 4
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (position == 0) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_date, null, false) as DateView
            view.cal = GregorianCal.INSTANCE
            container.addView(view)
            return view
        } else if (position == 1) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_calendar, null, false) as CalendarView
            view.cal = GregorianCal.INSTANCE
            CalendarViewGestureDetector(view)
            container.addView(view)
            return view
        } else if (position == 2) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_calendar, null, false) as CalendarView
            view.cal = EthiopicCal.INSTANCE
            CalendarViewGestureDetector(view)
            container.addView(view)
            return view
        } else if (position == 3) {
            val view = LayoutInflater.from(context).inflate(R.layout.layout_date, null, false) as DateView
            view.cal = EthiopicCal.INSTANCE
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
