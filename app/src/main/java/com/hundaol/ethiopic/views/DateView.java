package com.hundaol.ethiopic.views;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.App;
import com.hundaol.ethiopic.adapters.DeviceCalendarEventListAdapter;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.hundaol.ethiopic.domain.DateModel;
import com.hundaol.ethiopic.domain.DeviceCalendar;
import com.hundaol.ethiopic.domain.DeviceCalendarList;
import com.jakewharton.rxbinding2.view.RxView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

import static com.hundaol.ethiopic.domain.DateModel.uniqueJdnFilter;

/**
 * Created by john.pirie on 2017-04-28.
 */

public class DateView extends LinearLayout {

    @BindView(R.id.month)
    TextView month;

    @BindView(R.id.day)
    TextView day;

    @BindView(R.id.year)
    TextView year;

    @BindView(R.id.left)
    View left;

    @BindView(R.id.right)
    View right;

    @BindView(R.id.calendar_events)
    RecyclerView calendarEventsRecyclerView;

    @Inject
    DateModel dateModel;

    private DateViewModel viewModel;
    private Context context;
    DeviceCalendarEventListAdapter adapter;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public DateView(@NonNull Context context) {
        this(context, null);
    }

    public DateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.viewModel = new DateViewModel(GregorianCal.INSTANCE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);

        adapter = new DeviceCalendarEventListAdapter(context);

        calendarEventsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        calendarEventsRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        disposables.add(RxView.clicks(left).subscribe(v -> {
            dateModel.prevDays(1);
        }));

        disposables.add(RxView.clicks(right).subscribe(v -> {
            dateModel.nextDay(1);
        }));

        disposables.add(dateModel.getDate().filter(uniqueJdnFilter()).subscribe(jdv -> {
            viewModel.setJdn((int) jdv.floatValue());
            modelChanged();
        }));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposables.dispose();
    }

    public void setViewModel(DateViewModel viewModel) {
        this.viewModel = viewModel;
        modelChanged();
    }

    public DateViewModel getViewModel() {
        return viewModel;
    }

    void modelChanged() {
        month.setText(viewModel.getMonthName());
        day.setText(viewModel.getDay());
        year.setText(viewModel.getYear());

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            DeviceCalendarList deviceCalendarList = new DeviceCalendarList();
            deviceCalendarList.setDeviceCalendarList(getCalendars(viewModel.getMonth(), viewModel.getDay(), viewModel.getYear()));
            adapter.setCalendars(deviceCalendarList);
        }
    }

    public static final String[] FIELDS = {
            CalendarContract.Instances.BEGIN,
            CalendarContract.Instances.TITLE,
            CalendarContract.Instances.END,
            CalendarContract.Instances.CALENDAR_COLOR,
            CalendarContract.Instances.CALENDAR_DISPLAY_NAME
    };

    public List<DeviceCalendar> getCalendars(Integer month, String day, String year) {
        // Fetch a list of all calendars sync'd with the device and their display names
        ContentResolver contentResolver = context.getContentResolver();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Integer.valueOf(year), month,Integer.valueOf(day), 0, 0);
        long startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(Integer.valueOf(year), month,Integer.valueOf(day), 23, 59);
        long endMillis = endTime.getTimeInMillis();

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, startMillis);
        ContentUris.appendId(builder, endMillis);

        Cursor cursor = contentResolver.query(builder.build(), FIELDS, null, null, null);

        List<DeviceCalendar> googleCalendarList = new ArrayList<>();

        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DeviceCalendar googleCalendar = new DeviceCalendar();

                    String title = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.TITLE));
                    String displayName = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.CALENDAR_DISPLAY_NAME));
                    Long color = cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR));
                    String begin = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.BEGIN));
                    String end = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.END));
                    googleCalendar.setColor(color);
                    googleCalendar.setDisplayName(displayName);
                    googleCalendar.setTitle(title);
                    googleCalendar.setStart(new DateTime(Long.valueOf(begin), DateTimeZone.UTC));
                    googleCalendar.setEnd(new DateTime(Long.valueOf(end), DateTimeZone.UTC));
                    googleCalendarList.add(googleCalendar);
                }
            }
        } catch (AssertionError ex) { /*TODO: log exception and bail*/ }

        return googleCalendarList;
    }
}
