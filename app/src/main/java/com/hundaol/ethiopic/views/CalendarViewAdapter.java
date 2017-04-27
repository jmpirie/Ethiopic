package com.hundaol.ethiopic.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.hundaol.ethiocal.R;

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
        if (dayView == null) {
            dayView = View.inflate(context, R.layout.layout_day, null);
            dayView.measure(
                    View.MeasureSpec.makeMeasureSpec((int) viewModel.cellWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec((int) viewModel.cellWidth, View.MeasureSpec.EXACTLY));
            dayView.layout(0, 0, dayView.getMeasuredWidth(), dayView.getMeasuredHeight());
            dayView.forceLayout();
        }

        TextView label = ((TextView) dayView.findViewById(R.id.day));
        label.setText("" + viewModel.cal.getDay(jdn));
        //label.setText("" + jdn);
        if (viewModel.cal.getDayOfWeek(jdn) == 0 || viewModel.cal.getDayOfWeek(jdn) == 6) {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.weekendBackground));
        } else {
            label.setBackgroundColor(ContextCompat.getColor(context, R.color.weekdayBackground));
        }
        return dayView;
    }

    public View getMonthView(int jdn) {
        int weeksInMonth = viewModel.cal.getLastFullWeek(jdn) + 1;
        String monthName = new String(viewModel.cal.getMonthName(jdn));
        int year = viewModel.cal.getYear(jdn);

        View view = labelViews[weeksInMonth];
        if (view == null) {
            view = View.inflate(context, R.layout.layout_month, null);
            view.measure(
                    View.MeasureSpec.makeMeasureSpec((int) (weeksInMonth * viewModel.cellWidth), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec((int) viewModel.cellWidth, View.MeasureSpec.EXACTLY));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.forceLayout();
            labelViews[weeksInMonth] = view;
        }
        TextView label = ((TextView) view.findViewById(R.id.label));
        label.setText(monthName + ", " + year);
        return view;
    }
}
