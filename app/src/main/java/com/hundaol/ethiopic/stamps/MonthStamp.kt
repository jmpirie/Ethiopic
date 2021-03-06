package com.hundaol.ethiopic.stamps

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.hundaol.ethiopic.cal.GregorianCal
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.domain.ColorModel
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.views.calendar.CalendarViewModel

/**
 * Created by john.pirie on 2017-06-06.
 */
class MonthStamp(var jdn: Int = 0) {

    var viewModel = CalendarViewModel()
        get() = field
        set(value) {
            field = value
            dayStamp.viewModel = viewModel
            labelStamp.viewModel = viewModel
        }

    var dateModel = DateModel.default
        get() = field
        set(value) {
            field = value
            dayStamp.dateModel = dateModel
            labelStamp.dateModel = dateModel
        }

    var colorModel = ColorModel.default
        get() = field
        set(value) {
            field = value
            dayStamp.colorModel = colorModel
            labelStamp.colorModel = colorModel
        }

    var cal: ICal = GregorianCal.INSTANCE
        get() = field
        set(value) {
            field = value
            dayStamp.cal = value
            labelStamp.cal = value
        }

    val bounds: RectF
        get() = viewModel.boundsForMonth(dateModel, cal, jdn)

    val backPaint = Paint()
    val dayStamp = DayStamp()
    val labelStamp = LabelStamp()

    init {
        backPaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun stamp(canvas: Canvas) {
        backPaint.color = colorModel.backgroundColorForMonth(cal, jdn)
        canvas.drawPath(viewModel.pathForMonth(dateModel, cal, jdn), backPaint)

        var j = cal.firstOfMonth(jdn)

        for (i in 0..cal.getDaysInMonth(j) - 1) {
            dayStamp.jdn = j + i
            dayStamp.stamp(canvas)
        }

        labelStamp.jdn = jdn
        labelStamp.stamp(canvas)

    }
}