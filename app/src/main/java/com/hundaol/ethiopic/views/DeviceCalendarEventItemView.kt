package com.hundaol.ethiopic.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.viewmodels.DeviceCalendarEventViewModel

import org.joda.time.DateTime
import org.joda.time.DateTimeZone

import java.text.SimpleDateFormat

import butterknife.BindView
import butterknife.ButterKnife
import com.hundaol.ethiopic.domain.DeviceCalendar

/**
 * Created by abinet on 6/6/17.
 */

class DeviceCalendarEventItemView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    @BindView(R.id.indicator)
    lateinit var indicatorImage: ImageView

    @BindView(R.id.title)
    lateinit var title: TextView

    @BindView(R.id.time)
    lateinit var time: TextView

    internal var viewModel: DeviceCalendarEventViewModel = DeviceCalendarEventViewModel(context, DeviceCalendar())

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)
    }

    fun bindTo(viewModel: DeviceCalendarEventViewModel) {
        this.viewModel = viewModel
        validateView()
        requestLayout()
    }

    private fun validateView() {
        if (viewModel == null) {
            return
        }

        indicatorImage.background = viewModel.backgroundIndicator
        title.setText(viewModel.deviceCalendar.title)

        if (!viewModel.deviceCalendar.isAllDayEvent) {
            time.visibility = View.VISIBLE
            val dateFormat = SimpleDateFormat("hh:mm a")
            time.text = dateFormat.format(viewModel!!.deviceCalendar.start.toDate()) + " - " + dateFormat.format(viewModel!!.deviceCalendar.end.toDate())
        } else {
            time.visibility = View.GONE
        }
    }
}
