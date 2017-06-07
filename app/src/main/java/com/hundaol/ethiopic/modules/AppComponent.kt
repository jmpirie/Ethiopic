package com.hundaol.ethiopic.modules

import com.hundaol.ethiopic.App
import com.hundaol.ethiopic.MainActivity
import com.hundaol.ethiopic.views.CalendarView
import com.hundaol.ethiopic.views.DateView

import javax.inject.Singleton

import dagger.Component

/**
 * Created by john.pirie on 2017-04-14.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(o: App)

    fun inject(o: MainActivity)

    fun inject(o: CalendarView)

    fun inject(o: DateView)
}
