package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 17-Dec-17.
 */

public class Modification<T extends BaseObservable, S> extends Change<T, S> {

    private final S mModifiedItems;

    public Modification(T collection, S originalItems, S modifiedItems) {
        super(collection, originalItems);
        mModifiedItems = modifiedItems;
    }

    public S getModifiedItems() {
        return mModifiedItems;
    }

    public int sizeOfModifiedItems() {
        if (mModifiedItems instanceof Collection) {
            return ((Collection) mModifiedItems).size();
        }
        if (mModifiedItems instanceof Map) {
            return ((Map) mModifiedItems).size();
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Modification{" +
                "mModifiedItems=" + mModifiedItems +
                "} " + super.toString();
    }
}