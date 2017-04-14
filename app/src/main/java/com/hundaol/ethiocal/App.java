package com.hundaol.ethiocal;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

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

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        getAppComponent().inject(this);
    }
}
