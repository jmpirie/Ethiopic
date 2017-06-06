package com.hundaol.ethiopic.cal

import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat

/**
 * Created by john.pirie on 2017-04-22.
 */
class GregorianCalTest {

    private val cal = GregorianCal.INSTANCE

    @Test
    fun getDay_whenSunday() {
        assertThat(cal.getDay(cal.fromDate(2017, 4, 23)), `is`(23))
    }

    @Test
    fun getDay_whenSaturday() {
        assertThat(cal.getDay(cal.fromDate(2017, 4, 22)), `is`(22))
    }

    @Test
    fun getDayOfWeek_whenSunday() {
        assertThat(cal.getDayOfWeek(cal.fromDate(2017, 4, 23)), `is`(0))
    }

    @Test
    fun getDayOfWeek_whenSaturday() {
        assertThat(cal.getDayOfWeek(cal.fromDate(2017, 4, 22)), `is`(6))
    }


}
