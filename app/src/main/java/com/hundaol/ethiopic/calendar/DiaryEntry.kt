package com.hundaol.ethiopic.calendar

import org.joda.time.DateTime

import java.text.SimpleDateFormat

/**
 * Created by abinet on 6/6/17.
 */

data class DiaryEntry(var displayName: String = "",
                      var title: String = "",
                      var start: DateTime = minDate,
                      var end: DateTime = maxDate) {

    val isAllDayEvent: Boolean
        get() {
            val dateFormat = SimpleDateFormat("HH:mm:ss a")
            return dateFormat.format(start.toDate()) == dateFormat.format(end.toDate())
        }

    companion object {
        val minDate = DateTime(0)
        val maxDate = DateTime(Long.MAX_VALUE)
    }
}
