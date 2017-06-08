package com.hundaol.ethiopic.views

import android.support.v7.widget.RecyclerView

import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel

/**
 * Created by abinet on 6/6/17.
 */

class HeaderItemViewHolder(private val view: HeaderItemView) : RecyclerView.ViewHolder(view) {

    fun setViewModel(viewModel: HeaderItemViewModel) {
        view.viewModel = viewModel
    }
}
