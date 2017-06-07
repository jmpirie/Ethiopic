/**
 * Created by john.pirie on 2017-06-07.
 */
package com.hundaol.ethiopic.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.App
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.cal.GregorianCal
import com.jakewharton.rxbinding2.view.RxView

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import com.hundaol.ethiopic.cal.ICal
import com.hundaol.ethiopic.domain.ColorModel
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

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

    @BindView(R.id.day)
    lateinit var day: TextView

    @BindView(R.id.year)
    lateinit var year: TextView

    @BindView(R.id.left)
    lateinit var left: View

    @BindView(R.id.right)
    lateinit var right: View

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

    var cal : ICal = GregorianCal.INSTANCE
        get() = field
        set(value) {
            field = value
            validate()
        }

    private val disposables = CompositeDisposable()

    init {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        App.appComponent.inject(this)
        ButterKnife.bind(this)
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
        disposables.add(RxView.clicks(left).subscribe(
                { v ->
                    App.setJdv(dateModel.jdn - 1.0f)
                },
                { error ->
                    Timber.w(error, "error observed on todayButton clicks subscription")
                }))
        disposables.add(RxView.clicks(right).subscribe(
                { v ->
                    App.setJdv(dateModel.jdn + 1.0f)
                },
                { error ->
                    Timber.w(error, "error observed on todayButton clicks subscription")
                }))
    }

    private fun validate() {
        month.setText(String(cal.getMonthName(dateModel.jdn)))
        day.setText(cal.getDay(dateModel.jdn).toString())
        year.setText(cal.getYear(dateModel.jdn).toString())

        imageOverlay.setBackgroundColor(colorModel.dateImageOverlay(cal, dateModel.jdn))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.dispose()
    }
}
