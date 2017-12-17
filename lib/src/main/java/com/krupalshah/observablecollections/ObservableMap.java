package com.krupalshah.observablecollections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public class ObservableMap<K, V> extends BaseObservable<Change> implements Map<K, V> {

    private Map<K, V> mMap;

    public ObservableMap(@NonNull Map<K, V> map) {
        super();
        mMap = map;
    }

    public ObservableMap(@NonNull Map<K, V> map, @NonNull Subject<Change> subject) {
        super(subject);
        mMap = map;
    }

    //region custom implementation
    @Override
    public V put(K key, V value) {
        try {
            V oldValue = mMap.put(key, value);
            subject().onNext(new Change());
            return oldValue;
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            subject().onError(e);
            return null;
        }
    }

    @Override
    public V remove(Object key) {
        try {
            V oldValue = mMap.remove(key);
            subject().onNext(new Change());
            return oldValue;
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            subject().onError(e);
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        try {
            mMap.putAll(map);
            subject().onNext(new Change());
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            subject().onError(e);
        }
    }

    @Override
    public void clear() {

    }
    //endregion

    //region delegate only
    @Override
    public int size() {
        return mMap.size();
    }

    @Override
    public boolean isEmpty() {
        return mMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return mMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return mMap.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return mMap.get(key);
    }

    @Override
    public Set<K> keySet() {
        return mMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return mMap.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return mMap.entrySet();
    }

    public Map<K, V> items() {
        return mMap;
    }
    //endregion
}
