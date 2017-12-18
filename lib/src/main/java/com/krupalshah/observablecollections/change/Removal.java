package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 17-Dec-17.
 * <p>
 * Determines that items were removed from origin collection.
 * It will be received when remove()/removeAll()/poll()/clear() is called
 *
 * @param <Source> source observable (for ex. ObservableList/ObservableMap etc.)
 * @param <Result> collection of removed items
 */

public class Removal<Source extends BaseObservable, Result> extends Change<Source, Result> {

    private final Result mRemovedItems;

    public Removal(Source collection, Result originalItems, Result removedItems) {
        super(collection, originalItems);
        mRemovedItems = removedItems;
    }

    public Result getRemovedItems() {
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

    @Override
    public String toString() {
        return "Removal{" +
                "mRemovedItems=" + mRemovedItems +
                "} " + super.toString();
    }
}
