package com.hundaol.ethiopic.views;

import android.support.v7.widget.RecyclerView;

import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel;

/**
 * Created by abinet on 6/6/17.
 */

public class HeaderItemViewHolder extends RecyclerView.ViewHolder {

    private HeaderItemView view;

    public HeaderItemViewHolder(HeaderItemView view) {
        super(view);
        this.view = view;
    }

    public void setViewModel(HeaderItemViewModel viewModel) {
        view.bindTo(viewModel);
    }
}
