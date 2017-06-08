package com.hundaol.ethiopic.domain

import java.util.ArrayList
import java.util.HashSet

/**
 * Created by abinet on 6/6/17.
 */

class DeviceCalendarList {

    var deviceCalendarList: List<DeviceCalendar>? = null

    val uniqueDisplayNames: List<String>
        get() {
            val uniqueList = HashSet<String>()
            for (deviceCalendar in deviceCalendarList!!) {
                uniqueList.add(deviceCalendar.displayName)
            }
            return ArrayList(uniqueList)
        }

    fun getDeviceCalendarsByDisplayName(displayName: String): List<DeviceCalendar> {
        val deviceCalendars = ArrayList<DeviceCalendar>()
        for (deviceCalendar in deviceCalendarList!!) {
            if (deviceCalendar.displayName == displayName) {
                deviceCalendars.add(deviceCalendar)
            }
        }
        return deviceCalendars
    }
}