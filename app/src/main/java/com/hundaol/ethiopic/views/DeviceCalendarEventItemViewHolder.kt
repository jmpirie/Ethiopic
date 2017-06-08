package com.hundaol.ethiopic.views

import android.support.v7.widget.RecyclerView

import com.hundaol.ethiopic.viewmodels.DeviceCalendarEventViewModel
import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel

/**
 * Created by abinet on 6/6/17.
 */

class DeviceCalendarEventItemViewHolder(private val view: DeviceCalendarEventItemView) : RecyclerView.ViewHolder(view) {

    fun setViewModel(viewModel: DeviceCalendarEventViewModel) {
        view.bindTo(viewModel)
    }
}
