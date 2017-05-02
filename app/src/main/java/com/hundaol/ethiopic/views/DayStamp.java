package com.hundaol.ethiopic.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

import com.hundaol.ethiocal.R;

import timber.log.Timber;

/**
 * Created by john.pirie on 2017-04-30.
 */

public class DayStamp {

    private final Context context;
    private final CalendarViewModel viewModel;
    private final RectF bounds = new RectF();

    public final Paint backPaint;
    public final Paint forePaint;
    public final TextPaint textPaint;

    public int jdn;

    public DayStamp(Context context, CalendarViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;

        backPaint = new Paint();
        backPaint.setStyle(Paint.Style.FILL);

        forePaint = new Paint();
        forePaint.setStyle(Paint.Style.STROKE);
        forePaint.setColor(context.getResources().getColor(R.color.day_foreground));
        forePaint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.day_border_width));

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
        backPaint.setColor(viewModel.getCal().isWeekday(jdn)
                ? context.getResources().getColor(R.color.day_background_weekday)
                : context.getResources().getColor(R.color.day_background_weekend));

        canvas.drawRect(bounds, backPaint);

        canvas.drawRect(bounds, forePaint);

        String s = Integer.toString(viewModel.getCal().getDay(jdn));
        float tw = textPaint.measureText(s);
        float th = textPaint.getTextSize();
        canvas.drawText(s, bounds.left + (bounds.width() - tw) / 2.0f, bounds.top + (bounds.height() + th) / 2.0f, textPaint);
    }
}
