package com.hundaol.ethiopic.cal;

/**
 * Created by jmpirie on 2017-04-14.
 */
public interface ICal {

    int today();

    int fromDate(int year, int month, int day);

    int firstOfMonth(int jdn);

    int firstOfWeek(int jdn);

    public int nextDay(int jdn);

    public int nextDay(int jdn, int days);

    public int prevDay(int jdn);

    public int prevDay(int jdn, int days);

    public int nextMonth(int jdn);

    int prevMonth(int jdn);

    int getWeekNumber(int jdn);

    public int getYear(int jdn);

    public int getMonth(int jdn);

    public int getDay(int jdn);

    char[] getMonthName(int jdn);

    public int getFirstWeekDayOfMonth(int jdn);

    public int getLastFullWeek(int jdn);

    public int getWeeksInMonth(int jdn);

    public int getLastWeekDayOfMonth(int jdn);

    public int getWeekOfMonth(int jdn);

    public int getDayOfWeek(int jdn);

    public int getDaysInMonth(int jdn);

    public int getFirstDayOfWeek(int jdn);

    public int getLastDayOfWeek(int jdn);

    public int getMonthsInYear();
}