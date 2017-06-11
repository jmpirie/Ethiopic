package com.hundaol.ethiopic.views.date

import android.support.v7.widget.RecyclerView

import com.hundaol.ethiopic.viewmodels.DiaryEntryItemViewModel

/**
 * Created by abinet on 6/6/17.
 */

class DiaryEntryItemViewHolder(private val view: DiaryEntryItemView) : RecyclerView.ViewHolder(view) {

    fun setViewModel(viewModel: DiaryEntryItemViewModel) {
        view.bindTo(viewModel)
    }
}
