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

    private float jdv;

    private final Paint offsetPaint;
//    private final Rect offsetRect;

    private final ViewModel viewModel;

    @Inject
    DisplayMetrics displayMetrics;

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        App.getAppComponent().inject(this);

        viewModel = new ViewModel(GregorianCal.INSTANCE);

        calendarViewAdapter = new CalendarViewAdapter(getContext());
        setCal(GregorianCal.INSTANCE);
        setJdv(GregorianCal.INSTANCE.today());
//        setJdv(3);

        offsetPaint = new Paint();
        offsetPaint.setColor(ContextCompat.getColor(getContext(), R.color.black_a25));
        offsetPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        offsetPaint.setStrokeWidth(dpToPx(2));

//        offsetRect = new Rect();

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setJdv(getJdv() + 100000);
//                invalidate();
//                handler.postDelayed(this, 1000);
//            }
//        }, 1000);
    }

    private float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public void setCal(ICal cal) {
        viewModel.setCal(cal);
        calendarViewAdapter.setCal(cal);
    }

    public void setJdv(float jdv) {
        this.jdv = jdv;
        viewModel.setJdv(jdv);
        validate();
        invalidate();
    }

    public float getJdv() {
        return jdv;
    }


    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        viewWidth = r - l;
        viewHeight = b - t;

        cellWidth = viewWidth / 8.0f;
        calendarViewAdapter.setCellWidth(cellWidth);
        viewModel.setCellWidth(cellWidth);

        float offset = 3 * cellWidth;
        viewModel.setOffset(offset);

        validate();
    }

    private void validate() {
        //jdvViewPort.setOffset(jdv * cellWidth / 7.0f);
        //offsetRect.set(0, offset, viewWidth, offset + cellWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int jdn = (int) jdv;

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

            if (jdn == (int) jdv) {
                canvas.drawCircle(dayBounds.width() / 2.0f, dayBounds.width() / 2.0f, dayBounds.width() / 2.0f, offsetPaint);
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

//        canvas.drawRect(offsetRect, offsetPaint);


    }

//    public void stampDay(Canvas canvas, int jdn) {
//        viewModel.setJdn(jdn);
//
//        RectF dayBounds = viewPort.getBounds();
//
//        canvas.translate(dayBounds.left, dayBounds.top);
//
//        View dayView = calendarViewAdapter.getDayView(jdn);
//        dayView.draw(canvas);
//
//        if (jdn == (int) jdv) {
//            canvas.drawCircle(dayBounds.width() / 2.0f, dayBounds.width() / 2.0f, dayBounds.width() / 2.0f, offsetPaint);
//        }
//
//        canvas.translate(-dayBounds.left, -dayBounds.top);
//
//        if (cal.getDay(jdn) == 1) {
//            View labelView = calendarViewAdapter.getLabelView(jdn);
//
//            canvas.translate(8.0f * cellWidth, y);
//            canvas.rotate(90.0f);
//            labelView.draw(canvas);
//            canvas.rotate(-90.0f);
//            canvas.translate(-8.0f * cellWidth, -y);
//        }
//    }

//    public void stampMonth(Canvas canvas, int jdn) {
//        int weekNumber = cal.getWeekNumber(jdn);
//
//        float x = 8.0f * cellWidth;
//        float y = weekNumber * cellWidth;
//
//        if (cal.getDay(jdn) == 1) {
//            View labelView = calendarViewAdapter.getLabelView(jdn);
//
//            canvas.translate(x, y);
//            canvas.rotate(90.0f);
//            labelView.draw(canvas);
//            canvas.rotate(-90.0f);
//            canvas.translate(-x, -y);
//        }
//    }

    public static class ViewModel {

        public ICal cal;
        public float cellWidth;
        public float offset;
        public final RectF bounds = new RectF();
        public float jdv;

        public ViewModel(ICal cal) {
            this.cal = cal;
        }

        public ICal getCal() {
            return cal;
        }

        public void setCal(ICal cal) {
            this.cal = cal;
        }

        public float getCellWidth() {
            return cellWidth;
        }

        public void setCellWidth(float cellWidth) {
            this.cellWidth = cellWidth;
        }

        public float getOffset() {
            return offset;
        }

        public void setOffset(float offset) {
            this.offset = offset;
        }

        public float getJdv() {
            return jdv;
        }

        public void setJdv(float jdv) {
            this.jdv = jdv;
        }

        public RectF boundsFor(int jdn) {
            bounds.left = cellWidth * cal.getDayOfWeek(jdn);
            bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - (jdv * cellWidth / 7.0f);
            bounds.right = bounds.left + cellWidth;
            bounds.bottom = bounds.top + cellWidth;
            return bounds;
        }

        public float jdvFor(float y) {
            return (-1 + (y - offset) * cellWidth / 7.0f) + jdv;
        }

        public int jdnFor(float x, float y) {
            return -1 + (int) (((y - offset) / cellWidth) + jdv) + (int) (x / cellWidth);
        }
    }
}

