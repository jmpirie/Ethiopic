package com.hundaol.ethiopic.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable

import com.hundaol.ethiopic.domain.DeviceCalendar

/**
 * Created by abinet on 6/6/17.
 */

class DeviceCalendarEventViewModel(val context: Context, var deviceCalendar: DeviceCalendar) {
    var backgroundIndicator: Drawable? = null
}
