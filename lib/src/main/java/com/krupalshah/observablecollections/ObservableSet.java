package com.krupalshah.observablecollections;

import java.util.Set;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public class ObservableSet<E> extends ObservableCollection<E> implements Set<E> {

    public ObservableSet(@NonNull Set<E> collection) {
        super(collection);
    }

    public ObservableSet(@NonNull Set<E> collection, @NonNull Subject<Change> subject) {
        super(collection, subject);
    }

    @Override
    public Set<E> items() {
        return (Set<E>) super.items();
    }
}
