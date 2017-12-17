package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

/**
 * Created on 17-Dec-17.
 */

public class Modification<T extends BaseObservable, S> extends Change<T, S> {

    private final S mChangedItems;

    public Modification(T collection, S originalItems, S changedItems) {
        super(collection, originalItems);
        mChangedItems = changedItems;
    }


    public S getChangedItems() {
        return mChangedItems;
    }
}