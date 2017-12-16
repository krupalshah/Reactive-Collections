package com.krupalshah.observablecollections;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 16-Dec-17.
 */

public class ObservableList<E> extends ObservableCollection<E> implements List<E> {

    public ObservableList(@NonNull List<E> collection) {
        super(collection);
    }

    public ObservableList(@NonNull List<E> collection, @NonNull Subject<Change> subject) {
        super(collection, subject);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean added = mCollection.addAll(c);
        if (added) {
            mSubject.onNext(new Change());
        }
        return added;
    }

    @Override
    public E get(int index) {
        return items().get(index);
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public List<E> items() {
        return (List<E>) super.items();
    }
}
