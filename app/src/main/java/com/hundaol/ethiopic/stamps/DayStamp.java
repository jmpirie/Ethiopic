package com.hundaol.ethiopic.stamps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.views.CalendarViewModel;

/**
 * Created by john.pirie on 2017-04-30.
 */

public class DayStamp {

    private final Context context;
    private final CalendarViewModel viewModel;
    private final RectF bounds = new RectF();

    public final Paint backPaint;
    public final Paint forePaint;
    public final Paint todayOverlayPaint;
    public final Paint currentOverlayPaint;
    public final Paint weekendOverlayPaint;
    public final TextPaint textPaint;

    public int jdn;

    public DayStamp(Context context, CalendarViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;

        backPaint = new Paint();
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(context.getResources().getColor(R.color.day_background));

        forePaint = new Paint();
        forePaint.setStyle(Paint.Style.STROKE);
        forePaint.setColor(context.getResources().getColor(R.color.day_foreground));
        forePaint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.day_border_width));

        todayOverlayPaint = new Paint();
        todayOverlayPaint.setStyle(Paint.Style.FILL);
        todayOverlayPaint.setColor(context.getResources().getColor(R.color.day_background_today_overlay));

        currentOverlayPaint = new Paint();
        currentOverlayPaint.setStyle(Paint.Style.FILL);
        currentOverlayPaint.setColor(context.getResources().getColor(R.color.day_background_current_overlay));

        weekendOverlayPaint = new Paint();
        weekendOverlayPaint.setStyle(Paint.Style.FILL);
        weekendOverlayPaint.setColor(context.getResources().getColor(R.color.day_background_weekend_overlay));

        textPaint = new TextPaint();
        textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.day_text_size));
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(context.getResources().getColor(R.color.day_text));
    }

    public void setJdn(int jdn) {
        this.jdn = jdn;
        bounds.set(viewModel.boundsForDay(jdn));
    }

    public void stamp(Canvas canvas) {
        canvas.drawRect(bounds, backPaint);
        if (viewModel.isToday(jdn)) {
            canvas.drawRect(bounds, todayOverlayPaint);
        } else if (viewModel.isCurrent(jdn)) {
            canvas.drawRect(bounds, currentOverlayPaint);
        } else if (viewModel.isWeekend(jdn)) {
            canvas.drawRect(bounds, weekendOverlayPaint);
        }

        canvas.drawRect(bounds, forePaint);

        String s = Integer.toString(viewModel.getCal().getDay(jdn));
        float tw = textPaint.measureText(s);
        float th = textPaint.getTextSize();
        canvas.drawText(s, bounds.left + (bounds.width() - tw) / 2.0f, bounds.top + (bounds.height() + th) / 2.0f, textPaint);
    }
}
