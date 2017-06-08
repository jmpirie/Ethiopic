/**
 * Created by john.pirie on 2017-06-07.
 */
package com.hundaol.ethiopic.views

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

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import com.hundaol.ethiopic.adapters.DeviceCalendarEventListAdapter
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.domain.ColorModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by john.pirie on 2017-04-28.
 */

class DateView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    @BindView(R.id.image)
    lateinit var image: ImageView

    @BindView(R.id.image_overlay)
    lateinit var imageOverlay: View

    @BindView(R.id.month)
    lateinit var month: TextView

    @BindView(R.id.left_month)
    lateinit var leftMonth: View

    @BindView(R.id.right_month)
    lateinit var rightMonth: View

    @BindView(R.id.day)
    lateinit var day: TextView

    @BindView(R.id.left_day)
    lateinit var leftDay: View

    @BindView(R.id.right_day)
    lateinit var rightDay: View

    @BindView(R.id.year)
    lateinit var year: TextView

    @BindView(R.id.left_year)
    lateinit var leftYear: View

    @BindView(R.id.right_year)
    lateinit var rightYear: View

    @BindView(R.id.calendar_events)
    lateinit var calendarEventsRecyclerView: RecyclerView

    @BindView(R.id.calendar_message)
    lateinit var calendarMessageView: TextView


    var dateModel = DateModel.default
        get() = field
        set(value) {
            field = value
            validate()
        }

    var colorModel = ColorModel.default
        get() = field
        set(value) {
            field = value
            validate()
        }

    var cal: ICal = GregorianCal.INSTANCE
        get() = field
        set(value) {
            field = value
            validate()
        }

    private val adapter = DeviceCalendarEventListAdapter(context)
    private val disposables = CompositeDisposable()

    init {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        App.appComponent.inject(this)
        ButterKnife.bind(this)

        val layoutManager = LinearLayoutManager(context)
        calendarEventsRecyclerView.setLayoutManager(layoutManager)
        val dividerItemDecoration = DividerItemDecoration(calendarEventsRecyclerView.getContext(), layoutManager.getOrientation())
        calendarEventsRecyclerView.addItemDecoration(dividerItemDecoration)
        calendarEventsRecyclerView.setAdapter(adapter)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
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
        disposables.add(App.dateModels
                .debounce(250L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { dateModel ->
                            loadEvents()
                        },
                        { error ->
                            Timber.w(error, "error observed on date model subscription")
                        }))
        disposables.add(RxView.clicks(leftDay)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(dateModel.jdn - 1.0f)
                        },
                        { error ->
                            Timber.w(error, "error observed on leftDay clicks subscription")
                        }))
        disposables.add(RxView.clicks(rightDay)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(dateModel.jdn + 1.0f)
                        },
                        { error ->
                            Timber.w(error, "error observed on rightDay clicks subscription")
                        }))
        disposables.add(RxView.clicks(leftMonth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.prevMonth(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on leftMonth clicks subscription")
                        }))
        disposables.add(RxView.clicks(rightMonth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.nextMonth(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on rightMonth clicks subscription")
                        }))
        disposables.add(RxView.clicks(leftYear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.prevYear(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on leftYear clicks subscription")
                        }))
        disposables.add(RxView.clicks(rightYear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { v ->
                            App.setJdv(cal.nextYear(dateModel.jdn).toFloat(), 200L)
                        },
                        { error ->
                            Timber.w(error, "error observed on rightYear clicks subscription")
                        }))
    }

    private fun validate() {
        month.setText(String(cal.getMonthName(dateModel.jdn)))
        day.setText(cal.getDay(dateModel.jdn).toString())
        year.setText(cal.getYear(dateModel.jdn).toString())

        imageOverlay.setBackgroundColor(colorModel.dateImageOverlay(cal, dateModel.jdn))
    }

    private fun loadEvents() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            calendarEventsRecyclerView.setVisibility(VISIBLE)
            calendarMessageView.setVisibility(GONE)
            adapter.setCalendars(GregorianCal.INSTANCE.getDay(dateModel.jdn), GregorianCal.INSTANCE.getMonth(dateModel.jdn), GregorianCal.INSTANCE.getYear(dateModel.jdn))
        } else {
            calendarEventsRecyclerView.setVisibility(GONE);
            calendarMessageView.setVisibility(VISIBLE);
            calendarMessageView.setText("Hi, we don\'t seem to have permission to access you calendar events. Please go to the application properties and grant calendar access. Thank you.")
            calendarMessageView.setOnClickListener(
                    { v ->
                        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID)))
                    })
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.dispose()
    }
}
