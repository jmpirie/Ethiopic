package com.hundaol.ethiopic.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by abinet on 6/6/17.
 */

class HeaderItemView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    @BindView(R.id.title)
    lateinit var headerTitle: TextView

    var viewModel: HeaderItemViewModel = HeaderItemViewModel()
        get() = field
        set(value) {
            field = value
            validateView()
        }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ButterKnife.bind(this)
    }

    fun validateView() {
        setBackgroundColor(viewModel.backgroundColor)
        headerTitle.setTextColor(viewModel.textColor)
        headerTitle.setText(viewModel.text)
        requestLayout()
    }
}
