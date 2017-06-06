package com.hundaol.ethiopic.logging

import android.util.Log

import com.google.firebase.crash.FirebaseCrash

import timber.log.Timber

/**
 * Created by john.pirie on 2017-04-14.
 */

class EthioTree : Timber.Tree() {

    override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
        when (priority) {
            Log.WARN -> {
                FirebaseCrash.logcat(priority, tag, message)
                FirebaseCrash.report(t ?: Exception(String.format("WARN:%s", message)))
                FirebaseCrash.logcat(priority, tag, message)
                FirebaseCrash.report(t ?: Exception(String.format("ERROR:%s", message)))
            }
            Log.ERROR -> {
                FirebaseCrash.logcat(priority, tag, message)
                FirebaseCrash.report(t ?: Exception(String.format("ERROR:%s", message)))
            }
        }
    }
}
