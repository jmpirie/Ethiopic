package com.hundaol.ethiopic.views;

import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;

import com.hundaol.ethiopic.cal.ICal;

/**
 * Created by john.pirie on 2017-04-27.
 */
public class CalendarViewModel {

    private final RectF bounds = new RectF();

    private ICal cal;
    private float cellWidth;
    private float offset;
    private float jdv;

    public final ViewModelChangeEvent<CalendarViewModel> structureChangeEvent = new ViewModelChangeEvent<>();
    public final ViewModelChangeEvent<CalendarViewModel> valueChangeEvent = new ViewModelChangeEvent<>();

    public CalendarViewModel(ICal cal) {
        this.cal = cal;
    }

    public ICal getCal() {
        return cal;
    }

    public void setCal(ICal cal) {
        if (this.cal != cal) {
            this.cal = cal;
            structureChangeEvent.modelChanged(this);
        }
    }

    public float getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(float cellWidth) {
        if (this.cellWidth != cellWidth) {
            this.cellWidth = cellWidth;
            structureChangeEvent.modelChanged(this);
        }
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        if (this.offset != offset) {
            this.offset = offset;
            structureChangeEvent.modelChanged(this);
        }
    }

    public float getJdv() {
        return jdv;
    }

    public void setJdv(float jdv) {
        if (this.jdv != jdv) {
            this.jdv = jdv;
            valueChangeEvent.modelChanged(this);
        }
    }

    public int getJdn() {
        return (int) jdv;
    }

    public RectF boundsForDay(int jdn) {
        bounds.left = cellWidth * cal.getDayOfWeek(jdn);
        bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - (jdv * cellWidth / 7.0f); // todo: refactor to distance from float overflow
        bounds.right = bounds.left + cellWidth;
        bounds.bottom = bounds.top + cellWidth;
        return bounds;
    }

    public RectF boundsForLabel(int jdn) {
        jdn = cal.firstOfMonth(jdn);
        bounds.left = cellWidth * 7;
        bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - (jdv * cellWidth / 7.0f);
        bounds.right = bounds.left + cellWidth;
        bounds.bottom = bounds.top + (cellWidth * (cal.getLastFullWeek(jdn) + 1));
        return bounds;
    }

    public RectF boundsForOffset() {
        bounds.left = 0.0f;
        bounds.top = offset;
        bounds.right = 8 * cellWidth;
        bounds.bottom = bounds.top + cellWidth;
        return bounds;
    }

    public RectF boundsForMonth(int jdn) {
        jdn = cal.firstOfMonth(jdn);
        bounds.left = 0;
        bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - (jdv * cellWidth / 7.0f);
        bounds.right = 8 * cellWidth;
        bounds.bottom = bounds.top + (cellWidth * (cal.getWeeksInMonth(jdn)));
        return bounds;
    }

    public Path pathForMonth(int jdn) {
        int d0 = cal.firstOfMonth(jdn);
        int d1 = cal.getLastDayOfWeek(jdn);
        int d2 = cal.firstOfWeek(d0 + 7);

        int d5 = cal.lastOfMonth(jdn);
        int d4 = cal.getFirstDayOfWeek(d5);
        int d3 = cal.getLastDayOfWeek(d5-7);

        Path path = new Path();
        boundsForDay(d0);
        path.moveTo(bounds.left, bounds.bottom);
        path.lineTo(bounds.left, bounds.top);
        boundsForDay(d1);
        path.lineTo(bounds.right, bounds.top);
        boundsForDay(d3);
        path.lineTo(bounds.right, bounds.bottom);
        boundsForDay(d5);
        path.lineTo(bounds.right, bounds.top);
        path.lineTo(bounds.right, bounds.bottom);
        boundsForDay(d4);
        path.lineTo(bounds.left, bounds.bottom);
        boundsForDay(d2);
        path.lineTo(bounds.left, bounds.top);
        path.lineTo(bounds.right, bounds.top);

        return path;
    }

    public float jdvFor(float y) {
        return (-1 + (y - offset) * cellWidth / 7.0f) + jdv;
    }

    public int jdnFor(float x, float y) {
        return -1 + (int) (((y - offset) / cellWidth) + jdv) + (int) (x / cellWidth);
    }
}
