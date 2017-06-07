package com.hundaol.ethiopic.domain;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;

/**
 * Created by abinet on 6/6/17.
 */

public class DeviceCalendar {

    private String displayName;
    private String title;
    private DateTime start;
    private DateTime end;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getStart() {
        return start;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public DateTime getEnd() {
        return end;
    }

    public boolean isAllDayEvent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss a");
        return dateFormat.format(start.toDate()).equals(dateFormat.format(end.toDate()));
    }
}
