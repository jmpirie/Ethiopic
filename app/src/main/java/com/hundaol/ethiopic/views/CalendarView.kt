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

    var viewModel = CalendarViewModel()
        get() = field
        set(value) {
            field = value
            invalidate()
        }

    var dateModel = DateModel.default
        get() = field
        set(value) {
            field = value
            invalidate()
        }

    var colorModel = ColorModel.default
        get() = field
        set(value) {
            field = value
            invalidate()
        }

    var cal = GregorianCal.INSTANCE
        get() = field
        set(value) {
            field = value
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

        monthStamp.viewModel = viewModel
        monthStamp.colorModel = colorModel
        monthStamp.dateModel = dateModel
        monthStamp.cal = cal

        monthStamp.jdn = dateModel.jdn

        while (monthStamp.bounds.bottom > 0) {
             monthStamp.jdn = cal.prevMonth(monthStamp.jdn)
        }

        while (monthStamp.bounds.top < vh) {
            monthStamp.stamp(canvas)
            monthStamp.jdn = cal.nextMonth(monthStamp.jdn)
        }
    }
}

