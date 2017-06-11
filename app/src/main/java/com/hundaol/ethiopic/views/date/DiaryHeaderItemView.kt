package com.hundaol.ethiopic.views.date

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

import com.hundaol.ethiocal.R
import com.hundaol.ethiopic.viewmodels.DiaryHeaderItemViewModel

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by abinet on 6/6/17.
 */

class DiaryHeaderItemView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    @BindView(R.id.title)
    lateinit var headerTitle: TextView

    var viewModelDiary: DiaryHeaderItemViewModel = DiaryHeaderItemViewModel()
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
        setBackgroundColor(viewModelDiary.backgroundColor)
        headerTitle.setTextColor(viewModelDiary.textColor)
        headerTitle.setText(viewModelDiary.text)
        requestLayout()
    }
}
