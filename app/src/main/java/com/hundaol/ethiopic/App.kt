package com.hundaol.ethiopic

import android.animation.ValueAnimator
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.view.animation.AccelerateDecelerateInterpolator

import com.google.firebase.analytics.FirebaseAnalytics
import com.hundaol.ethiocal.BuildConfig
import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.adapters.DeviceCalendarEventListAdapter
import com.hundaol.ethiopic.calendar.CalendarEvent
import com.hundaol.ethiopic.domain.ColorModel
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.logging.EthioTree
import com.hundaol.ethiopic.modules.AppComponent
import com.hundaol.ethiopic.modules.AppModule
import com.hundaol.ethiopic.modules.DaggerAppComponent
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

import javax.inject.Inject

import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Created by john.pirie on 2017-04-14.
 */

class App : Application() {

    @Inject
    lateinit internal var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()

        context = this

        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else EthioTree())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        appComponent.inject(this)

        setColorModel(context.getColor(R.color.default_theme))
    }

    companion object {
        lateinit var context: Context

        lateinit var appComponent: AppComponent

        private val colorModelSubject = BehaviorSubject.createDefault(ColorModel())

        private val dateModelSubject = BehaviorSubject.createDefault(DateModel.default)

        val colorModels = colorModelSubject as Observable<ColorModel>

        val dateModels = dateModelSubject as Observable<DateModel>

        var jdvAnimator: ValueAnimator? = null

        fun setJdv(jdv: Float, duration: Long = 0L): Unit {
            if (duration < 0L) {
                dateModelSubject.onNext(DateModel(jdv))
            } else if (duration == 0L) {
                jdvAnimator?.cancel()
                jdvAnimator = null
                dateModelSubject.onNext(DateModel(jdv))
            } else {
                val anim = ValueAnimator.ofFloat(dateModelSubject.value.jdv, jdv)
                anim.setInterpolator(AccelerateDecelerateInterpolator())
                anim.setDuration(duration)
                anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator) {
                        setJdv(animation.animatedValue as Float, -1L)
                    }
                })
                anim.start()
                jdvAnimator = anim
            }
        }

        fun setColorModel(rgb: Int): Unit {
            colorModelSubject.onNext(ColorModel(rgb))
        }

        val events = dateModels
                .debounce(250, TimeUnit.MILLISECONDS)
                .map(
                        { dateModel ->
                            Timber.d("getting events for %d", dateModel.jdn)
                            getEventsFor(dateModel)
                        })

        private fun getEventsFor(dateModel: DateModel): List<CalendarEvent> {
            val uri = CalendarContract.Instances.CONTENT_BY_DAY_URI.buildUpon()
                    .appendEncodedPath(dateModel.jdn.toString())
                    .appendEncodedPath(dateModel.jdn.toString())
                    .build()

            val events = ArrayList<CalendarEvent>()
            val cursor = context.contentResolver.query(uri, DeviceCalendarEventListAdapter.FIELDS, null, null, null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val event = CalendarEvent()
                    event.displayName = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.CALENDAR_DISPLAY_NAME))
                    event.title = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.TITLE))

                    val begin = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.BEGIN))
                    val end = cursor.getString(cursor.getColumnIndex(CalendarContract.Instances.END))
                    event.start = DateTime(java.lang.Long.valueOf(begin), DateTimeZone.UTC)
                    event.end = DateTime(java.lang.Long.valueOf(end), DateTimeZone.UTC)

                    events.add(event)
                }
            }
            return events
        }
    }
}
