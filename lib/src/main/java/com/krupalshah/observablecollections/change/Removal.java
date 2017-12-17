package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 17-Dec-17.
 */

public class Removal<T extends BaseObservable, S> extends Change<T, S> {

    private final S mRemovedItems;

    public Removal(T collection, S originalItems, S removedItems) {
        super(collection, originalItems);
        mRemovedItems = removedItems;
    }

    public S getRemovedItems() {
        return mRemovedItems;
    }

    public int sizeOfRemovedItems() {
        if (mRemovedItems instanceof Collection) {
            return ((Collection) mRemovedItems).size();
        }
        if (mRemovedItems instanceof Map) {
            return ((Map) mRemovedItems).size();
        }
        return -1;
    }

    public boolean wasCleared() {
        return sizeOfOriginalItems() == sizeOfRemovedItems();
    }
}
