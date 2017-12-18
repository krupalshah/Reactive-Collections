package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 17-Dec-17.
 */

public class Modification<Source extends BaseObservable, Result> extends Change<Source, Result> {

    private final Result mModifiedItems;

    public Modification(Source collection, Result originalItems, Result modifiedItems) {
        super(collection, originalItems);
        mModifiedItems = modifiedItems;
    }

    public Result getModifiedItems() {
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