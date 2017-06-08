package com.hundaol.ethiopic.domain

import java.util.ArrayList
import java.util.HashSet

/**
 * Created by abinet on 6/6/17.
 */

class CalendarEventList {

    var calendarEventList: List<CalendarEvent>? = null

    val uniqueDisplayNames: List<String>
        get() {
            val uniqueList = HashSet<String>()
            for (deviceCalendar in calendarEventList!!) {
                uniqueList.add(deviceCalendar.displayName)
            }
            return ArrayList(uniqueList)
        }

    fun getDeviceCalendarsByDisplayName(displayName: String): List<CalendarEvent> {
        val deviceCalendars = ArrayList<CalendarEvent>()
        for (deviceCalendar in calendarEventList!!) {
            if (deviceCalendar.displayName == displayName) {
                deviceCalendars.add(deviceCalendar)
            }
        }
        return deviceCalendars
    }
}