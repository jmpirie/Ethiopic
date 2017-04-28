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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    CalendarPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        pagerAdapter = new CalendarPagerAdapter(this);
        pagerAdapter.setJdv(GregorianCal.INSTANCE.today());

        viewPager.setAdapter(pagerAdapter);
    }

    public static class CalendarPagerAdapter extends PagerAdapter {

        private final Context context;
        private final CalendarViewModel[] viewModels = new CalendarViewModel[]{
                new CalendarViewModel(GregorianCal.INSTANCE),
                new CalendarViewModel(EthiopicCal.INSTANCE)
        };

        public CalendarPagerAdapter(Context context) {
            this.context = context;
        }

        public void setJdv(float jdv) {
            for (CalendarViewModel viewModel : viewModels) {
                viewModel.setJdv(jdv);
            }
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
            CalendarViewModel viewModel = viewModels[position];
            CalendarView view = new CalendarView(context, null);
            view.setViewModel(viewModel);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((CalendarView)object);
        }
    }
}
