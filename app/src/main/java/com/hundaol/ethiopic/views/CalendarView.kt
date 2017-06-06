package com.hundaol.ethiopic.views

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

import com.hundaol.ethiopic.App
import com.hundaol.ethiopic.cal.GregorianCal
import com.hundaol.ethiopic.domain.ColorModel
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.stamps.MonthStamp

import io.reactivex.disposables.CompositeDisposable
import org.reactivestreams.Subscriber
import timber.log.Timber

/**
 * Created by jmpirie on 2017-04-14
 */
class CalendarView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val disposables = CompositeDisposable()

    private var viewModel = CalendarViewModel()
        get() = field
        set(value) {
            field = value
            monthStamp.viewModel = viewModel
            invalidate()
        }

    private var dateModel = DateModel.default
        get() = field
        set(value) {
            field = value
            monthStamp.jdn = value.jdn
            invalidate()
        }

    private var colorModel = ColorModel.default
        get() = field
        set(value) {
            field = value
            monthStamp.colorModel = value
            invalidate()
        }

    private var cal = GregorianCal.INSTANCE
        get() = field
        set(value) {
            field = value
            monthStamp.cal = value
            invalidate()
        }

    private var monthStamp = MonthStamp()

    private var vw = 0
    private var vh = 0
    private var cw = 0.0f
    private var ch = 0.0f

    init {
        App.appComponent.inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        disposables.add(App.colorModels.subscribe(
                { colorModel ->
                    this.colorModel = colorModel
                    invalidate()
                },
                { error ->
                    Timber.w(error, "error observed on color model subscription")
                }))
        disposables.add(App.dateModels.subscribe(
                { dateModel ->
                    this.dateModel = dateModel
                    invalidate()
                },
                { error ->
                    Timber.w(error, "error observed on date model subscription")
                }))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.dispose()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b);

        vw = r - l
        vh = b - t

        cw = vw / 8.0f
        ch = vw / 8.0f

        viewModel = viewModel.copy(cellWidth = cw, cellHeight = ch, offset = 3 * cw)
    }

    override fun draw(canvas: Canvas): Unit {
        super.draw(canvas)

        var jdn = dateModel.jdn
        var monthBounds = viewModel.boundsForMonth(dateModel, cal, jdn)

        while (monthBounds.bottom > 0) {
            jdn = cal.prevMonth(jdn)
            monthBounds = viewModel.boundsForMonth(dateModel, cal, jdn)
        }

        while (monthBounds.top < vh) {
            monthStamp.jdn = jdn
            monthStamp.stamp(canvas)

            jdn = cal.nextMonth(jdn)
            monthBounds = viewModel.boundsForMonth(dateModel, cal, jdn)
        }
    }
}

