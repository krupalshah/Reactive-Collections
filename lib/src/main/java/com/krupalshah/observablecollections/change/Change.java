package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 16-Dec-17.
 */

public abstract class Change<Source extends BaseObservable, Result> {
    private final Result mOriginalItems;
    private final Source mObservableCollection;

    protected Change(Source observableCollection, Result originalItems) {
        mObservableCollection = observableCollection;
        mOriginalItems = originalItems;
    }

    public Source getAssociatedCollection(){
        return mObservableCollection;
    }

    public Result getOriginalItems() {
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
