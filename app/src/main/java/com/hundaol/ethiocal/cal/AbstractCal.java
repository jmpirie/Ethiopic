package com.hundaol.ethiocal.cal;

/**
 * Created by jmpirie on 2017-04-14.
 */
public abstract class AbstractCal implements ICal {

    @Override
    public int nextDay(int jdn) {
        return jdn + 1;
    }

    @Override
    public int nextDay(int jdn, int days) {
        return jdn + days;
    }

    @Override
    public int prevDay(int jdn) {
        return jdn - 1;
    }

    @Override
    public int prevDay(int jdn, int days) {
        return jdn - days;
    }

    @Override
    public int nextMonth(int jdn) {
        int year = getYear(jdn);
        int month = getMonth(jdn);
        int monthsInYear = getMonthsInYear();
        if (month == monthsInYear) {
            return fromDate(year + 1, 1, 1);
        } else {
            return fromDate(year, month + 1, 1);
        }
    }

    @Override
    public int prevMonth(int jdn) {
        int year = getYear(jdn);
        int month = getMonth(jdn);
        if (month < 0) {
            prevMonth(jdn);
        }
        int monthsInYear = getMonthsInYear();
        if (month == 1) {
            return fromDate(year - 1, monthsInYear, 1);
        } else {
            return fromDate(year, month - 1, 1);
        }
    }

    @Override
    public int firstOfMonth(int jdn) {
        return jdn - getDay(jdn) + 1;
    }

    @Override
    public int firstOfWeek(int jdn) {
        return jdn - getDayOfWeek(jdn);
    }

    public int getWeekNumber(int jdn) {
        return (jdn + 1) / 7;
    }

    @Override
    public int getFirstWeekDayOfMonth(int jdn) {
        return (36 + getDayOfWeek(jdn) - getDay(jdn)) % 7;
    }

    @Override
    public int getLastFullWeek(int jdn) {
        return (getDaysInMonth(jdn) + getFirstWeekDayOfMonth(jdn)) / 7 - 1;
    }

    @Override
    public int getWeeksInMonth(int jdn) {
        return (getDaysInMonth(jdn) + getFirstWeekDayOfMonth(jdn)) / 7 + 1;
    }

    @Override
    public int getLastWeekDayOfMonth(int jdn) {
        return (getFirstWeekDayOfMonth(jdn) + getDaysInMonth(jdn) - 1) % 7;
    }

    @Override
    public int getWeekOfMonth(int jdn) {
        return (getDay(jdn) + getFirstWeekDayOfMonth(jdn) - 1) / 7;
    }

    @Override
    public int getDayOfWeek(int jdn) {
        return (((jdn - 2456775) % 7) + 7) % 7;
    }
}