package com.hundaol.ethiopic.views;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john.pirie on 2017-04-27.
 */

public class ViewModelChangeEvent<M> implements ViewModelChangeListener<M> {

    private final List<ViewModelChangeListener<M>> listeners = new ArrayList<>(0);

    public void add(ViewModelChangeListener<M> listener) {
        listeners.add(listener);
    }

    public void remove(ViewModelChangeListener<M> listener) {
        listeners.remove(listener);
    }

    @Override
    public void modelChanged(M m) {
        for (ViewModelChangeListener listener : listeners) {
            listener.modelChanged(m);
        }
    }
}
