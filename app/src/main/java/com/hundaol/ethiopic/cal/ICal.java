package com.hundaol.ethiopic.cal;

/**
 * Created by jmpirie on 2017-04-14.
 */
public interface ICal {

    // initialization

    int today();

    int fromDate(int year, int month, int day);

    // date

    int getYear(int jdn);

    int getMonth(int jdn);

    int getDay(int jdn);
    
    // day

    int nextDay(int jdn);

    int nextDay(int jdn, int days);

    int prevDay(int jdn);

    int prevDay(int jdn, int days);

    // week

    int getDayOfWeek(int jdn);

    int getFirstDayOfWeek(int jdn);

    int getLastDayOfWeek(int jdn);

    boolean isWeekend(int jdn);

    boolean isWeekday(int jdn);

    // month

    int firstOfMonth(int jdn);

    int lastOfMonth(int jdn);

    int nextMonth(int jdn);

    int prevMonth(int jdn);

    char[] getMonthName(int jdn);

    int getDaysInMonth(int jdn);

    int getWeeksInMonth(int jdn);

    int getWeekOfMonth(int jdn);

    int getDayOfWeekOfFirstDayInMonth(int jdn);

    int getDayOfWeekOfLastDayInMonth(int jdn);

    int getLastFullWeek(int jdn);

    // year

    int getMonthsInYear();

    // week number

    int getWeekNumber(int jdn);
}