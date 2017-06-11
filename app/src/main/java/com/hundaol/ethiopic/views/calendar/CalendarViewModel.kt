package com.hundaol.ethiopic.views.calendar

import android.graphics.Path
import android.graphics.RectF
import com.hundaol.ethiopic.cal.GregorianCal
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.domain.DateModel

/**
 * Created by john.pirie on 2017-06-06.
 */

data class CalendarViewModel(val cellWidth: Float = 100.0f,
                             val cellHeight: Float = 100.0f,
                             val offset: Float = 0.0f) {

    private val bounds = RectF()
    private val path = Path()

    fun boundsForDay(dateModel: DateModel, cal: ICal, jdn: Int): RectF {
        bounds.left = cellWidth * cal.getDayOfWeek(jdn)
        bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - dateModel.jdv * cellWidth / 7.0f
        bounds.right = bounds.left + cellWidth
        bounds.bottom = bounds.top + cellWidth
        return bounds
    }

    fun boundsForLabel(dateModel: DateModel, cal: ICal, jdn: Int): RectF {
        val jdn = cal.firstOfMonth(jdn)
        bounds.left = cellWidth * 7
        bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - dateModel.jdv * cellWidth / 7.0f
        bounds.right = bounds.left + cellWidth
        bounds.bottom = bounds.top + cellWidth * (cal.getLastFullWeek(jdn) + 1)
        return bounds
    }

    fun boundsForMonth(dateModel: DateModel, cal: ICal, jdn: Int): RectF {
        var jdn = cal.firstOfMonth(jdn)
        bounds.left = 0f
        bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - dateModel.jdv * cellWidth / 7.0f
        bounds.right = 8 * cellWidth
        bounds.bottom = bounds.top + cellWidth * cal.getWeeksInMonth(jdn)
        return bounds
    }

    fun pathForMonth(dateModel: DateModel, cal: ICal, jdn: Int): Path {
        val d0 = cal.firstOfMonth(jdn)
        val d1 = cal.getLastDayOfWeek(jdn)
        val d2 = cal.getFirstDayOfWeek(d0 + 7)

        val d5 = cal.lastOfMonth(jdn)
        val d4 = cal.getFirstDayOfWeek(d5)
        val d3 = cal.getLastDayOfWeek(d5 - 7)

        path.reset()
        boundsForDay(dateModel, cal, d0)
        path.moveTo(bounds.left, bounds.bottom)
        path.lineTo(bounds.left, bounds.top)
        boundsForDay(dateModel, cal, d1)
        path.lineTo(bounds.right, bounds.top)
        boundsForDay(dateModel, cal, d3)
        path.lineTo(bounds.right, bounds.bottom)
        boundsForDay(dateModel, cal, d5)
        path.lineTo(bounds.right, bounds.top)
        path.lineTo(bounds.right, bounds.bottom)
        boundsForDay(dateModel, cal, d4)
        path.lineTo(bounds.left, bounds.bottom)
        boundsForDay(dateModel, cal, d2)
        path.lineTo(bounds.left, bounds.top)
        path.close()

        return path
    }

    fun getTextSizeForDay(dateModel: DateModel, cal: ICal, jdn : Int) : Float {
        return cellWidth / 4.0f
    }

    fun getTextSizeForLabel(dateModel: DateModel, cal: ICal, jdn : Int) : Float {
        return cellWidth / 4.0f
    }
}
