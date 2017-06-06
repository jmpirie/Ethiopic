package com.hundaol.ethiopic

import android.app.Application

import com.google.firebase.analytics.FirebaseAnalytics
import com.hundaol.ethiocal.BuildConfig
import com.hundaol.ethiopic.logging.EthioTree
import com.hundaol.ethiopic.modules.AppComponent
import com.hundaol.ethiopic.modules.AppModule
import com.hundaol.ethiopic.modules.DaggerAppComponent

import javax.inject.Inject

import timber.log.Timber

/**
 * Created by john.pirie on 2017-04-14.
 */

class App : Application() {

    @Inject
    lateinit internal var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate() {
        super.onCreate()

        Timber.plant(if (BuildConfig.DEBUG)
            Timber.DebugTree()
        else
            EthioTree())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        appComponent.inject(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
