package com.hundaol.ethiopic;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.hundaol.ethiocal.BuildConfig;
import com.hundaol.ethiopic.logging.EthioTree;
import com.hundaol.ethiopic.modules.AppComponent;
import com.hundaol.ethiopic.modules.AppModule;
import com.hundaol.ethiopic.modules.DaggerAppComponent;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by john.pirie on 2017-04-14.
 */

public class App extends Application {

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(BuildConfig.DEBUG
                ? new Timber.DebugTree()
                : new EthioTree());

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        getAppComponent().inject(this);
    }
}
