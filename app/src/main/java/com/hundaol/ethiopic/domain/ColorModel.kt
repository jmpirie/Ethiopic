package com.hundaol.ethiopic.domain

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import com.hundaol.ethiopic.cal.ICal

/**
 * Created by john.pirie on 2017-06-06.
 */
data class ColorModel(@ColorInt val baseRgb: Int = 0) {

    private val baseHsl = floatArrayOf(0.0f, 0.0f, 0.0f)
    val result = floatArrayOf(0.0f, 0.0f, 0.0f)

    init {
        ColorUtils.RGBToHSL(Color.red(baseRgb), Color.green(baseRgb), Color.blue(baseRgb), baseHsl)
    }

    private @ColorInt fun colorFor(argb : Int, result: FloatArray = this.result): Int {
        return colorFor(Color.alpha(argb), Color.red(argb), Color.green(argb), Color.blue(argb), result)
    }

    private @ColorInt fun colorFor(a : Int, rgb : Int, result: FloatArray = this.result): Int {
        return colorFor(a, Color.red(rgb), Color.green(rgb), Color.blue(rgb), result)
    }

    private @ColorInt fun colorFor(r: Int, g: Int, b: Int, result: FloatArray = this.result): Int {
        return colorFor(255, r, g, b, result)
    }

    private @ColorInt fun colorFor(a: Int, r: Int, g: Int, b: Int, result: FloatArray = this.result): Int {
        ColorUtils.RGBToHSL(r, g, b, result)
        return Color.argb(a, r, g, b)
    }

    private @ColorInt fun withLightness(l : Float, result: FloatArray = this.result): Int {
        result[0] = baseHsl[0]
        result[1] = baseHsl[1]
        result[2] = l
        return ColorUtils.HSLToColor(result)
    }

    @ColorInt fun backgroundColorForMonth(cal: ICal, jdn: Int, result: FloatArray = this.result): Int {
        val v = (cal.getMonth(jdn) -1) / cal.monthsInYear.toFloat()
        return withLightness(0.55f + 0.30f * (1.0f - v), result)
    }

    @ColorInt fun backgroundColorForDay(cal: ICal, jdn: Int, result: FloatArray = this.result): Int {
        if (cal.isWeekday(jdn)) {
            return colorFor(8, baseRgb, result)
        } else {
            return Color.TRANSPARENT
        }
    }

    @ColorInt fun dateImageOverlay(cal: ICal, jdn: Int, result: FloatArray = this.result): Int {
        return colorFor(160, 0, 0, 0, result)
    }

    @ColorInt fun foregroundColorForDay(cal: ICal, jdn: Int, result: FloatArray = this.result): Int {
        backgroundColorForMonth(cal, jdn, result)
        if (result[2] > 0.5f) {
            return withLightness(0.10f, result)
        } else {
            return withLightness(0.90f, result)
        }
    }

    @ColorInt fun foregroundColorForLabel(cal: ICal, jdn: Int, result: FloatArray = this.result): Int {
        backgroundColorForMonth(cal, jdn, result)
        if (result[2] > 0.5f) {
            return withLightness(0.10f, result)
        } else {
            return withLightness(0.90f, result)
        }
    }

    @ColorInt fun gridColor(cal: ICal, jdn: Int, result: FloatArray = this.result): Int {
        return withLightness(0.50f, result)
    }

    companion object {
        val default = ColorModel()
    }
}