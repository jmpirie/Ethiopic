package com.hundaol.ethiopic.stamps

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.hundaol.ethiopic.cal.GregorianCal
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.domain.ColorModel
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.views.CalendarViewModel

/**
 * Created by john.pirie on 2017-06-06.
 */
class MonthStamp(var jdn : Int = 0,
                 var cal : ICal = GregorianCal.INSTANCE) {

    var viewModel = CalendarViewModel()
        get() = field
        set(value) {
            field = value
            dayStamp.viewModel = viewModel
        }

    var dateModel = DateModel.default
        get() = field
        set(value) {
            field = value
            dayStamp.dateModel = dateModel
        }

    var colorModel = ColorModel.default
        get() = field
        set(value) {
            field = value
            dayStamp.colorModel = colorModel
        }

    val bounds: RectF
        get() = viewModel.boundsForMonth(dateModel, cal, jdn)

    val backPaint = Paint()
    val dayStamp = DayStamp()

    init {
        backPaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun stamp(canvas: Canvas) {
        backPaint.color = colorModel.backgroundColorForMonth(cal, jdn)

        var j = cal.firstOfMonth(jdn)

        for (i in 0..cal.getDaysInMonth(j) - 1) {
            dayStamp.jdn = j + i
            dayStamp.stamp(canvas)
        }

//        labelStamp.setJdn(j)
//        labelStamp.stamp(canvas)

        val monthPath = viewModel.pathForMonth(dateModel, cal, j)
        canvas.drawPath(monthPath, backPaint)
    }
}