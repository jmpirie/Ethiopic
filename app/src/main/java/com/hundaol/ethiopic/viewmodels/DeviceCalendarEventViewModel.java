package com.hundaol.ethiopic.viewmodels;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.hundaol.ethiopic.domain.DeviceCalendar;

/**
 * Created by abinet on 6/6/17.
 */

public class DeviceCalendarEventViewModel {

    private Drawable backgroundIndicator;
    private DeviceCalendar deviceCalendar;

    public DeviceCalendarEventViewModel(Context context, DeviceCalendar deviceCalendar) {
        this.deviceCalendar = deviceCalendar;
    }

    public DeviceCalendar getDeviceCalendar() {
        return deviceCalendar;
    }

    public void setDeviceCalendar(DeviceCalendar deviceCalendar) {
        this.deviceCalendar = deviceCalendar;
    }

    public Drawable getBackgroundIndicator() {
        return backgroundIndicator;
    }

    public void setBackgroundIndicator(Drawable backgroundIndicator) {
        this.backgroundIndicator = backgroundIndicator;
    }
}
