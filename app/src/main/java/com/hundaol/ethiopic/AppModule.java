package com.hundaol.ethiopic;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by john.pirie on 2017-04-14.
 */
@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    public App provideApp() {
        return app;
    }

    @Singleton
    @Provides
    public FirebaseAnalytics provideFirebaseAnalytics(App app) {
        return FirebaseAnalytics.getInstance(app);
    }

    @Singleton
    @Provides
    public DisplayMetrics provideDisplayMetrics(App app) {
        return app.getResources().getDisplayMetrics();
    }

    @Singleton
    @Provides
    public Display provideDisplay(App app) {
        return ((WindowManager) app.getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
    }
}
