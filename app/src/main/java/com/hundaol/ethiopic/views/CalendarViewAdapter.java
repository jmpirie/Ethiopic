package com.hundaol.ethiopic.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.cal.ICal;

/**
 * Created by jmpirie on 2017-04-14
 */
public class CalendarViewAdapter {

    private Context context;

    private ICal cal;
    private int cellWidth;

    private View dayView;
    private View[] labelViews;

    public CalendarViewAdapter(Context context) {
        this.context = context;
    }

    public ICal getCal() {
        return cal;
    }

    public void setCal(ICal cal) {
        if (cal != this.cal) {
            this.cal = cal;
            invalidate();
        }
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        if (cellWidth != this.cellWidth) {
            this.cellWidth = cellWidth;
            invalidate();
        }
    }

    void invalidate() {
        dayView = null;
        labelViews = new View[10];
    }

    public View getDayView(int jdn) {
        if (dayView == null) {
            dayView = View.inflate(context, R.layout.layout_day, null);
            dayView.measure(
                    View.MeasureSpec.makeMeasureSpec(cellWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(cellWidth, View.MeasureSpec.EXACTLY));
            dayView.layout(0, 0, cellWidth, cellWidth);
            dayView.forceLayout();
        }

        TextView label = ((TextView) dayView.findViewById(R.id.day));
        label.setText("" + cal.getDay(jdn));
        if (cal.getDayOfWeek(jdn) == 0 || cal.getDayOfWeek(jdn) == 6) {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.weekendBackground));
        } else {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.weekdayBackground));
        }
        return dayView;
    }

    public View getLabelView(int jdn) {
        int weeksInMonth = cal.getLastFullWeek(jdn) + 1;
        String monthName = new String(cal.getMonthName(jdn));
        int year = cal.getYear(jdn);

        View view = labelViews[weeksInMonth];
        if (view == null) {
            view = View.inflate(context, R.layout.layout_month, null);
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(weeksInMonth * cellWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(cellWidth, View.MeasureSpec.EXACTLY));
            view.layout(0, 0, weeksInMonth * cellWidth, cellWidth);
            view.forceLayout();
            labelViews[weeksInMonth] = view;
        }
        TextView label = ((TextView) view.findViewById(R.id.label));
        label.setText(monthName + ", " + year);
        return view;
    }
}
