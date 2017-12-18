package com.krupalshah.observablecollections;

import com.krupalshah.observablecollections.change.Change;
import com.krupalshah.observablecollections.change.Insertion;
import com.krupalshah.observablecollections.change.Modification;
import com.krupalshah.observablecollections.change.Removal;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public class ObservableMap<K, V> extends BaseObservable<Change> implements Map<K, V> {

    private Map<K, V> mMap;

    protected ObservableMap(@NonNull Map<K, V> map) {
        super();
        mMap = map;
    }

    protected ObservableMap(@NonNull Map<K, V> map, @NonNull Subject<Change> subject) {
        super(subject);
        mMap = map;
    }

    //region custom implementation
    @Override
    public V put(K key, V value) {
        Map<K, V> original = Collections.unmodifiableMap(items());
        try {
            V oldValue = mMap.put(key, value);
            Map<K, V> addedOrUpdated = Collections.singletonMap(key, value);
            Change<ObservableMap<K, V>, Map<K, V>> change;
            if (oldValue != null) {
                change = new Modification<>(this, original, addedOrUpdated);
            } else {
                change = new Insertion<>(this, original, addedOrUpdated);
            }
            subject().onNext(change);
            return oldValue;
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            subject().onError(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(Object key) {
        Map<K, V> original = Collections.unmodifiableMap(items());
        try {
            V oldValue = mMap.remove(key);
            if (oldValue != null) {
                Map<K, V> removed = Collections.singletonMap((K) key, oldValue);
                Change<ObservableMap<K, V>, Map<K, V>> change = new Removal<>(this, original, removed);
                subject().onNext(change);
            }
            return oldValue;
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            subject().onError(e);
            return null;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        Map<K, V> original = Collections.unmodifiableMap(items());
        try {
            mMap.putAll(map);
            Map<K, V> added = new LinkedHashMap<>();
            Map<K, V> modified = new LinkedHashMap<>();
            for (K k : map.keySet()) {
                if (!original.containsKey(k)) {
                    added.put(k, map.get(k));
                } else {
                    modified.put(k, map.get(k));
                }
            }
            if (!added.isEmpty()) {
                Change<ObservableMap<K, V>, Map<K, V>> change = new Insertion<>(this, original, Collections.unmodifiableMap(added));
                subject().onNext(change);
            }
            if (!modified.isEmpty()) {
                Change<ObservableMap<K, V>, Map<K, V>> change = new Modification<>(this, original, Collections.unmodifiableMap(modified));
                subject().onNext(change);
            }
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            subject().onError(e);
        }
    }

    @Override
    public void clear() {
        Map<K, V> original = Collections.unmodifiableMap(mMap);
        try {
            mMap.clear();
            Change<ObservableMap<K, V>, Map<K, V>> change = new Removal<>(
                    this, original, original
            );
            subject().onNext(change);
        } catch (UnsupportedOperationException e) {
            subject().onError(e);
        }
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

    @Override
    public String toString() {
        return items().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ObservableMap<?, ?> that = (ObservableMap<?, ?>) o;

        return mMap.equals(that.mMap);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mMap.hashCode();
        return result;
    }
}
