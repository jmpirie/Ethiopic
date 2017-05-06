package com.hundaol.ethiopic.stamps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.cal.ICal;
import com.hundaol.ethiopic.views.CalendarViewModel;

/**
 * Created by john.pirie on 2017-05-01.
 */

public class MonthStamp {
    private final Context context;
    private CalendarViewModel viewModel;
    private final DayStamp dayStamp;
    private final LabelStamp labelStamp;

    public int jdn;

    public final Paint forePaint;

    public MonthStamp(Context context, CalendarViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;

        dayStamp = new DayStamp(context, viewModel);
        labelStamp = new LabelStamp(context, viewModel);

        forePaint = new Paint();
        forePaint.setStyle(Paint.Style.STROKE);
        forePaint.setColor(context.getResources().getColor(R.color.month_foreground));
        forePaint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.month_border_width));
    }

    public void setJdn(int jdn) {
        this.jdn = jdn;
    }

    public void stamp(Canvas canvas) {
        int jdn = viewModel.getCal().firstOfMonth(this.jdn);

        ICal cal = viewModel.getCal();
        for (int i = 0; i < cal.getDaysInMonth(jdn); i++) {
            dayStamp.setJdn(jdn + i);
            dayStamp.stamp(canvas);
        }

        labelStamp.setJdn(jdn);
        labelStamp.stamp(canvas);

        Path monthPath = viewModel.pathForMonth(jdn);
        canvas.drawPath(monthPath, forePaint);
    }
}
