package com.hundaol.ethiopic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.viewmodels.HeaderItemViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by abinet on 6/6/17.
 */

public class HeaderItemView extends LinearLayout {

    @BindView(R.id.title)
    TextView headerTitle;

    private HeaderItemViewModel viewModel;

    public HeaderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(HeaderItemViewModel viewModel) {
        if (viewModel == null) {
            viewModel = new HeaderItemViewModel();
        }

        this.viewModel = viewModel;
        validateView();
    }

    public void validateView() {
        setBackgroundColor(viewModel.getBackgroundColor());
        headerTitle.setTextColor(viewModel.getTextColor());
        headerTitle.setText(viewModel.getText());

        requestLayout();
    }
}
