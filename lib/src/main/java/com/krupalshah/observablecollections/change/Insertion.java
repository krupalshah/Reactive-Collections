package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 17-Dec-17.
 */

public class Insertion<T extends BaseObservable, S> extends Change<T,S> {

    private final S mInsertedItems;

    public Insertion(T collection, S originalItems, S insertedItems) {
        super(collection, originalItems);
        mInsertedItems = insertedItems;
    }

    public S getInsertedItems() {
        return mInsertedItems;
    }

    public int sizeOfInsertedItems() {
        if (mInsertedItems instanceof Collection) {
            return ((Collection) mInsertedItems).size();
        }
        if (mInsertedItems instanceof Map) {
            return ((Map) mInsertedItems).size();
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Insertion{" +
                "mInsertedItems=" + mInsertedItems +
                "} " + super.toString();
    }
}
