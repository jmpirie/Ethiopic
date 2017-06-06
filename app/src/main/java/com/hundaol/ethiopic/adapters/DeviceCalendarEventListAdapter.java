package com.hundaol.ethiopic.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.domain.DeviceCalendar;
import com.hundaol.ethiopic.domain.DeviceCalendarList;
import com.hundaol.ethiopic.viewmodels.DeviceCalendarEventViewModel;
import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel;
import com.hundaol.ethiopic.views.DeviceCalendarEventItemView;
import com.hundaol.ethiopic.views.DeviceCalendarEventItemViewHolder;
import com.hundaol.ethiopic.views.HeaderItemView;
import com.hundaol.ethiopic.views.HeaderItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abinet on 6/6/17.
 */

public class DeviceCalendarEventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<Object> viewModels = new ArrayList<>();
    private List<Integer> viewTypes = new ArrayList<>();

    private static final int CALENDAR_HEADER = 0;
    private static final int CALENDAR_ITEM = 1;

    private DeviceCalendarList calendarList;

    public DeviceCalendarEventListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CALENDAR_HEADER) {
            return new HeaderItemViewHolder((HeaderItemView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_calendar_header_item_view, parent, false));
        } else {
            return new DeviceCalendarEventItemViewHolder((DeviceCalendarEventItemView) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_calendar_event_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == CALENDAR_HEADER) {
            ((HeaderItemViewHolder) holder).setViewModel((HeaderItemViewModel) getViewModel(position));
        } else {
            ((DeviceCalendarEventItemViewHolder) holder).setViewModel((DeviceCalendarEventViewModel) getViewModel(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(position);
    }

    @Override
    public int getItemCount() {
        return viewModels.size();
    }

    public void setCalendars(DeviceCalendarList calendars) {
        this.calendarList = calendars;
        validateViewModels();
    }

    public void validateViewModels() {
        viewModels.clear();
        viewTypes.clear();

        List<String> displayNames = calendarList.getUniqueDisplayNames();

        for (String displayName : displayNames) {
            HeaderItemViewModel headerItemViewModel = new HeaderItemViewModel();
            headerItemViewModel.setText(displayName);
            headerItemViewModel.setTextColor(ContextCompat.getColor(context, R.color.black));
            viewModels.add(headerItemViewModel);
            viewTypes.add(CALENDAR_HEADER);
            for (DeviceCalendar deviceCalendar : calendarList.getDeviceCalendarsByDisplayName(displayName)) {
                DeviceCalendarEventViewModel deviceCalendarEventViewModel = new DeviceCalendarEventViewModel(context, deviceCalendar);
                viewModels.add(deviceCalendarEventViewModel);
                viewTypes.add(CALENDAR_ITEM);
            }
        }

        notifyDataSetChanged();
    }

    public Object getViewModel(int position) {
        return viewModels.get(position);
    }
}
