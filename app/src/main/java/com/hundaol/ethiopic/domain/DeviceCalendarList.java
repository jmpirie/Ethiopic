package com.hundaol.ethiopic.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by abinet on 6/6/17.
 */

public class DeviceCalendarList {

    private List<DeviceCalendar> deviceCalendarList;


    public List<DeviceCalendar> getDeviceCalendarList() {
        return deviceCalendarList;
    }

    public void setDeviceCalendarList(List<DeviceCalendar> deviceCalendarList) {
        this.deviceCalendarList = deviceCalendarList;
    }

    public List<String> getUniqueDisplayNames() {
        HashSet<String> uniqueList = new HashSet<>();
        for (DeviceCalendar deviceCalendar : deviceCalendarList) {
            uniqueList.add(deviceCalendar.getDisplayName());
        }
        return new ArrayList<>(uniqueList);
    }

    public List<DeviceCalendar> getDeviceCalendarsByDisplayName(String displayName) {
        List<DeviceCalendar> deviceCalendars = new ArrayList<>();
        for (DeviceCalendar deviceCalendar : deviceCalendarList) {
            if (deviceCalendar.getDisplayName().equals(displayName)) {
                deviceCalendars.add(deviceCalendar);
            }
        }
        return deviceCalendars;
    }
}
