package com.hundaol.ethiopic.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.App;
import com.hundaol.ethiopic.domain.DateModel;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

import static com.hundaol.ethiopic.domain.DateModel.uniqueJdnFilter;

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

    @Inject
    DateModel dateModel;

    private DateViewModel viewModel;

    private final CompositeDisposable disposables = new CompositeDisposable();

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
        App.getAppComponent().inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        disposables.add(RxView.clicks(left).subscribe(v -> {
            dateModel.prevDays(1);
        }));

        disposables.add(RxView.clicks(right).subscribe(v -> {
            dateModel.nextDay(1);
        }));

        disposables.add(dateModel.getDate().filter(uniqueJdnFilter()).subscribe(jdv -> {
            viewModel.setJdn((int)jdv.floatValue());
            modelChanged();
        }));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposables.dispose();
    }

    public void setViewModel(DateViewModel viewModel) {
        this.viewModel = viewModel;
        modelChanged();
    }

    public DateViewModel getViewModel() {
        return viewModel;
    }

    void modelChanged() {
        month.setText(viewModel.getMonth());
        day.setText(viewModel.getDay());
        year.setText(viewModel.getYear());
    }
}
