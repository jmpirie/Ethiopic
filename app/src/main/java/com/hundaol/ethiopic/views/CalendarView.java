package com.hundaol.ethiopic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.App;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.hundaol.ethiopic.cal.ICal;

import javax.inject.Inject;

/**
 * Created by jmpirie on 2017-04-14
 */
public class CalendarView extends View {

    public static final int MIN = GregorianCal.INSTANCE.fromDate(1500, 1, 1);
    public static final int MAX = GregorianCal.INSTANCE.fromDate(2400, 12, 31);

    private int viewWidth;
    private int viewHeight;
    private float cellWidth;

    private final Paint offsetPaint;

    private CalendarViewModel viewModel;

    private MonthStamp monthStamp;

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        App.getAppComponent().inject(this);

        setViewModel(new CalendarViewModel(GregorianCal.INSTANCE));

        offsetPaint = new Paint();
        offsetPaint.setColor(ContextCompat.getColor(getContext(), R.color.black_a25));
        offsetPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        offsetPaint.setStrokeWidth(context.getResources().getDimensionPixelOffset(R.dimen.offset_border_width));
    }

    ViewModelChangeListener<CalendarViewModel> structureChangeListener = m -> {
        requestLayout();
        invalidate();
    };

    ViewModelChangeListener<CalendarViewModel> valueChangeListener = m -> {
        invalidate();
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        viewModel.structureChangeEvent.add(structureChangeListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewModel.structureChangeEvent.remove(structureChangeListener);
    }

    public CalendarViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(CalendarViewModel viewModel) {
        if (this.viewModel != null) {
            this.viewModel.structureChangeEvent.remove(structureChangeListener);
        }
        monthStamp = new MonthStamp(getContext(), viewModel);
        this.viewModel = viewModel;
        this.viewModel.structureChangeEvent.add(structureChangeListener);
        this.viewModel.valueChangeEvent.add(valueChangeListener);
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

