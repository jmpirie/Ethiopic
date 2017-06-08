package com.hundaol.ethiopic.adapters

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.domain.DeviceCalendar
import com.hundaol.ethiopic.domain.DeviceCalendarList
import com.hundaol.ethiopic.viewmodels.DeviceCalendarEventViewModel
import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel
import com.hundaol.ethiopic.views.DeviceCalendarEventItemView
import com.hundaol.ethiopic.views.DeviceCalendarEventItemViewHolder
import com.hundaol.ethiopic.views.HeaderItemView
import com.hundaol.ethiopic.views.HeaderItemViewHolder

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import timber.log.Timber

import java.util.ArrayList
import java.util.Calendar

/**
 * Created by abinet on 6/6/17.
 */

class DeviceCalendarEventListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewModels = ArrayList<Any>()
    private val viewTypes = ArrayList<Int>()

    private var calendarList: DeviceCalendarList? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CALENDAR_HEADER) {
            return HeaderItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_calendar_header_item_view, parent, false) as HeaderItemView)
        } else {
            return DeviceCalendarEventItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_calendar_event_item, parent, false) as DeviceCalendarEventItemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == CALENDAR_HEADER) {
            (holder as HeaderItemViewHolder).setViewModel(getViewModel(position) as HeaderItemViewModel)
        } else {
            (holder as DeviceCalendarEventItemViewHolder).setViewModel(getViewModel(position) as DeviceCalendarEventViewModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypes[position]
    }

    override fun getItemCount(): Int {
        return viewModels.size
    }

    fun setCalendars(day: Int, month: Int, year: Int) {
        val deviceCalendarList = DeviceCalendarList()
        deviceCalendarList.deviceCalendarList = getCalendars(day, month, year)
        this.calendarList = deviceCalendarList
        validateViewModels()
    }

    fun validateViewModels() {
        viewModels.clear()
        viewTypes.clear()

        val displayNames = calendarList!!.uniqueDisplayNames

        for (displayName in displayNames) {
            val headerItemViewModel = HeaderItemViewModel()
            headerItemViewModel.text = displayName
            headerItemViewModel.textColor = ContextCompat.getColor(context, R.color.white)
            headerItemViewModel.backgroundColor = ContextCompat.getColor(context, R.color.black_a9)
            viewModels.add(headerItemViewModel)
            viewTypes.add(CALENDAR_HEADER)
            for (deviceCalendar in calendarList!!.getDeviceCalendarsByDisplayName(displayName)) {
                val deviceCalendarEventViewModel = DeviceCalendarEventViewModel(context, deviceCalendar)
                viewModels.add(deviceCalendarEventViewModel)
                viewTypes.add(CALENDAR_ITEM)
            }
        }

        notifyDataSetChanged()
    }

    fun getViewModel(position: Int): Any {
        return viewModels[position]
    }

    fun getCalendars(day: Int, month: Int, year: Int): List<DeviceCalendar> {

        val beginTime = Calendar.getInstance()
        beginTime.set(year, month, day, 0, 0)
        val endTime = Calendar.getInstance()
        endTime.set(year, month, day, 23, 59)

        val uriBuilder = CalendarContract.Instances.CONTENT_URI.buildUpon()
        ContentUris.appendId(uriBuilder, beginTime.timeInMillis)
        ContentUris.appendId(uriBuilder, endTime.timeInMillis)
        val uri = uriBuilder.build()

        val googleCalendarList = ArrayList<DeviceCalendar>()

        val cursor = context.contentResolver.query(uri, FIELDS, null, null, null)
        try {
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val googleCalendar = DeviceCalendar()

                    googleCalendar.displayName = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.CALENDAR_DISPLAY_NAME))
                    googleCalendar.title = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.TITLE))

                    val begin = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.BEGIN))
                    val end = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.END))
                    googleCalendar.start = DateTime(java.lang.Long.valueOf(begin), DateTimeZone.UTC)
                    googleCalendar.end = DateTime(java.lang.Long.valueOf(end), DateTimeZone.UTC)

                    googleCalendarList.add(googleCalendar)
                }
            }
        } catch (ex: AssertionError) {
            Timber.e(ex)
        }

        return googleCalendarList
    }

    companion object {

        private val CALENDAR_HEADER = 0
        private val CALENDAR_ITEM = 1

        val FIELDS = arrayOf(
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.TITLE,
                CalendarContract.Instances.END,
                CalendarContract.Instances.CALENDAR_DISPLAY_NAME)
    }
}
