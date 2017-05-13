package com.hundaol.ethiopic.cal;

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
    public int getFirstDayOfWeek(int jdn){
        return jdn - getDayOfWeek(jdn);
    }

    @Override
    public int getLastDayOfWeek(int jdn){
        return getFirstDayOfWeek(jdn) + 6;
    }

    public int getWeekNumber(int jdn) {
        return (jdn + 1) / 7;
    }

    @Override
    public int getDayOfWeekOfFirstDayInMonth(int jdn) {
        return (36 + getDayOfWeek(jdn) - getDay(jdn)) % 7;
    }

    @Override
    public int getLastFullWeek(int jdn) {
        return (getDaysInMonth(jdn) + getDayOfWeekOfFirstDayInMonth(jdn)) / 7 - 1;
    }

    @Override
    public int getWeeksInMonth(int jdn) {
        return (getDaysInMonth(jdn) + getDayOfWeekOfFirstDayInMonth(jdn)) / 7 + 1;
    }

    @Override
    public int getDayOfWeekOfLastDayInMonth(int jdn) {
        return (getDayOfWeekOfFirstDayInMonth(jdn) + getDaysInMonth(jdn) - 1) % 7;
    }

    @Override
    public int getWeekOfMonth(int jdn) {
        return (getDay(jdn) + getDayOfWeekOfFirstDayInMonth(jdn) - 1) / 7;
    }

    @Override
    public int getDayOfWeek(int jdn) {
        return (((jdn - 2456775) % 7) + 7) % 7;
    }

    @Override
    public boolean isWeekend(int jdn) {
        int d = getDayOfWeek(jdn);
        return d == 0 || d == 6;
    }

    public boolean isWeekday(int jdn) {
        return !isWeekend(jdn);
    }

    public int lastOfMonth(int jdn) {
        return firstOfMonth(jdn) + getDaysInMonth(jdn) - 1;
    }
}
