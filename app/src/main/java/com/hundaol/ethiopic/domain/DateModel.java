package com.hundaol.ethiopic.domain;

import com.hundaol.ethiopic.cal.GregorianCal;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by john.pirie on 2017-05-07.
 */

public final class DateModel {

    private final BehaviorSubject<Float> dateSubject;

    public DateModel() {
        dateSubject = BehaviorSubject.createDefault(new Float(GregorianCal.INSTANCE.today()));
    }

    public Observable<Float> getDate() {
        return dateSubject;
    }

    public void setJdv(float jdv) {
        dateSubject.onNext(jdv);
    }

    public void toToday() {
        setJdv(GregorianCal.INSTANCE.today());
    }

    public void nextDay(int days) {
        setJdv(dateSubject.getValue() + days);
    }

    public void prevDays(int days) {
        setJdv(dateSubject.getValue() - days);
    }

    public static Predicate<Float> newJdnFilter() {
        return new Predicate<Float>() {
            int v = Integer.MIN_VALUE;
            @Override
            public boolean test(@NonNull Float value) throws Exception {
                int i = (int)value.floatValue();
                return v != (v = i);
            }
        };
    }

}
