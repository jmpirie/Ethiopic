package com.hundaol.ethiopic.cal

import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Created by jmpirie on 2017-04-14.
 */
class GregorianCal private constructor() : AbstractCal() {

    override fun today(): Int {
        val cal = GregorianCalendar()
        return fromDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
    }

    override fun fromDate(year: Int, month: Int, day: Int): Int {
        return 1461 * (year + 4800 + (month - 14) / 12) / 4 + 367 * (month - 2 - 12 * ((month - 14) / 12)) / 12 - 3 * ((year + 4900 + (month - 14) / 12) / 100) / 4 + day - 32075
    }

    override fun getYear(jdn: Int): Int {
        var l = jdn + 68569
        val n = 4 * l / 146097
        l = l - (146097 * n + 3) / 4
        val i = 4000 * (l + 1) / 1461001
        l = l - 1461 * i / 4 + 31
        val j = 80 * l / 2447
        val day = l - 2447 * j / 80
        l = j / 11
        val month = j + 2 - 12 * l
        val year = 100 * (n - 49) + i + l

        return year
    }

    override fun getMonth(jdn: Int): Int {
        var l = jdn + 68569
        val n = 4 * l / 146097
        l = l - (146097 * n + 3) / 4
        val i = 4000 * (l + 1) / 1461001
        l = l - 1461 * i / 4 + 31
        val j = 80 * l / 2447
        val day = l - 2447 * j / 80
        l = j / 11
        val month = j + 2 - 12 * l

        return month
    }

    override fun getDay(jdn: Int): Int {
        var l = jdn + 68569
        val n = 4 * l / 146097
        l = l - (146097 * n + 3) / 4
        val i = 4000 * (l + 1) / 1461001
        l = l - 1461 * i / 4 + 31
        val j = 80 * l / 2447
        val day = l - 2447 * j / 80

        return day
    }

    override fun getDaysInMonth(jdn: Int): Int {
        val month = getMonth(jdn)
        if (month == 2) {
            val year = getYear(jdn)
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                return 29
            } else {
                return 28
            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31
        } else {
            return 30
        }
    }

    override val monthsInYear: Int
        get() = 12

    override fun getMonthName(jdn: Int): CharArray {
        return MONTH_NAMES[(getMonth(jdn) - 1 + 12) % 12]
    }

    companion object {

        val INSTANCE = GregorianCal()

        val MONTH_NAMES = arrayOf<CharArray>("JANUARY".toCharArray(), "FEBRUARY".toCharArray(), "MARCH".toCharArray(), "APRIL".toCharArray(), "MAY".toCharArray(), "JUNE".toCharArray(), "JULY".toCharArray(), "AUGUST".toCharArray(), "SEPTEMBER".toCharArray(), "OCTOBER".toCharArray(), "NOVEMBER".toCharArray(), "DECEMBER".toCharArray())
    }
}