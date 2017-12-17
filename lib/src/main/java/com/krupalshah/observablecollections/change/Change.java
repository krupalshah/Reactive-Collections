package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 16-Dec-17.
 */

public abstract class Change<T extends BaseObservable, S> {
    private final S mOriginalItems;
    private final T mObservableCollection;

    protected Change(T observableCollection, S originalItems) {
        mObservableCollection = observableCollection;
        mOriginalItems = originalItems;
    }

    public T getAssociatedCollection(){
        return mObservableCollection;
    }

    public S getOriginalItems() {
        return mOriginalItems;
    }

    public int sizeOfOriginalItems() {
        if (mOriginalItems instanceof Collection) {
            return ((Collection) mOriginalItems).size();
        }
        if (mOriginalItems instanceof Map) {
            return ((Map) mOriginalItems).size();
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Change{" +
                "mOriginalItems=" + mOriginalItems +
                '}';
    }
}
