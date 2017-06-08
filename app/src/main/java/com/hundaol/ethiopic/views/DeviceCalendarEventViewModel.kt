package com.hundaol.ethiopic.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable

import com.hundaol.ethiopic.domain.CalendarEvent

/**
 * Created by abinet on 6/6/17.
 */

class DeviceCalendarEventViewModel(val context: Context, var calendarEvent: CalendarEvent) {
    var backgroundIndicator: Drawable? = null
}
