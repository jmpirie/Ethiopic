package com.hundaol.ethiopic

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Display

import com.google.firebase.analytics.FirebaseAnalytics
import com.hundaol.ethiocal.R

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import com.hundaol.ethiopic.cal.GregorianCal
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    @Inject
    lateinit var display: Display

    @BindView(R.id.view_pager)
    lateinit var viewPager: ViewPager

    @BindView(R.id.today)
    lateinit var todayButton: FloatingActionButton

    lateinit var pagerAdapter: PagerAdapter

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        if (!hasPermissionReadCalendar()) {
            requestPermissionReadCalendar()
        }

        pagerAdapter = MainPagerAdapter(this, display)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 1
    }

    override fun onResume() {
        super.onResume()
        disposables.add(RxView.clicks(todayButton).subscribe(
                { v ->
                    App.setJdv(GregorianCal.INSTANCE.today().toFloat(), 1000L);
                },
                { error ->
                    Timber.w(error, "error observed on todayButton clicks subscription")
                }
        ))
    }

    override fun onPause() {
        super.onPause()
        disposables.dispose()
    }

    fun requestPermissionReadCalendar(): Unit {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALENDAR), 1)
    }

    fun hasPermissionReadCalendar(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED
    }
}

