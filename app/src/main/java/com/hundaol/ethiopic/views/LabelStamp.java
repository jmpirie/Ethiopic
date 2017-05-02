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

public class LabelStamp {

    private final Context context;
    private final CalendarViewModel viewModel;
    private final RectF bounds = new RectF();

    public final Paint backPaint;
    public final Paint forePaint;
    public final TextPaint textPaint;

    public int jdn;

    public LabelStamp(Context context, CalendarViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;

        backPaint = new Paint();
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setColor(context.getResources().getColor(R.color.label_background));

        forePaint = new Paint();
        forePaint.setStyle(Paint.Style.STROKE);
        forePaint.setColor(context.getResources().getColor(R.color.label_foreground));
        forePaint.setStrokeWidth(context.getResources().getDimensionPixelSize(R.dimen.label_border_width));

        textPaint = new TextPaint();
        textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.label_text_size));
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(context.getResources().getColor(R.color.label_text));
    }

    public void setJdn(int jdn) {
        this.jdn = jdn;
        bounds.set(viewModel.boundsForLabel(jdn));
    }

    public void stamp(Canvas canvas) {
        canvas.drawRect(bounds, backPaint);

        canvas.drawRect(bounds, forePaint);

        String s =  new String(viewModel.getCal().getMonthName(jdn)) + ", " + viewModel.getCal().getYear(jdn);
        float tw = textPaint.measureText(s);
        float th = textPaint.getTextSize();
        canvas.rotate(90, bounds.centerX(), bounds.centerY());
        canvas.drawText(s, bounds.left + (bounds.width() - tw) / 2.0f, bounds.top + (bounds.height() + th) / 2.0f, textPaint);
        canvas.rotate(-90, bounds.centerX(), bounds.centerY());
    }
}
