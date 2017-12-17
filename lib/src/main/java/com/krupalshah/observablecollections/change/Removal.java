package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

/**
 * Created on 17-Dec-17.
 */

public class Removal<T extends BaseObservable, S> extends Change<T,S> {

    private final S mRemovedItems;

    public Removal(T collection, S originalItems, S removedItems) {
        super(collection, originalItems);
        mRemovedItems = removedItems;
    }

    public S getRemovedItems() {
        return mRemovedItems;
    }
}
