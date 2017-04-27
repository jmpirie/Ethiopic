package com.hundaol.ethiopic.cal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by john.pirie on 2017-04-22.
 */
public class GregorianCalTest {

    private ICal cal = GregorianCal.INSTANCE;

    @Test
    public void getDay_whenSunday() {
        assertThat(cal.getDay(cal.fromDate(2017, 4, 23)), is(23));
    }

    @Test
    public void getDay_whenSaturday() {
        assertThat(cal.getDay(cal.fromDate(2017, 4, 22)), is(22));
    }

    @Test
    public void getDayOfWeek_whenSunday() {
        assertThat(cal.getDayOfWeek(cal.fromDate(2017, 4, 23)), is(0));
    }

    @Test
    public void getDayOfWeek_whenSaturday() {
        assertThat(cal.getDayOfWeek(cal.fromDate(2017, 4, 22)), is(6));
    }


}
