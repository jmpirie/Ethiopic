package com.hundaol.ethiocal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);

        setContentView(R.layout.activity_main);
    }
}
