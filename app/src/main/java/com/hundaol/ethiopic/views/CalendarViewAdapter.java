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
    private CalendarViewModel viewModel;

    private View dayView;
    private View[] labelViews;

    public CalendarViewAdapter(Context context) {
        this.context = context;
    }

    public void setViewModel(CalendarViewModel viewModel) {
        if (this.viewModel!=null) {
            this.viewModel.structureChangeEvent.remove(structureChangeListener);
        }
        this.viewModel = viewModel;
        if (this.viewModel!=null) {
            this.viewModel.structureChangeEvent.add(structureChangeListener);
        }
        invalidate();
    }

    ViewModelChangeListener<CalendarViewModel> structureChangeListener = (m) -> invalidate();

    void invalidate() {
        dayView = null;
        labelViews = new View[10];
    }

    public View getDayView(int jdn) {
        final ICal cal = viewModel.getCal();
        final float cellWidth = viewModel.getCellWidth();

        if (dayView == null) {
            dayView = View.inflate(context, R.layout.layout_day, null);
            dayView.measure(
                    View.MeasureSpec.makeMeasureSpec((int) cellWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec((int) cellWidth, View.MeasureSpec.EXACTLY));
            dayView.layout(0, 0, dayView.getMeasuredWidth(), dayView.getMeasuredHeight());
            dayView.forceLayout();
        }

        TextView label = ((TextView) dayView.findViewById(R.id.day));
        label.setText("" + cal.getDay(jdn));
        //label.setText("" + jdn);
        if (cal.getDayOfWeek(jdn) == 0 || cal.getDayOfWeek(jdn) == 6) {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.day_background_weekend));
        } else {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.day_background_weekday));
        }
        return dayView;
    }

    public View getMonthView(int jdn) {
        final ICal cal = viewModel.getCal();
        final float cellWidth = viewModel.getCellWidth();

        int weeksInMonth = cal.getLastFullWeek(jdn) + 1;
        String monthName = new String(cal.getMonthName(jdn));
        int year = cal.getYear(jdn);

        View view = labelViews[weeksInMonth];
        if (view == null) {
            view = View.inflate(context, R.layout.layout_month, null);
            view.measure(
                    View.MeasureSpec.makeMeasureSpec((int) (weeksInMonth * cellWidth), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec((int) cellWidth, View.MeasureSpec.EXACTLY));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.forceLayout();
            labelViews[weeksInMonth] = view;
        }
        TextView label = ((TextView) view.findViewById(R.id.label));
        label.setText(monthName + ", " + year);
        return view;
    }
}
