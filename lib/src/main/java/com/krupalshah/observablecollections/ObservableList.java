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
    public boolean addAll(int index, Collection<? extends E> collection) {
        boolean changed;
        try {
            changed = items().addAll(collection);
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            changed = false;
            mSubject.onError(e);
        }
        if (changed) {
            mSubject.onNext(new Change());
        }
        return changed;
    }

    @Override
    public E get(int index) {
        return items().get(index);
    }

    @Override
    public E set(int index, E element) {
        try {
            E oldElement = items().set(index, element);
            mSubject.onNext(new Change());
            return oldElement;
        } catch (UnsupportedOperationException | IllegalArgumentException | IndexOutOfBoundsException | ClassCastException | NullPointerException e) {
            mSubject.onError(e);
            return null;
        }
    }

    @Override
    public void add(int index, E element) {
        boolean changed;
        try {
            changed = items().add(element);
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            changed = false;
            mSubject.onError(e);
        }
        if (changed) {
            mSubject.onNext(new Change());
        }
    }

    @Override
    public E remove(int index) {
        try {
            E oldElement = items().remove(index);
            mSubject.onNext(new Change());
            return oldElement;
        } catch (UnsupportedOperationException | IndexOutOfBoundsException e) {
            mSubject.onError(e);
            return null;
        }
    }

    @Override
    public int indexOf(Object object) {
        return items().indexOf(object);
    }

    @Override
    public int lastIndexOf(Object object) {
        return items().lastIndexOf(object);
    }

    @Override
    public ListIterator<E> listIterator() {
        return items().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return items().listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return items().subList(fromIndex,toIndex);
    }

    @Override
    public List<E> items() {
        return (List<E>) super.items();
    }
}
