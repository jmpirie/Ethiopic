package com.hundaol.ethiopic.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundaol.ethiocal.BuildConfig;
import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.App;
import com.hundaol.ethiopic.adapters.DeviceCalendarEventListAdapter;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.hundaol.ethiopic.domain.DateModel;
import com.jakewharton.rxbinding2.view.RxView;

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

    @BindView(R.id.calendar_message)
    TextView calendarMessageView;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        calendarEventsRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(calendarEventsRecyclerView.getContext(),
                layoutManager.getOrientation());
        calendarEventsRecyclerView.addItemDecoration(mDividerItemDecoration);
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
            calendarEventsRecyclerView.setVisibility(VISIBLE);
            calendarMessageView.setVisibility(GONE);
            adapter.setCalendars(GregorianCal.INSTANCE.getDay(viewModel.getJdn()),GregorianCal.INSTANCE.getMonth(viewModel.getJdn()), GregorianCal.INSTANCE.getYear(viewModel.getJdn()));
        } else {
            calendarEventsRecyclerView.setVisibility(GONE);
            calendarMessageView.setVisibility(VISIBLE);
            calendarMessageView.setText("Hi, we don\'t seem to have permission to access you calendar events. Please go to the application properties and grant calendar access. Thank you.");
            calendarMessageView.setOnClickListener(v -> context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID))));
        }
    }
}
