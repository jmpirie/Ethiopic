package com.hundaol.ethiopic.views;

import android.support.v7.widget.RecyclerView;

import com.hundaol.ethiopic.viewmodels.DeviceCalendarEventViewModel;
import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel;

/**
 * Created by abinet on 6/6/17.
 */

public class DeviceCalendarEventItemViewHolder extends RecyclerView.ViewHolder {

    private DeviceCalendarEventItemView view;

    public DeviceCalendarEventItemViewHolder(DeviceCalendarEventItemView view) {
        super(view);
        this.view = view;
    }

    public void setViewModel(DeviceCalendarEventViewModel viewModel) {
        view.bindTo(viewModel);
    }
}
