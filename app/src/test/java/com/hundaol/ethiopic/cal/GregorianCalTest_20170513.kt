package com.hundaol.ethiopic.cal

import org.junit.Test

import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.Assert.assertThat

/**
 * Created by john.pirie on 2017-04-22.
 */
class GregorianCalTest_20170513 {

    private val cal = GregorianCal.INSTANCE
    private val jdn = 2457887 // G: 2017, 5, 13 E: 2009, 9, 5

    @Test
    fun fromDate() {
        assertThat(cal.fromDate(2017, 5, 13), Is(jdn))
    }

    // date

    @Test
    fun getYear() {
        assertThat(cal.getYear(jdn), Is(2017))
    }

    @Test
    fun getMonth() {
        assertThat(cal.getMonth(jdn), Is(5))
    }

    @Test
    fun getDay() {
        assertThat(cal.getDay(jdn), Is(13))
    }

    // day

    @Test
    fun nextDay() {
        assertThat(cal.nextDay(jdn, 20), Is(cal.fromDate(2017, 6, 2)))
    }

    @Test
    fun prevDay() {
        assertThat(cal.prevDay(jdn, 20), Is(cal.fromDate(2017, 4, 23)))
    }

    // week

    @Test
    fun getDayOfWeek() {
        assertThat(cal.getDayOfWeek(jdn), Is(6))
    }

    @Test
    fun getFirstDayOfWeek() {
        assertThat(cal.getFirstDayOfWeek(jdn), Is(cal.fromDate(2017, 5, 7)))
    }

    @Test
    fun getLastDayOfWeek() {
        assertThat(cal.getLastDayOfWeek(jdn), Is(cal.fromDate(2017, 5, 13)))
    }

    @Test
    fun isWeekday() {
        assertThat(cal.isWeekday(jdn), Is(false))
    }

    @Test
    fun isWeekend() {
        assertThat(cal.isWeekend(jdn), Is(true))
    }

    // month

    @Test
    fun firstOfMonth() {
        assertThat(cal.firstOfMonth(jdn), Is(cal.fromDate(2017, 5, 1)))
    }

    @Test
    fun lastOfMonth() {
        assertThat(cal.lastOfMonth(jdn), Is(cal.fromDate(2017, 5, 31)))
    }

    @Test
    fun nextMonth() {
        assertThat(cal.nextMonth(jdn), Is(cal.fromDate(2017, 6, 1)))
    }

    @Test
    fun prevMonth() {
        assertThat(cal.prevMonth(jdn), Is(cal.fromDate(2017, 4, 1)))
    }

    @Test
    fun getMonthName() {
        assertThat(cal.getMonthName(jdn)[0], Is('M'))
        assertThat(cal.getMonthName(jdn)[1], Is('a'))
        assertThat(cal.getMonthName(jdn)[2], Is('y'))
    }

    @Test
    fun getDaysInMonth() {
        assertThat(cal.getDaysInMonth(jdn), Is(31))
    }

    @Test
    fun getWeeksInMonth() {
        assertThat(cal.getWeeksInMonth(jdn), Is(5))
    }

    @Test
    fun getWeekOfMonth() {
        assertThat(cal.getWeekOfMonth(jdn), Is(1))
    }

    @Test
    fun getDayOfWeekOfFirstDayInMonth() {
        assertThat(cal.getDayOfWeekOfFirstDayInMonth(jdn), Is(1))
    }

    @Test
    fun getLastWeekDayOfMonth() {
        assertThat(cal.getDayOfWeekOfLastDayInMonth(jdn), Is(3))
    }

    @Test
    fun getLastFullWeek() {
        assertThat(cal.getLastFullWeek(jdn), Is(3))
    }


    // year

    @Test
    fun getLastFullWgeteek() {
        assertThat(cal.monthsInYear, Is(12))
    }

    // week number

    @Test
    fun getWeekNumber() {
        assertThat(cal.getWeekNumber(jdn), Is(351126))
    }
}
