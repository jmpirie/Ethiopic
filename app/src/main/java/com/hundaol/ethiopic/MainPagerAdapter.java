package com.hundaol.ethiopic;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.cal.EthiopicCal;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.hundaol.ethiopic.views.CalendarView;
import com.hundaol.ethiopic.views.CalendarViewGestureDetector;
import com.hundaol.ethiopic.views.CalendarViewModel;
import com.hundaol.ethiopic.views.DateView;
import com.hundaol.ethiopic.views.DateViewModel;

/**
 * Created by john.pirie on 2017-04-29.
 */
public class MainPagerAdapter extends PagerAdapter {

    private final Context context;
    private final Display display;
    private final DateModel dateModel;

    private float jdv;
    private final Object[] viewModels = new Object[]{
            new DateViewModel(GregorianCal.INSTANCE),
            new CalendarViewModel(GregorianCal.INSTANCE),
            new CalendarViewModel(EthiopicCal.INSTANCE),
            new DateViewModel(EthiopicCal.INSTANCE)
    };

    public MainPagerAdapter(Context context, Display display, DateModel dateModel) {
        this.context = context;
        this.display = display;
        this.dateModel = dateModel;
//        ((DateViewModel) viewModels[0]).valueChangeEvent.add(m -> setJdv(m.getJdn()));
//        ((CalendarViewModel) viewModels[1]).valueChangeEvent.add(m -> setJdv(m.getJdv()));
//        ((CalendarViewModel) viewModels[2]).valueChangeEvent.add(m -> setJdv(m.getJdv()));
//        ((DateViewModel) viewModels[3]).valueChangeEvent.add(m -> setJdv(m.getJdn()));
    }

    public void setJdv(float jdv) {
        this.jdv = jdv;
        ((DateViewModel) viewModels[0]).setJdn((int) jdv);
        ((CalendarViewModel) viewModels[1]).setJdv(jdv);
        ((CalendarViewModel) viewModels[2]).setJdv(jdv);
        ((DateViewModel) viewModels[3]).setJdn((int) jdv);
    }

    @Override
    public float getPageWidth(int position) {
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return height >= width ? 1.0f : 0.5f;
    }

    @Override
    public int getCount() {
        return viewModels.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (position == 0 || position == 3) {
            DateViewModel viewModel = (DateViewModel) viewModels[position];
            viewModel.setJdn((int) jdv);
            DateView view = (DateView) LayoutInflater.from(context).inflate(R.layout.layout_date, null, false);
            view.setViewModel(viewModel);
            container.addView(view);
            return view;
        } else {
            CalendarViewModel viewModel = (CalendarViewModel) viewModels[position];
            viewModel.setJdv(jdv);
            CalendarView view = (CalendarView) LayoutInflater.from(context).inflate(R.layout.layout_calendar, null, false);
            new CalendarViewGestureDetector(view, dateModel);
            view.setViewModel(viewModel);
            container.addView(view);
            return view;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
