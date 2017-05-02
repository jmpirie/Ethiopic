package com.hundaol.ethiopic.modules;

import com.hundaol.ethiopic.App;
import com.hundaol.ethiopic.MainActivity;
import com.hundaol.ethiopic.views.CalendarView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by john.pirie on 2017-04-14.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(App o);

    void inject(MainActivity o);

    void inject(CalendarView o);
}
