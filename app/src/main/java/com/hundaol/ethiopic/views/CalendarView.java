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

    private CalendarViewAdapter calendarViewAdapter;

    private int viewWidth;
    private int viewHeight;
    private float cellWidth;

    private final Paint offsetPaint;

    private CalendarViewModel viewModel;

    @Inject
    DisplayMetrics displayMetrics;

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        App.getAppComponent().inject(this);

        calendarViewAdapter = new CalendarViewAdapter(getContext());
        setViewModel(new CalendarViewModel(GregorianCal.INSTANCE));

        offsetPaint = new Paint();
        offsetPaint.setColor(ContextCompat.getColor(getContext(), R.color.black_a25));
        offsetPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        offsetPaint.setStrokeWidth(dpToPx(2));
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

    private float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public CalendarViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(CalendarViewModel viewModel) {
        if (this.viewModel != null) {
            this.viewModel.structureChangeEvent.remove(structureChangeListener);
        }
        this.viewModel = viewModel;
        this.calendarViewAdapter.setViewModel(viewModel);
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

        int jdn = viewModel.getJdn();

        jdn = viewModel.cal.firstOfMonth(jdn);

        while (true) {
            RectF dayBounds = viewModel.boundsFor(jdn);

            if (dayBounds.top > viewHeight) {
                break;
            }

            canvas.translate(0.0f, dayBounds.top);

            // day cell

            canvas.translate(dayBounds.left, 0.0f);

            View dayView = calendarViewAdapter.getDayView(jdn);
            dayView.draw(canvas);

            if (jdn == viewModel.getJdn()) {
                canvas.drawCircle(dayBounds.width() / 2.0f, dayBounds.width() / 2.0f, dayBounds.width() / 3.0f, offsetPaint);
            }

            canvas.translate(-dayBounds.left, 0.0f);

            // month cell

            if (viewModel.cal.getDay(jdn) == 1) {
                canvas.translate(viewModel.getCellWidth() * 8.0f, 0.0f);

                View labelView = calendarViewAdapter.getMonthView(jdn);

                canvas.rotate(90.0f);
                labelView.draw(canvas);
                canvas.rotate(-90.0f);

                canvas.translate(-viewModel.getCellWidth() * 8.0f, 0.0f);
            }

            canvas.translate(0.0f, -dayBounds.top);

            jdn++;
        }

        RectF offsetBounds = viewModel.boundsForOffset();
        canvas.drawRect(offsetBounds, offsetPaint);
    }
}

