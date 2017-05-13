package com.hundaol.ethiopic.cal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by john.pirie on 2017-04-22.
 */
public class GregorianCalTest_20170513 {

    private ICal cal = GregorianCal.INSTANCE;
    private int jdn = GregorianCal.INSTANCE.fromDate(2017, 5, 13);

    @Test
    public void fromDate() {
        assertThat(jdn, is(2457887));
    }

    // date

    @Test
    public void getYear() {
        assertThat(cal.getYear(jdn), is(2017));
    }

    @Test
    public void getMonth() {
        assertThat(cal.getMonth(jdn), is(5));
    }

    @Test
    public void getDay() {
        assertThat(cal.getDay(jdn), is(13));
    }

    // day

    @Test
    public void nextDay() {
        assertThat(cal.nextDay(jdn, 20), is(cal.fromDate(2017, 6, 2)));
    }

    @Test
    public void prevDay() {
        assertThat(cal.prevDay(jdn, 20), is(cal.fromDate(2017, 4, 23)));
    }

    // week

    @Test
    public void getDayOfWeek() {
        assertThat(cal.getDayOfWeek(jdn), is(6));
    }

    @Test
    public void getFirstDayOfWeek() {
        assertThat(cal.getFirstDayOfWeek(jdn), is(cal.fromDate(2017, 5, 7)));
    }

    @Test
    public void getLastDayOfWeek() {
        assertThat(cal.getLastDayOfWeek(jdn), is(cal.fromDate(2017, 5, 13)));
    }

    @Test
    public void isWeekday() {
        assertThat(cal.isWeekday(jdn), is(false));
    }

    @Test
    public void isWeekend() {
        assertThat(cal.isWeekend(jdn), is(true));
    }

    // month

    @Test
    public void firstOfMonth() {
        assertThat(cal.firstOfMonth(jdn), is(cal.fromDate(2017, 5, 1)));
    }

    @Test
    public void lastOfMonth() {
        assertThat(cal.lastOfMonth(jdn), is(cal.fromDate(2017, 5, 31)));
    }

    @Test
    public void nextMonth() {
        assertThat(cal.nextMonth(jdn), is(cal.fromDate(2017, 6, 1)));
    }

    @Test
    public void prevMonth() {
        assertThat(cal.prevMonth(jdn), is(cal.fromDate(2017, 4, 1)));
    }

    @Test
    public void getMonthName() {
        assertThat(cal.getMonthName(jdn)[0], is('M'));
        assertThat(cal.getMonthName(jdn)[1], is('A'));
        assertThat(cal.getMonthName(jdn)[2], is('Y'));
    }

    @Test
    public void getDaysInMonth() {
        assertThat(cal.getDaysInMonth(jdn), is(31));
    }

    @Test
    public void getWeeksInMonth() {
        assertThat(cal.getWeeksInMonth(jdn), is(5));
    }

    @Test
    public void getWeekOfMonth() {
        assertThat(cal.getWeekOfMonth(jdn), is(1));
    }

    @Test
    public void getDayOfWeekOfFirstDayInMonth() {
        assertThat(cal.getDayOfWeekOfFirstDayInMonth(jdn), is(1));
    }

    @Test
    public void getLastWeekDayOfMonth() {
        assertThat(cal.getDayOfWeekOfLastDayInMonth(jdn), is(3));
    }

    @Test
    public void getLastFullWeek() {
        assertThat(cal.getLastFullWeek(jdn), is(3));
    }


    // year

    @Test
    public void getLastFullWgeteek() {
        assertThat(cal.getMonthsInYear(), is(12));
    }

    // week number

    @Test
    public void getWeekNumber() {
        assertThat(cal.getWeekNumber(jdn), is(351126));
    }
}
