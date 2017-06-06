package com.hundaol.ethiopic.views;

import android.support.v7.widget.RecyclerView;

import com.hundaol.ethiopic.viewmodels.DeviceCalendarEventViewModel;

/**
 * Created by abinet on 6/6/17.
 */

public class CalendarItemViewHolder extends RecyclerView.ViewHolder {

    private DeviceCalendarEventItemView view;

    public CalendarItemViewHolder(DeviceCalendarEventItemView view) {
        super(view);
        this.view = view;
    }

    public void setViewModel(DeviceCalendarEventViewModel viewModel) {
        view.bindTo(viewModel);
    }
}
