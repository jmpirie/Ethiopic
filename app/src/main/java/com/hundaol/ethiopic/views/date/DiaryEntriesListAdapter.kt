package com.hundaol.ethiopic.adapters

import android.content.Context
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.calendar.DiaryEntry
import com.hundaol.ethiopic.viewmodels.DiaryEntryItemViewModel
import com.hundaol.ethiopic.viewmodels.DiaryHeaderItemViewModel
import com.hundaol.ethiopic.views.date.DiaryEntryItemView
import com.hundaol.ethiopic.views.date.DiaryEntryItemViewHolder
import com.hundaol.ethiopic.views.date.DiaryHeaderItemView
import com.hundaol.ethiopic.views.date.DiaryHeaderItemViewHolder

import java.util.ArrayList

/**
 * Created by abinet on 6/6/17.
 */

class DiaryEntriesListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewModels = ArrayList<Any>()
    private val viewTypes = ArrayList<Int>()

    private var diaryEntryList: List<DiaryEntry> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CALENDAR_HEADER) {
            return DiaryHeaderItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_diary_header_item, parent, false) as DiaryHeaderItemView)
        } else {
            return DiaryEntryItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_diary_entry_item, parent, false) as DiaryEntryItemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == CALENDAR_HEADER) {
            (holder as DiaryHeaderItemViewHolder).setViewModel(getViewModel(position) as DiaryHeaderItemViewModel)
        } else {
            (holder as DiaryEntryItemViewHolder).setViewModel(getViewModel(position) as DiaryEntryItemViewModel)
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

        val eventsByName = diaryEntryList.groupBy({ e -> e.displayName})

        for (name in eventsByName.keys.sorted()){
            val events = eventsByName[name]

            val headerItemViewModel = DiaryHeaderItemViewModel()
            headerItemViewModel.text = name
            headerItemViewModel.textColor = ContextCompat.getColor(context, R.color.white)
            headerItemViewModel.backgroundColor = ContextCompat.getColor(context, R.color.black_a9)
            viewModels.add(headerItemViewModel)
            viewTypes.add(CALENDAR_HEADER)
            for (event in events!!) {
                val deviceCalendarEventViewModel = DiaryEntryItemViewModel(context, event)
                viewModels.add(deviceCalendarEventViewModel)
                viewTypes.add(CALENDAR_ITEM)
            }
        }

        notifyDataSetChanged()
    }

    fun getViewModel(position: Int): Any {
        return viewModels[position]
    }

    fun setEvents(events: List<DiaryEntry>) : Unit {
        diaryEntryList = events
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
