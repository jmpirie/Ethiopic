package com.hundaol.ethiocal.logging;

import android.util.Log;

import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;

/**
 * Created by john.pirie on 2017-04-14.
 */

public class EthioTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        switch(priority) {
            case Log.WARN:
                FirebaseCrash.logcat(priority, tag, message);
                FirebaseCrash.report(t != null ? t : new Exception(String.format("WARN:%s", message)));
            case Log.ERROR:
                FirebaseCrash.logcat(priority, tag, message);
                FirebaseCrash.report(t != null ? t : new Exception(String.format("ERROR:%s", message)));
        }
    }
}
