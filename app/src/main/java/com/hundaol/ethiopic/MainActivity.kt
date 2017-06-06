package com.hundaol.ethiopic

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Display

import com.google.firebase.analytics.FirebaseAnalytics
import com.hundaol.ethiocal.R

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var display: Display

    @BindView(R.id.view_pager)
    lateinit var viewPager: ViewPager

    @BindView(R.id.today)
    lateinit var today: FloatingActionButton

    lateinit var pagerAdapter : PagerAdapter

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        pagerAdapter = MainPagerAdapter(this, display)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 1
    }

    override fun onResume() {
        super.onResume()

        //        disposables.add(RxView.clicks(today).subscribe(v -> {
        //            dateModel.setJdv(GregorianCal.INSTANCE.today());
        //        }));
    }

    override fun onPause() {
        super.onPause()
        disposables.dispose()
    }
}
