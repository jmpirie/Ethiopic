package com.hundaol.ethiopic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

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

    private ICal cal;
    private int cellWidth;
    private int viewWidth;
    private int viewHeight;

    private float jdv;

    private int offset;
    private Paint offsetPaint;
    private Rect offsetRect;

    @Inject
    DisplayMetrics displayMetrics;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        App.getAppComponent().inject(this);

        calendarViewAdapter = new CalendarViewAdapter(getContext());
        setCal(GregorianCal.INSTANCE);
        setJdv(GregorianCal.INSTANCE.today());

        offsetPaint = new Paint();
        offsetPaint.setColor(Color.argb(54, 0, 0, 0));
        offsetPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        offsetPaint.setStrokeWidth(dpToPx(2));

        offsetRect = new Rect();
    }

    private float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public void setCal(ICal cal) {
        this.cal = cal;
        calendarViewAdapter.setCal(cal);
    }

    public void setJdv(float jdv) {
        this.jdv = jdv;
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        viewWidth = r - l;
        viewHeight = b - t;

        cellWidth = viewWidth / 8;
        calendarViewAdapter.setCellWidth(cellWidth);

        offset = 3 * cellWidth;
        offsetRect.set(0, offset, viewWidth, offset + cellWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int jdn = (int) jdv;

        int weekNumber = cal.getWeekNumber(jdn);
        int firstDayOfWeek = cal.getFirstDayOfWeek(jdn);
        int weeksInView = (viewHeight / cellWidth) + 2;
        int daysInView = weeksInView * 7;

        canvas.translate(0, -weekNumber * cellWidth);
        canvas.translate(0, 3 * cellWidth);
        canvas.translate(0, -(jdv - firstDayOfWeek) * cellWidth / 7.0f);

        for (int d = firstDayOfWeek - 21, D = d + daysInView; d<D; d++) {
            stampJdn(canvas, d);
        }

        canvas.translate(0, (jdv - firstDayOfWeek) * cellWidth / 7.0f);
        canvas.translate(0, -3 * cellWidth);
        canvas.translate(0, weekNumber * cellWidth);

        canvas.drawRect(offsetRect, offsetPaint);
    }

    public void stampJdn(Canvas canvas, int jdn) {
        int dayOfWeek = cal.getDayOfWeek(jdn);
        int weekNumber = cal.getWeekNumber(jdn);

        float x = dayOfWeek * cellWidth;
        float y = weekNumber * cellWidth;

        canvas.translate(x, y);

        View dayView = calendarViewAdapter.getDayView(jdn);
        dayView.draw(canvas);

        if (jdn == (int)jdv) {
            canvas.drawCircle(cellWidth / 2.0f, cellWidth / 2.0f, cellWidth / 2.0f, offsetPaint);
        }

        canvas.translate(-x, -y);

        if (cal.getDay(jdn) == 1) {
            View labelView = calendarViewAdapter.getLabelView(jdn);

            canvas.translate(8.0f * cellWidth, y);
            canvas.rotate(90.0f);
            labelView.draw(canvas);
            canvas.rotate(-90.0f);
            canvas.translate(-8.0f * cellWidth, -y);
        }

    }

}

