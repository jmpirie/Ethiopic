package com.hundaol.ethiocal.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.hundaol.ethiocal.cal.ICal;
import com.hundaol.ethiocal.cal.GregorianCal;

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

    private float jdn;
    private int offset;

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        calendarViewAdapter = new CalendarViewAdapter(getContext());
        setCal(GregorianCal.INSTANCE);
        setJdn(GregorianCal.INSTANCE.today());
        setOffset(200);
    }

    public void setCal(ICal cal) {
        this.cal = cal;
        calendarViewAdapter.setCal(cal);
    }

    public void setJdn(float jdn) {
        this.jdn = jdn;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        viewWidth = r - l;
        viewHeight = b - t;

        cellWidth = viewWidth / 8;
        calendarViewAdapter.setCellWidth(cellWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int firstOfMonth = cal.firstOfMonth((int) jdn);
        int firstOfPrevMonth = cal.prevMonth(firstOfMonth);

        int anchorJdn = firstOfPrevMonth;
        int anchorWeek = cal.getWeekNumber(anchorJdn);
        float anchorOffset = -(jdn - anchorJdn) / (7.0f * cellWidth);

        for (int jdn=anchorJdn; true; jdn++) {
            float dx = cal.getDayOfWeek(jdn) * cellWidth;
            float dy = anchorOffset + (cal.getWeekNumber(jdn) - anchorWeek) * cellWidth;
            View dayView = calendarViewAdapter.getDayView(jdn);

            canvas.translate(dx, dy);
            dayView.draw(canvas);
            canvas.translate(-dx, -dy);

            if (cal.getDay(jdn) == 1) {
                dx = 7 * cellWidth;
                View labelView = calendarViewAdapter.getLabelView(jdn);

                canvas.translate(dx+ cellWidth, dy);
                canvas.rotate(90.0f);
                labelView.draw(canvas);
                canvas.rotate(-90.0f);
                canvas.translate(-dx- cellWidth, -dy);
            }

            if (dy > viewHeight) {
                break;
            }
        }
    }
}

