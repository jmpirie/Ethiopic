package com.hundaol.ethiopic.cal

/**
 * Created by jmpirie on 2017-04-14.
 */
interface ICal {

    // initialization

    fun today(): Int

    fun fromDate(year: Int, month: Int, day: Int): Int

    // date

    fun getYear(jdn: Int): Int

    fun getMonth(jdn: Int): Int

    fun getDay(jdn: Int): Int

    // day

    fun nextDay(jdn: Int): Int

    fun nextDay(jdn: Int, days: Int): Int

    fun prevDay(jdn: Int): Int

    fun prevDay(jdn: Int, days: Int): Int

    // week

    fun getDayOfWeek(jdn: Int): Int

    fun getFirstDayOfWeek(jdn: Int): Int

    fun getLastDayOfWeek(jdn: Int): Int

    fun isWeekend(jdn: Int): Boolean

    fun isWeekday(jdn: Int): Boolean

    // month

    fun firstOfMonth(jdn: Int): Int

    fun lastOfMonth(jdn: Int): Int

    fun nextMonth(jdn: Int): Int

    fun prevMonth(jdn: Int): Int

    fun getMonthName(jdn: Int): CharArray

    fun getDaysInMonth(jdn: Int): Int

    fun getWeeksInMonth(jdn: Int): Int

    fun getWeekOfMonth(jdn: Int): Int

    fun getDayOfWeekOfFirstDayInMonth(jdn: Int): Int

    fun getDayOfWeekOfLastDayInMonth(jdn: Int): Int

    fun getLastFullWeek(jdn: Int): Int

    // year

    val monthsInYear: Int

    // week number

    fun getWeekNumber(jdn: Int): Int
}