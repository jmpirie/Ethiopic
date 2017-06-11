package com.hundaol.ethiopic.views.date

import android.support.v7.widget.RecyclerView

import com.hundaol.ethiopic.viewmodels.DiaryHeaderItemViewModel

/**
 * Created by abinet on 6/6/17.
 */

class DiaryHeaderItemViewHolder(private val viewDiary: DiaryHeaderItemView) : RecyclerView.ViewHolder(viewDiary) {

    fun setViewModel(viewModelDiary: DiaryHeaderItemViewModel) {
        viewDiary.viewModelDiary = viewModelDiary
    }
}
