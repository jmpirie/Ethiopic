package com.hundaol.ethiopic.cal;

import java.util.Timer;

import timber.log.Timber;

/**
 * Created by jmpirie on 2017-04-14.
 */
public class EthiopicCal extends AbstractCal {

    public static final EthiopicCal INSTANCE = new EthiopicCal();

    public static final char[][] MONTH_NAMES = {
            "Mäskäräm".toCharArray(),
            "Ṭəqəmt".toCharArray(),
            "Ḫədar".toCharArray(),
            "Taḫśaś".toCharArray(),
            "Ṭərr".toCharArray(),
            "Yäkatit".toCharArray(),
            "Mägabit".toCharArray(),
            "Miyazya".toCharArray(),
            "Gənbot".toCharArray(),
            "Säne".toCharArray(),
            "Ḥamle".toCharArray(),
            "Nähase".toCharArray(),
            "Ṗagume".toCharArray()
    };

    private EthiopicCal() {
    }

    @Override
    public int today() {
        return GregorianCal.INSTANCE.today();
    }

    @Override
    public int fromDate(int year, int month, int day) {
        return  (1723856 + 365)
                + (365 * (year - 1))
                + (year / 4)
                + (30 * month)
                + (day - 31);
    }


    @Override
    public int getYear(int jdn) {
        int r = (jdn - 1723856) % 1461;
        int n = (r % 365) + 365 * (r / 1460);
        int year = 4 * ((jdn - 1723856) / 1461)
                + (r / 365)
                - (r / 1460);
        int month = (n / 30) + 1;
        int day = (n % 30) + 1;

        return year;
    }

    @Override
    public int getMonth(int jdn) {
        int r = (jdn - 1723856) % 1461;
        int n = (r % 365) + 365 * (r / 1460);
        int year = 4 * ((jdn - 1723856) / 1461)
                + (r / 365)
                - (r / 1460);
        int month = (n / 30) + 1;
        return month;
    }

    @Override
    public int getDay(int jdn) {
        int r = (jdn - 1723856) % 1461;
        int n = (r % 365) + 365 * (r / 1460);
        int year = 4 * ((jdn - 1723856) / 1461)
                + (r / 365)
                - (r / 1460);
        int month = (n / 30) + 1;
        int day = (n % 30) + 1;

        return day;
    }

    @Override
    public int getDaysInMonth(int jdn) {
        int month = getMonth(jdn);
        if (month == 13) {
            int year = getYear(jdn);
            if (year % 4 == 3) {
                return  6;
            } else {
                return 5;
            }
        } else {
            return 30;
        }
    }

    @Override
    public int getMonthsInYear() {
        return 13;
    }

    @Override
    public char[] getMonthName(int jdn) {
        return MONTH_NAMES[(getMonth(jdn) - 1 + 13) % 13];
    }
}