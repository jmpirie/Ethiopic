package com.hundaol.ethiopic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hundaol.ethiopic.App;
import com.hundaol.ethiopic.domain.DateModel;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.hundaol.ethiopic.cal.ICal;
import com.hundaol.ethiopic.stamps.MonthStamp;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.hundaol.ethiopic.domain.DateModel.uniqueJdvFilter;

/**
 * Created by jmpirie on 2017-04-14
 */
public class CalendarView extends View {

    private int viewWidth;
    private int viewHeight;
    private float cellWidth;

    private CalendarViewModel viewModel;

    private MonthStamp monthStamp;

    @Inject
    DateModel dateModel;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        App.getAppComponent().inject(this);

        setViewModel(new CalendarViewModel(GregorianCal.INSTANCE));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        disposables.add(dateModel.getDate().filter(uniqueJdvFilter()).subscribe(jdv -> {
            viewModel.setJdv(jdv);
            invalidate();
        }));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposables.dispose();
    }

    public CalendarViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(CalendarViewModel viewModel) {
        monthStamp = new MonthStamp(getContext(), viewModel);
        this.viewModel = viewModel;
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        viewWidth = r - l;
        viewHeight = b - t;

        cellWidth = viewWidth / 8.0f;
        viewModel.setCellWidth(cellWidth);
        viewModel.setOffset(3 * cellWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        final ICal cal = viewModel.getCal();
        int jdn = viewModel.getJdn();

        RectF monthBounds = viewModel.boundsForMonth(jdn);
        while (monthBounds.bottom > 0) {
            jdn = cal.prevMonth(jdn);
            monthBounds = viewModel.boundsForMonth(jdn);
        }
        while (monthBounds.top < viewHeight) {
            monthStamp.setJdn(jdn);
            monthStamp.stamp(canvas);
            jdn = cal.nextMonth(jdn);
            monthBounds = viewModel.boundsForMonth(jdn);
        }
    }
}

