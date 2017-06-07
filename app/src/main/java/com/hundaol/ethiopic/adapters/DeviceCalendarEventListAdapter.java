package com.hundaol.ethiopic.adapters;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Calendar;
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

    public void setCalendars(Integer day, Integer month, Integer year) {
        DeviceCalendarList deviceCalendarList = new DeviceCalendarList();
        deviceCalendarList.setDeviceCalendarList(getCalendars(day, month, year));
        this.calendarList = deviceCalendarList;
        validateViewModels();
    }

    public void validateViewModels() {
        viewModels.clear();
        viewTypes.clear();

        List<String> displayNames = calendarList.getUniqueDisplayNames();

        for (String displayName : displayNames) {
            HeaderItemViewModel headerItemViewModel = new HeaderItemViewModel();
            headerItemViewModel.setText(displayName);
            headerItemViewModel.setTextColor(ContextCompat.getColor(context, R.color.white));
            headerItemViewModel.setBackgroundColor(ContextCompat.getColor(context, R.color.black_a9));
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

    public static final String[] FIELDS = {
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.END,
            CalendarContract.Instances.CALENDAR_DISPLAY_NAME
    };

    public List<DeviceCalendar> getCalendars(Integer day, Integer month, Integer year) {

        ContentResolver contentResolver = context.getContentResolver();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, 0, 0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, 23, 59);

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, beginTime.getTimeInMillis());
        ContentUris.appendId(builder, endTime.getTimeInMillis());

        Cursor cursor = contentResolver.query(builder.build(), FIELDS, null, null, null);

        List<DeviceCalendar> googleCalendarList = new ArrayList<>();

        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DeviceCalendar googleCalendar = new DeviceCalendar();

                    googleCalendar.setDisplayName(cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.CALENDAR_DISPLAY_NAME)));
                    googleCalendar.setTitle(cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.TITLE)));

                    String begin = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.BEGIN));
                    String end = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.END));
                    googleCalendar.setStart(new DateTime(Long.valueOf(begin), DateTimeZone.UTC));
                    googleCalendar.setEnd(new DateTime(Long.valueOf(end), DateTimeZone.UTC));

                    googleCalendarList.add(googleCalendar);
                }
            }
        } catch (AssertionError ex) { /*TODO: log exception and bail*/ }

        return googleCalendarList;
    }
}
