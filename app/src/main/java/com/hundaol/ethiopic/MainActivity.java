package com.hundaol.ethiopic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hundaol.ethiocal.R;
import com.hundaol.ethiopic.cal.EthiopicCal;
import com.hundaol.ethiopic.cal.GregorianCal;
import com.hundaol.ethiopic.views.CalendarView;
import com.hundaol.ethiopic.views.CalendarViewModel;
import com.hundaol.ethiopic.views.DateView;
import com.hundaol.ethiopic.views.DateViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    MainPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pagerAdapter = new MainPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        pagerAdapter.setJdv(GregorianCal.INSTANCE.today());
    }

    public static class MainPagerAdapter extends PagerAdapter {

        private final Context context;
        private float jdv;
        private final Object[] viewModels = new Object[]{
                new DateViewModel(GregorianCal.INSTANCE),
                new CalendarViewModel(GregorianCal.INSTANCE),
                new CalendarViewModel(EthiopicCal.INSTANCE),
                new DateViewModel(GregorianCal.INSTANCE)
        };

        public MainPagerAdapter(Context context) {
            this.context = context;
        }

        public void setJdv(float jdv) {
            this.jdv = jdv;
            ((DateViewModel)viewModels[0]).setJdn((int) jdv);
            ((CalendarViewModel)viewModels[1]).setJdv(jdv);
            ((CalendarViewModel)viewModels[2]).setJdv(jdv);
            ((DateViewModel)viewModels[3]).setJdn((int) jdv);
        }

        @Override
        public float getPageWidth(int position) {
            return 1.0f;
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
                view.setViewModel(viewModel);
                container.addView(view);
                return view;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
