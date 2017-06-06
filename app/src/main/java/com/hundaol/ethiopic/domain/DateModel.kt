package com.hundaol.ethiopic.domain

import com.hundaol.ethiopic.cal.GregorianCal

/**
 * Created by john.pirie on 2017-06-06.
 */

data class DateModel(val jdv: Float = GregorianCal.INSTANCE.today().toFloat() ) {

    val jdn: Int
        get() = jdv.toInt()

    companion object {
        val default = DateModel()
    }
}
