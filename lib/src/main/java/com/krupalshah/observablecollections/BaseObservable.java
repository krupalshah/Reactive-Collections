package com.krupalshah.observablecollections;

import com.krupalshah.observablecollections.change.Change;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 * <p>
 * Base class for all observable collections
 * By default uses {@link PublishSubject} to trigger changes.
 * But you can specify own subject in constructor.
 *
 * @param <T> type of change observed by collection. It will create {@link Subject} of that type.
 */

public abstract class BaseObservable<T extends Change> {

    private Subject<T> mSubject;

    protected BaseObservable() {
        mSubject = PublishSubject.create();
    }

    protected BaseObservable(Subject<T> subject) {
        mSubject = subject;
    }

    public Subject<T> subject() {
        return mSubject;
    }

    public abstract Object items();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseObservable<?> that = (BaseObservable<?>) o;

        return mSubject.equals(that.mSubject);
    }

    @Override
    public int hashCode() {
        return mSubject.hashCode();
    }
}
