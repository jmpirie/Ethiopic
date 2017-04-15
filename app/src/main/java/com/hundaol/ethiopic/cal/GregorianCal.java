package com.hundaol.ethiopic.cal;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jmpirie on 2017-04-14.
 */
public class GregorianCal extends AbstractCal {

    public static final GregorianCal INSTANCE = new GregorianCal();

    public static final char[][] MONTH_NAMES = {
            "JANUARY".toCharArray(),
            "FEBRUARY".toCharArray(),
            "MARCH".toCharArray(),
            "APRIL".toCharArray(),
            "MAY".toCharArray(),
            "JUNE".toCharArray(),
            "JULY".toCharArray(),
            "AUGUST".toCharArray(),
            "SEPTEMBER".toCharArray(),
            "OCTOBER".toCharArray(),
            "NOVEMBER".toCharArray(),
            "DECEMBER".toCharArray()
    };

    private GregorianCal() {
    }

    @Override
    public int today() {
        Calendar cal = new GregorianCalendar();
        return fromDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public int fromDate(int year, int month, int day) {
        return (1461 * (year + 4800 + (month - 14) / 12)) / 4 +
                (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12 -
                (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 +
                day - 32075;
    }

    @Override
    public int getYear(int jdn) {
        int l = jdn + 68569;
        int n = (4 * l) / 146097;
        l = l - (146097 * n + 3) / 4;
        int i = (4000 * (l + 1)) / 1461001;
        l = l - (1461 * i) / 4 + 31;
        int j = (80 * l) / 2447;
        int day = l - (2447 * j) / 80;
        l = j / 11;
        int month = j + 2 - (12 * l);
        int year = 100 * (n - 49) + i + l;

        return year;
    }

    @Override
    public int getMonth(int jdn) {
        int l = jdn + 68569;
        int n = (4 * l) / 146097;
        l = l - (146097 * n + 3) / 4;
        int i = (4000 * (l + 1)) / 1461001;
        l = l - (1461 * i) / 4 + 31;
        int j = (80 * l) / 2447;
        int day = l - (2447 * j) / 80;
        l = j / 11;
        int month = j + 2 - (12 * l);

        return month;
    }

    @Override
    public int getDay(int jdn) {
        int l = jdn + 68569;
        int n = (4 * l) / 146097;
        l = l - (146097 * n + 3) / 4;
        int i = (4000 * (l + 1)) / 1461001;
        l = l - (1461 * i) / 4 + 31;
        int j = (80 * l) / 2447;
        int day = l - (2447 * j) / 80;

        return day;
    }

    @Override
    public int getDaysInMonth(int jdn) {
        int month = getMonth(jdn);
        if (month == 2) {
            int year = getYear(jdn);
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                return 29;
            } else {
                return 28;
            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else {
            return 30;
        }
    }

    @Override
    public int getMonthsInYear() {
        return 12;
    }

    @Override
    public char[] getMonthName(int jdn) {
        return MONTH_NAMES[getMonth(jdn) - 1];
    }
}