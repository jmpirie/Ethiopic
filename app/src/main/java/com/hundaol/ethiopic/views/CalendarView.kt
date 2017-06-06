package com.hundaol.ethiopic.views

import android.content.Context
import android.util.AttributeSet
import android.view.View

import com.hundaol.ethiopic.App

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by jmpirie on 2017-04-14
 */
class CalendarView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private val disposables = CompositeDisposable()

    init {

        App.appComponent.inject(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //        disposables.add(dateModel.getDate().filter(uniqueJdvFilter()).subscribe(jdv -> {
        //            viewModel.setJdv(jdv);
        //            invalidate();
        //        }));
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        disposables.dispose()
    }

    //    public CalendarViewModel getViewModel() {
    //        return viewModel;
    //    }
    //
    //    public void setViewModel(CalendarViewModel viewModel) {
    //        monthStamp = new MonthStamp(getContext(), viewModel);
    //        this.viewModel = viewModel;
    //    }
    //
    //    @Override
    //    public void onLayout(boolean changed, int l, int t, int r, int b) {
    //        super.onLayout(changed, l, t, r, b);
    //
    //        viewWidth = r - l;
    //        viewHeight = b - t;
    //
    //        cellWidth = viewWidth / 8.0f;
    //        viewModel.setCellWidth(cellWidth);
    //        viewModel.setOffset(3 * cellWidth);
    //    }
    //
    //    @Override
    //    public void draw(Canvas canvas) {
    //        super.draw(canvas);
    //
    //        final ICal cal = viewModel.getCal();
    //        int jdn = viewModel.getJdn();
    //
    //        RectF monthBounds = viewModel.boundsForMonth(jdn);
    //        while (monthBounds.bottom > 0) {
    //            jdn = cal.prevMonth(jdn);
    //            monthBounds = viewModel.boundsForMonth(jdn);
    //        }
    //        while (monthBounds.top < viewHeight) {
    //            monthStamp.setJdn(jdn);
    //            monthStamp.stamp(canvas);
    //            jdn = cal.nextMonth(jdn);
    //            monthBounds = viewModel.boundsForMonth(jdn);
    //        }
    //    }
}

