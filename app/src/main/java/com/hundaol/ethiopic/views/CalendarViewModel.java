package com.hundaol.ethiopic.views;

import android.graphics.RectF;

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

    public RectF boundsFor(int jdn) {
        bounds.left = cellWidth * cal.getDayOfWeek(jdn);
        bounds.top = cellWidth * cal.getWeekNumber(jdn) + offset - (jdv * cellWidth / 7.0f);
        bounds.right = bounds.left + cellWidth;
        bounds.bottom = bounds.top + cellWidth;
        return bounds;
    }

    public RectF boundsForOffset() {
        bounds.left = 0.0f;
        bounds.top = offset;
        bounds.right = 8 * cellWidth;
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
