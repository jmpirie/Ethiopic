package com.hundaol.ethiopic.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.cal.GregorianCal;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john.pirie on 2017-04-28.
 */

public class DateView extends LinearLayout {

    @BindView(R.id.month)
    TextView month;

    @BindView(R.id.day)
    TextView day;

    @BindView(R.id.year)
    TextView year;

    @BindView(R.id.left)
    View left;

    @BindView(R.id.right)
    View right;

    private DateViewModel viewModel;

    public DateView(@NonNull Context context) {
        this(context, null);
    }

    public DateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.viewModel = new DateViewModel(GregorianCal.INSTANCE);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        modelChanged();

        left.setOnClickListener(v -> {
            viewModel.decr();
        });

        right.setOnClickListener(v -> {
            viewModel.incr();
        });
    }

    ViewModelChangeListener<DateViewModel> modelChangeListener = m -> modelChanged();

    public DateViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(DateViewModel viewModel) {
        if (this.viewModel != null) {
            this.viewModel.valueChangeEvent.remove(modelChangeListener);
        }
        this.viewModel = viewModel;
        this.viewModel.valueChangeEvent.add(modelChangeListener);
        modelChanged();
    }

    void modelChanged() {
        month.setText(viewModel.getMonth());
        day.setText(viewModel.getDay());
        year.setText(viewModel.getYear());
    }
}
