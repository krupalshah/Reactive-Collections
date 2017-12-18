package com.krupalshah.observablecollections.change;

import com.krupalshah.observablecollections.BaseObservable;

import java.util.Collection;
import java.util.Map;

/**
 * Created on 17-Dec-17.
 * <p>
 * Determines that items were added to origin collection.
 * For collection - it will be received when add()/addALl()/offer() is called
 * For map - it will be received when put()/putAll() is called. (Note that putAll() can trigger both : insertion and modification)
 *
 * @param <Source> source observable (for ex. ObservableList/ObservableMap etc.)
 * @param <Result> collection of inserted items
 */

public class Insertion<Source extends BaseObservable, Result> extends Change<Source, Result> {

    private final Result mInsertedItems;

    public Insertion(Source collection, Result originalItems, Result insertedItems) {
        super(collection, originalItems);
        mInsertedItems = insertedItems;
    }

    public Result getInsertedItems() {
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
