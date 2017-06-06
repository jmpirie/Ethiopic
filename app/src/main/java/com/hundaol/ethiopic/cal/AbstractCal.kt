package com.hundaol.ethiopic.cal

/**
 * Created by jmpirie on 2017-04-14.
 */
abstract class AbstractCal : ICal {

    override fun nextDay(jdn: Int): Int {
        return jdn + 1
    }

    override fun nextDay(jdn: Int, days: Int): Int {
        return jdn + days
    }

    override fun prevDay(jdn: Int): Int {
        return jdn - 1
    }

    override fun prevDay(jdn: Int, days: Int): Int {
        return jdn - days
    }

    override fun nextMonth(jdn: Int): Int {
        val year = getYear(jdn)
        val month = getMonth(jdn)
        val monthsInYear = monthsInYear
        if (month == monthsInYear) {
            return fromDate(year + 1, 1, 1)
        } else {
            return fromDate(year, month + 1, 1)
        }
    }

    override fun prevMonth(jdn: Int): Int {
        val year = getYear(jdn)
        val month = getMonth(jdn)
        if (month < 0) {
            prevMonth(jdn)
        }
        val monthsInYear = monthsInYear
        if (month == 1) {
            return fromDate(year - 1, monthsInYear, 1)
        } else {
            return fromDate(year, month - 1, 1)
        }
    }

    override fun firstOfMonth(jdn: Int): Int {
        return jdn - getDay(jdn) + 1
    }

    override fun getFirstDayOfWeek(jdn: Int): Int {
        return jdn - getDayOfWeek(jdn)
    }

    override fun getLastDayOfWeek(jdn: Int): Int {
        return getFirstDayOfWeek(jdn) + 6
    }

    override fun getWeekNumber(jdn: Int): Int {
        return (jdn + 1) / 7
    }

    override fun getDayOfWeekOfFirstDayInMonth(jdn: Int): Int {
        return (36 + getDayOfWeek(jdn) - getDay(jdn)) % 7
    }

    override fun getLastFullWeek(jdn: Int): Int {
        return (getDaysInMonth(jdn) + getDayOfWeekOfFirstDayInMonth(jdn)) / 7 - 1
    }

    override fun getWeeksInMonth(jdn: Int): Int {
        return (getDaysInMonth(jdn) + getDayOfWeekOfFirstDayInMonth(jdn)) / 7 + 1
    }

    override fun getDayOfWeekOfLastDayInMonth(jdn: Int): Int {
        return (getDayOfWeekOfFirstDayInMonth(jdn) + getDaysInMonth(jdn) - 1) % 7
    }

    override fun getWeekOfMonth(jdn: Int): Int {
        return (getDay(jdn) + getDayOfWeekOfFirstDayInMonth(jdn) - 1) / 7
    }

    override fun getDayOfWeek(jdn: Int): Int {
        return ((jdn - 2456775) % 7 + 7) % 7
    }

    override fun isWeekend(jdn: Int): Boolean {
        val d = getDayOfWeek(jdn)
        return d == 0 || d == 6
    }

    override fun isWeekday(jdn: Int): Boolean {
        return !isWeekend(jdn)
    }

    override fun lastOfMonth(jdn: Int): Int {
        return firstOfMonth(jdn) + getDaysInMonth(jdn) - 1
    }
}
