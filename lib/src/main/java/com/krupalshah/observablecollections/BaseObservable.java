package com.krupalshah.observablecollections;

import com.krupalshah.observablecollections.change.Change;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public abstract class BaseObservable<T extends Change> {

    private Subject<T> mSubject;

    protected BaseObservable(){
        mSubject = PublishSubject.create();
    }

    protected BaseObservable(Subject<T> subject){
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
