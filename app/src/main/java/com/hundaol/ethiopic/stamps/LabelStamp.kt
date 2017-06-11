package com.hundaol.ethiopic.stamps

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import com.hundaol.ethiopic.cal.GregorianCal
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.domain.ColorModel
import com.hundaol.ethiopic.views.calendar.CalendarViewModel

/**
 * Created by john.pirie on 2017-06-06.
 */

class LabelStamp(var jdn: Int = 0,
                 var cal: ICal = GregorianCal.INSTANCE) {

    var viewModel = CalendarViewModel()
    var dateModel: DateModel = DateModel.default

    var colorModel: ColorModel = ColorModel.default

    val bounds: RectF
        get() = viewModel.boundsForLabel(dateModel, cal, jdn)

    val backPaint = Paint()
    val textPaint = TextPaint()
    val gridPaint = Paint()

    init {
        backPaint.style = Paint.Style.FILL_AND_STROKE

        textPaint.style = Paint.Style.STROKE

        gridPaint.style = Paint.Style.STROKE
        gridPaint.strokeWidth = 4.0f
    }

    fun stamp(canvas: Canvas) {
        val bounds = this.bounds

        backPaint.color = colorModel.backgroundColorForMonth(cal, jdn)
        canvas.drawRect(viewModel.boundsForLabel(dateModel, cal, jdn), backPaint)

        textPaint.color = colorModel.foregroundColorForLabel(cal, jdn)
        textPaint.textSize = viewModel.getTextSizeForLabel(dateModel, cal, jdn)

        val s = String(cal.getMonthName(jdn)) + ", " + cal.getYear(jdn)
        val tw = textPaint.measureText(s)
        val th = textPaint.getTextSize()
        canvas.rotate(90.0f, bounds.centerX(), bounds.centerY())
        canvas.drawText(s, bounds.left + (bounds.width() - tw) / 2.0f, bounds.top + (bounds.height() + th) / 2.0f, textPaint)
        canvas.rotate(-90.0f, bounds.centerX(), bounds.centerY())

        gridPaint.color = colorModel.gridColor(cal, jdn)
        canvas.drawRect(bounds, gridPaint)
    }
}
