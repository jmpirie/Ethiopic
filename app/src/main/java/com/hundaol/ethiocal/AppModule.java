package com.hundaol.ethiocal;

import android.content.Context;

import dagger.Module;

/**
 * Created by john.pirie on 2017-04-14.
 */
@Module
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }
}
