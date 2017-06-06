package com.hundaol.ethiopic.cal

import org.junit.Test

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat

/**
 * Created by john.pirie on 2017-04-22.
 */
class EthiopicCalTest_20170513 {

    private val cal = EthiopicCal.INSTANCE
    private val jdn = GregorianCal.INSTANCE.fromDate(2017, 5, 13)

    @Test
    fun fromDate() {
        assertThat(jdn, `is`(2457887))
    }

    // date

    @Test
    fun getYear() {
        assertThat(cal.getYear(jdn), `is`(2009))
    }

    @Test
    fun getMonth() {
        assertThat(cal.getMonth(jdn), `is`(9))
    }

    @Test
    fun getDay() {
        assertThat(cal.getDay(jdn), `is`(5))
    }

    // day

    @Test
    fun nextDay() {
        assertThat(cal.nextDay(jdn, 20), `is`(cal.fromDate(2009, 9, 25)))
    }

    @Test
    fun prevDay() {
        assertThat(cal.prevDay(jdn, 20), `is`(cal.fromDate(2009, 8, 15)))
    }

    // week

    @Test
    fun getDayOfWeek() {
        assertThat(cal.getDayOfWeek(jdn), `is`(6))
    }

    @Test
    fun getFirstDayOfWeek() {
        assertThat(cal.getFirstDayOfWeek(jdn), `is`(cal.fromDate(2009, 8, 29)))
    }

    @Test
    fun getLastDayOfWeek() {
        assertThat(cal.getLastDayOfWeek(jdn), `is`(cal.fromDate(2009, 9, 5)))
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
        assertThat(cal.firstOfMonth(jdn), `is`(cal.fromDate(2009, 9, 1)))
    }

    @Test
    fun lastOfMonth() {
        assertThat(cal.lastOfMonth(jdn), `is`(cal.fromDate(2009, 9, 30)))
    }

    @Test
    fun nextMonth() {
        assertThat(cal.nextMonth(jdn), `is`(cal.fromDate(2009, 10, 1)))
    }

    @Test
    fun prevMonth() {
        assertThat(cal.prevMonth(jdn), `is`(cal.fromDate(2009, 8, 1)))
    }

    @Test
    fun getMonthName() {
        assertThat(cal.getMonthName(jdn)[0], `is`('G'))
        assertThat(cal.getMonthName(jdn)[1], `is`('ə'))
        assertThat(cal.getMonthName(jdn)[2], `is`('n'))
    }

    @Test
    fun getDaysInMonth() {
        assertThat(cal.getDaysInMonth(jdn), `is`(30))
    }

    @Test
    fun getWeeksInMonth() {
        assertThat(cal.getWeeksInMonth(jdn), `is`(5))
    }

    @Test
    fun getWeekOfMonth() {
        assertThat(cal.getWeekOfMonth(jdn), `is`(0))
    }

    @Test
    fun getDayOfWeekOfFirstDayInMonth() {
        assertThat(cal.getDayOfWeekOfFirstDayInMonth(jdn), `is`(2))
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
    fun getMonthsInYear() {
        assertThat(cal.monthsInYear, `is`(13))
    }

    // week number

    @Test
    fun getWeekNumber() {
        assertThat(cal.getWeekNumber(jdn), `is`(351126))
    }
}
