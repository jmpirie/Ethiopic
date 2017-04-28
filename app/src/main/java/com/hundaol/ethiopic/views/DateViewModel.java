package com.hundaol.ethiopic.views;

import com.hundaol.ethiopic.cal.ICal;

/**
 * Created by john.pirie on 2017-04-28.
 */

public class DateViewModel {

    private ICal cal;
    private int jdn;

    public final ViewModelChangeEvent<DateViewModel> valueChangeEvent = new ViewModelChangeEvent<>();

    public DateViewModel(ICal cal) {
        this.cal = cal;
    }

    public ICal getCal() {
        return cal;
    }

    public void setCal(ICal cal) {
        if (this.cal != cal) {
            this.cal = cal;
            valueChangeEvent.modelChanged(this);
        }
    }

    public int getJdn() {
        return jdn;
    }

    public void setJdn(int jdn) {
        if (this.jdn != jdn) {
            this.jdn = jdn;
            valueChangeEvent.modelChanged(this);
        }
    }

    public String getMonth() {
        return new String(cal.getMonthName(jdn));
    }

    public String getDay() {
        return Integer.toString(cal.getDay(jdn));
    }

    public String getYear() {
        return Integer.toString(cal.getYear(jdn));
    }
}
