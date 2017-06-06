package com.hundaol.ethiopic.domain

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import com.hundaol.ethiopic.cal.ICal

/**
 * Created by john.pirie on 2017-06-06.
 */
data class ColorModel(@ColorInt val baseColor: Int = 0) {

    val hsl = floatArrayOf(0.0f, 0.0f, 0.0f)
    val result = floatArrayOf(0.0f, 0.0f, 0.0f)

    init {
        ColorUtils.RGBToHSL(Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor), hsl);
    }

    @ColorInt fun backgroundColorForMonth(cal: ICal, jdn: Int): Int {
        hsl[2] = (cal.getMonth(jdn) - 1).toFloat() / cal.monthsInYear.toFloat()
        return ColorUtils.HSLToColor(hsl)
    }

    companion object {
        val default = ColorModel()
    }
}