package com.hundaol.ethiocal;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by john.pirie on 2017-04-14.
 */

public class App extends Application {

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }
}
