package com.hundaol.ethiopic.modules

import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager

import com.google.firebase.analytics.FirebaseAnalytics
import com.hundaol.ethiopic.App

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

import android.content.Context.WINDOW_SERVICE

/**
 * Created by john.pirie on 2017-04-14.
 */
@Module
class AppModule(private val app: App) {

    @Singleton
    @Provides
    fun provideApp(): App {
        return app
    }

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(app: App): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(app)
    }

    @Singleton
    @Provides
    fun provideDisplayMetrics(app: App): DisplayMetrics {
        return app.resources.displayMetrics
    }

    @Singleton
    @Provides
    fun provideDisplay(app: App): Display {
        return (app.getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
    }
}
