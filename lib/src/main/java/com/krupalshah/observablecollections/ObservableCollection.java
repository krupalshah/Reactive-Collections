package com.krupalshah.observablecollections;

import java.util.Collection;
import java.util.Iterator;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

public class ObservableCollection<E> extends BaseObservable<Change> implements Collection<E> {

    private Collection<E> mCollection;

    public ObservableCollection(@NonNull Collection<E> collection) {
        super();
        mCollection = collection;
    }

    public ObservableCollection(@NonNull Collection<E> collection, @NonNull Subject<Change> subject) {
        super(subject);
        mCollection = collection;
    }

    //region custom implementation
    @Override
    public boolean add(E element) {
        boolean changed;
        try {
            changed = mCollection.add(element);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            subject().onNext(new Change());
        }
        return changed;
    }

    @Override
    public boolean remove(Object object) {
        boolean removed;
        try {
            removed = mCollection.remove(object);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            removed = false;
            subject().onError(e);
        }
        if (removed) {
            subject().onNext(new Change());
        }
        return removed;
    }


    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean changed;
        try {
            changed = mCollection.addAll(collection);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            subject().onNext(new Change());
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean changed;
        try {
            changed = mCollection.removeAll(collection);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            subject().onNext(new Change());
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean changed;
        try {
            changed = mCollection.retainAll(collection);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            subject().onNext(new Change());
        }
        return changed;
    }

    @Override
    public void clear() {
        try {
            mCollection.clear();
            subject().onNext(new Change());
        } catch (UnsupportedOperationException e) {
            subject().onError(e);
        }
    }
    //endregion

    //region delegate only
    @Override
    public int size() {
        return mCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return mCollection.isEmpty();
    }

    @Override
    public boolean contains(Object object) {
        return mCollection.contains(object);
    }

    @Override
    public Iterator<E> iterator() {
        return mCollection.iterator();
    }

    @Override
    public Object[] toArray() {
        return mCollection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return mCollection.toArray(array);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return mCollection.containsAll(collection);
    }
    //endregion

    public Collection<E> items(){
        return mCollection;
    }

}
