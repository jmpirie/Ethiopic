package com.hundaol.ethiopic.adapters

import android.content.ContentUris
import android.content.Context
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.calendar.CalendarEvent
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

    private var calendarEventList: List<CalendarEvent> = ArrayList()

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

    fun validateViewModels() {
        viewModels.clear()
        viewTypes.clear()

        val eventsByName = calendarEventList.groupBy({e -> e.displayName})

        for (name in eventsByName.keys.sorted()){
            val events = eventsByName[name]

            val headerItemViewModel = HeaderItemViewModel()
            headerItemViewModel.text = name
            headerItemViewModel.textColor = ContextCompat.getColor(context, R.color.white)
            headerItemViewModel.backgroundColor = ContextCompat.getColor(context, R.color.black_a9)
            viewModels.add(headerItemViewModel)
            viewTypes.add(CALENDAR_HEADER)
            for (event in events!!) {
                val deviceCalendarEventViewModel = DeviceCalendarEventViewModel(context, event)
                viewModels.add(deviceCalendarEventViewModel)
                viewTypes.add(CALENDAR_ITEM)
            }
        }

        notifyDataSetChanged()
    }

    fun getViewModel(position: Int): Any {
        return viewModels[position]
    }

    fun setEvents(events: List<CalendarEvent>) : Unit {
        calendarEventList = events
        validateViewModels()
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
