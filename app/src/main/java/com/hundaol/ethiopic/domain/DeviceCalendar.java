package com.hundaol.ethiopic.domain;

import org.joda.time.DateTime;

/**
 * Created by abinet on 6/6/17.
 */

public class DeviceCalendar {
    String displayName;
    String title;
    Long color;
    DateTime start;
    DateTime end;


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

    public void setColor(Long color) {
        this.color = color;
    }

    public Long getColor() {
        return color;
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
}
