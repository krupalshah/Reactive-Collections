package com.krupalshah.observablecollections;

import java.util.Collection;
import java.util.Iterator;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class ObservableCollection<E> implements Collection<E> {

    protected Collection<E> mCollection;
    protected Subject<Change> mSubject;

    public ObservableCollection(@NonNull Collection<E> collection) {
        mCollection = collection;
        mSubject = PublishSubject.create();
    }

    public ObservableCollection(@NonNull Collection<E> collection, @NonNull Subject<Change> subject) {
        mCollection = collection;
        mSubject = subject;
    }


    @Override
    public int size() {
        return mCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return mCollection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mCollection.contains(o);
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
    public <T> T[] toArray(T[] a) {
        return mCollection.toArray(a);
    }

    @Override
    public boolean add(E element) {
        boolean changed;
        try {
            changed = mCollection.add(element);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException | ClassCastException | NullPointerException e) {
            changed = false;
            mSubject.onError(e);
        }
        if (changed) {
            mSubject.onNext(new Change());
        }
        return changed;
    }

    @Override
    public boolean remove(Object o) {
        boolean removed;
        try {
            removed = mCollection.remove(o);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            removed = false;
            mSubject.onError(e);
        }
        if (removed) {
            mSubject.onNext(new Change());
        }
        return removed;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return mCollection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed;
        try {
            changed = mCollection.addAll(c);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException | ClassCastException | NullPointerException e) {
            changed = false;
            mSubject.onError(e);
        }
        if (changed) {
            mSubject.onNext(new Change());
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed;
        try {
            changed = mCollection.removeAll(c);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            changed = false;
            mSubject.onError(e);
        }
        if (changed) {
            mSubject.onNext(new Change());
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed;
        try {
            changed = mCollection.retainAll(c);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            changed = false;
            mSubject.onError(e);
        }
        if (changed) {
            mSubject.onNext(new Change());
        }
        return changed;
    }

    @Override
    public void clear() {
        try {
            mCollection.clear();
            mSubject.onNext(new Change());
        } catch (UnsupportedOperationException e) {
            mSubject.onError(e);
        }
    }

    public Collection<E> items() {
        return mCollection;
    }

    public Subject<Change> observe() {
        return mSubject;
    }
}
