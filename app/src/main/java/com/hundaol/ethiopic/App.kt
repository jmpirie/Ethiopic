package com.hundaol.ethiopic

import android.animation.ValueAnimator
import android.app.Application
import android.graphics.Color
import android.view.animation.AccelerateDecelerateInterpolator

import com.google.firebase.analytics.FirebaseAnalytics
import com.hundaol.ethiocal.BuildConfig
import com.hundaol.ethiopic.domain.ColorModel
import com.hundaol.ethiopic.domain.DateModel
import com.hundaol.ethiopic.logging.EthioTree
import com.hundaol.ethiopic.modules.AppComponent
import com.hundaol.ethiopic.modules.AppModule
import com.hundaol.ethiopic.modules.DaggerAppComponent
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.joda.time.DateTime

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

        private val colorModelSubject = BehaviorSubject.createDefault(ColorModel(Color.parseColor("#ffbdbdbd")))
        private val dateModelSubject = BehaviorSubject.createDefault(DateModel.default)

        var colorModels = colorModelSubject as Observable<ColorModel>

        var jdvAnimator: ValueAnimator? = null

        var dateModels = dateModelSubject as Observable<DateModel>

        fun setJdv(jdv: Float, duration: Long = 0L): Unit {
            if (duration < 0L) {
                dateModelSubject.onNext(DateModel(jdv))
            } else if (duration == 0L) {
                jdvAnimator?.cancel()
                jdvAnimator = null
                dateModelSubject.onNext(DateModel(jdv))
            } else {
                val anim = ValueAnimator.ofFloat(dateModelSubject.value.jdv, jdv)
                anim.setInterpolator(AccelerateDecelerateInterpolator())
                anim.setDuration(duration)
                anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                    override fun onAnimationUpdate(animation: ValueAnimator) {
                        setJdv(animation.animatedValue as Float, -1L)
                    }
                })
                anim.start()
                jdvAnimator = anim
            }
        }

        fun setColorModel(rgb: Int): Unit {
            colorModelSubject.onNext(ColorModel(rgb))
        }
    }
}
