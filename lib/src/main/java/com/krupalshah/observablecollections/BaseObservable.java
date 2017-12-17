package com.krupalshah.observablecollections;

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
}
