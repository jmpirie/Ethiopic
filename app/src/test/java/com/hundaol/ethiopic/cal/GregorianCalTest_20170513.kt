package com.hundaol.ethiopic.cal

import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat

/**
 * Created by john.pirie on 2017-04-22.
 */
class GregorianCalTest_20170513 {

    private val cal = GregorianCal.INSTANCE
    private val jdn = GregorianCal.INSTANCE.fromDate(2017, 5, 13)

    @Test
    fun fromDate() {
        assertThat(jdn, `is`(2457887))
    }

    // date

    @Test
    fun getYear() {
        assertThat(cal.getYear(jdn), `is`(2017))
    }

    @Test
    fun getMonth() {
        assertThat(cal.getMonth(jdn), `is`(5))
    }

    @Test
    fun getDay() {
        assertThat(cal.getDay(jdn), `is`(13))
    }

    // day

    @Test
    fun nextDay() {
        assertThat(cal.nextDay(jdn, 20), `is`(cal.fromDate(2017, 6, 2)))
    }

    @Test
    fun prevDay() {
        assertThat(cal.prevDay(jdn, 20), `is`(cal.fromDate(2017, 4, 23)))
    }

    // week

    @Test
    fun getDayOfWeek() {
        assertThat(cal.getDayOfWeek(jdn), `is`(6))
    }

    @Test
    fun getFirstDayOfWeek() {
        assertThat(cal.getFirstDayOfWeek(jdn), `is`(cal.fromDate(2017, 5, 7)))
    }

    @Test
    fun getLastDayOfWeek() {
        assertThat(cal.getLastDayOfWeek(jdn), `is`(cal.fromDate(2017, 5, 13)))
    }

    @Test
    fun isWeekday() {
        assertThat(cal.isWeekday(jdn), `is`(false))
    }

    @Test
    fun isWeekend() {
        assertThat(cal.isWeekend(jdn), `is`(true))
    }

    // month

    @Test
    fun firstOfMonth() {
        assertThat(cal.firstOfMonth(jdn), `is`(cal.fromDate(2017, 5, 1)))
    }

    @Test
    fun lastOfMonth() {
        assertThat(cal.lastOfMonth(jdn), `is`(cal.fromDate(2017, 5, 31)))
    }

    @Test
    fun nextMonth() {
        assertThat(cal.nextMonth(jdn), `is`(cal.fromDate(2017, 6, 1)))
    }

    @Test
    fun prevMonth() {
        assertThat(cal.prevMonth(jdn), `is`(cal.fromDate(2017, 4, 1)))
    }

    @Test
    fun getMonthName() {
        assertThat(cal.getMonthName(jdn)[0], `is`('M'))
        assertThat(cal.getMonthName(jdn)[1], `is`('A'))
        assertThat(cal.getMonthName(jdn)[2], `is`('Y'))
    }

    @Test
    fun getDaysInMonth() {
        assertThat(cal.getDaysInMonth(jdn), `is`(31))
    }

    @Test
    fun getWeeksInMonth() {
        assertThat(cal.getWeeksInMonth(jdn), `is`(5))
    }

    @Test
    fun getWeekOfMonth() {
        assertThat(cal.getWeekOfMonth(jdn), `is`(1))
    }

    @Test
    fun getDayOfWeekOfFirstDayInMonth() {
        assertThat(cal.getDayOfWeekOfFirstDayInMonth(jdn), `is`(1))
    }

    @Test
    fun getLastWeekDayOfMonth() {
        assertThat(cal.getDayOfWeekOfLastDayInMonth(jdn), `is`(3))
    }

    @Test
    fun getLastFullWeek() {
        assertThat(cal.getLastFullWeek(jdn), `is`(3))
    }


    // year

    @Test
    fun getLastFullWgeteek() {
        assertThat(cal.monthsInYear, `is`(12))
    }

    // week number

    @Test
    fun getWeekNumber() {
        assertThat(cal.getWeekNumber(jdn), `is`(351126))
    }
}
