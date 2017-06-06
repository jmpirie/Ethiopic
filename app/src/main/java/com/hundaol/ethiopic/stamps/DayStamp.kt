package com.hundaol.ethiopic.stamps

import android.graphics.Canvas
import android.graphics.RectF
import com.hundaol.ethiopic.cal.GregorianCal
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.domain.ColorModel
import com.hundaol.ethiopic.views.CalendarViewModel

/**
 * Created by john.pirie on 2017-06-06.
 */

class DayStamp(var jdn: Int = 0,
               var cal: ICal = GregorianCal.INSTANCE) {

    var viewModel = CalendarViewModel()
    var dateModel: DateModel = DateModel.default
    var colorModel: ColorModel = ColorModel.default

    val bounds: RectF
        get() = viewModel.boundsForDay(dateModel, cal, jdn)

    fun stamp(canvas: Canvas) {

    }
}
