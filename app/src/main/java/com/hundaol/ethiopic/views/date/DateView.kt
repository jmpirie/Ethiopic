/**
 * Created by john.pirie on 2017-06-07.
 */
package com.hundaol.ethiopic.views.date

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.hundaol.ethiocal.R
import com.hundaol.ethiocal.BuildConfig
import com.hundaol.ethiopic.App
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.cal.GregorianCal
import com.jakewharton.rxbinding2.view.RxView

import butterknife.BindView
import butterknife.ButterKnife
import com.hundaol.ethiopic.adapters.DiaryEntriesListAdapter
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.calendar.DiaryEntry
import com.hundaol.ethiopic.domain.ColorModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by john.pirie on 2017-04-28.
 */

class DateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    @BindView(R.id.banner_image)
    lateinit var bannerImage: ImageView

    @BindView(R.id.banner_image_overlay)
    lateinit var bannderImageOverlay: View

    @BindView(R.id.month)
    lateinit var month: TextView

    @BindView(R.id.prev_month)
    lateinit var prevMonth: View

    @BindView(R.id.next_month)
    lateinit var nextMonth: View

    @BindView(R.id.day)
    lateinit var day: TextView

    @BindView(R.id.prev_day)
    lateinit var prevDay: View

    @BindView(R.id.next_day)
    lateinit var nextDay: View

    @BindView(R.id.year)
    lateinit var year: TextView

    @BindView(R.id.prev_year)
    lateinit var prevYear: View

    @BindView(R.id.next_year)
    lateinit var nextYear: View

    @BindView(R.id.event_list)
    lateinit var calendarEventsList: RecyclerView

    @BindView(R.id.no_permissions_message)
    lateinit var noPermissionsMessage: TextView

    @BindView(R.id.no_events_message)
    lateinit var noEventsMessage: TextView

    var dateModel = DateModel.default
        get() = field
        set(value) {
            field = value
            onModelsChanged()
        }

    var colorModel = ColorModel.default
        get() = field
        set(value) {
            field = value
            onModelsChanged()
        }

    var cal: ICal = GregorianCal.INSTANCE
        get() = field
        set(value) {
            field = value
            onModelsChanged()
        }

    private val adapter = DiaryEntriesListAdapter(context)
    private val disposables = CompositeDisposable()

    init {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        App.appComponent.inject(this)
        ButterKnife.bind(this)

        val layoutManager = LinearLayoutManager(context)
        calendarEventsList.setLayoutManager(layoutManager)
        val dividerItemDecoration = DividerItemDecoration(calendarEventsList.getContext(), layoutManager.getOrientation())
        calendarEventsList.addItemDecoration(dividerItemDecoration)
        calendarEventsList.setAdapter(adapter)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        noPermissionsMessage.setOnClickListener(
                { v ->
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)))
                })

        disposables.add(App.colorModels
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { colorModel ->
                            this.colorModel = colorModel
                            invalidate()
                        },
                        { error ->
                            Timber.w(error, "error observed on color model subscription")
                        }))
        disposables.add(App.dateModels
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dateModel ->
                            this.dateModel = dateModel
                            invalidate()
                        },
                        { error ->
                            Timber.w(error, "error observed on date model subscription")
                        }))
        disposables.add(App.events
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { events ->
                            setEvents(events)
                        },
                        { error ->
                            Timber.w(error, "error observed on events subscription")
                        }))

        disposables.add(RxView.clicks(prevDay)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(dateModel.jdn - 1.0f)
                        },
                        { error ->
                            Timber.w(error, "error observed on prev day clicks subscription")
                        }))
        disposables.add(RxView.clicks(nextDay)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(dateModel.jdn + 1.0f)
                        },
                        { error ->
                            Timber.w(error, "error observed on next day clicks subscription")
                        }))

        disposables.add(RxView.clicks(prevMonth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.prevMonth(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on prev month clicks subscription")
                        }))
        disposables.add(RxView.clicks(nextMonth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.nextMonth(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on next month clicks subscription")
                        }))

        disposables.add(RxView.clicks(prevYear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.prevYear(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on prev year clicks subscription")
                        }))
        disposables.add(RxView.clicks(nextYear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.nextYear(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on next year clicks subscription")
                        }))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.dispose()
    }

    private fun onModelsChanged() {
        month.setText(String(cal.getMonthName(dateModel.jdn)))
        day.setText(cal.getDay(dateModel.jdn).toString())
        year.setText(cal.getYear(dateModel.jdn).toString())

        bannderImageOverlay.setBackgroundColor(colorModel.dateImageOverlay(cal, dateModel.jdn))
    }

    private fun setEvents(events: List<DiaryEntry>) {
        adapter.setEvents(events)
        if (events.isNotEmpty()) {
            calendarEventsList.setVisibility(VISIBLE)
            noPermissionsMessage.setVisibility(GONE)
            noEventsMessage.setVisibility(GONE)
        } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            calendarEventsList.setVisibility(GONE);
            noPermissionsMessage.setVisibility(GONE);
            noEventsMessage.setVisibility(VISIBLE)
        } else {
            calendarEventsList.setVisibility(GONE);
            noPermissionsMessage.setVisibility(VISIBLE);
            noEventsMessage.setVisibility(GONE)
        }
    }
}
