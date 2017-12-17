package com.krupalshah.observablecollections;

import com.krupalshah.observablecollections.change.Change;
import com.krupalshah.observablecollections.change.Insertion;
import com.krupalshah.observablecollections.change.Modification;
import com.krupalshah.observablecollections.change.Removal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 16-Dec-17.
 */

public class ObservableList<E> extends ObservableCollection<E> implements List<E> {

    protected ObservableList(@NonNull List<E> collection) {
        super(collection);
    }

    protected ObservableList(@NonNull List<E> collection, @NonNull Subject<Change> subject) {
        super(collection, subject);
    }

    //region custom implementation
    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        Collection<E> original = Collections.unmodifiableCollection(items());
        boolean changed;
        try {
            changed = items().addAll(collection);
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<E> inserted = Collections.unmodifiableCollection(collection);
            Change<ObservableList<E>, Collection<E>> change = new Insertion<>(
                    this, original, inserted
            );
            subject().onNext(change);
        }
        return changed;
    }

    @Override
    public E set(int index, E element) {
        Collection<E> original = Collections.unmodifiableList(items());
        try {
            E oldElement = items().set(index, element);
            Collection<E> changedItems = Collections.singleton(element);
            Change<ObservableList<E>, Collection<E>> change = new Modification<>(
                    this, original, changedItems
            );
            subject().onNext(change);
            return oldElement;
        } catch (UnsupportedOperationException | IllegalArgumentException | IndexOutOfBoundsException | ClassCastException | NullPointerException e) {
            subject().onError(e);
            return null;
        }
    }

    @Override
    public void add(int index, E element) {
        Collection<E> original = Collections.unmodifiableCollection(items());
        boolean changed;
        try {
            changed = items().add(element);
        } catch (UnsupportedOperationException | IllegalArgumentException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<E> inserted = Collections.singleton(element);
            Change<ObservableList<E>, Collection<E>> change = new Insertion<>(
                    this, original, inserted
            );
            subject().onNext(change);
        }
    }

    @Override
    public E remove(int index) {
        Collection<E> original = Collections.unmodifiableCollection(items());
        try {
            E oldElement = items().remove(index);
            Collection<E> removed = Collections.singleton(oldElement);
            Change<ObservableList<E>, Collection<E>> change = new Removal<>(
                    this, original, removed
            );
            subject().onNext(change);
            return oldElement;
        } catch (UnsupportedOperationException | IndexOutOfBoundsException e) {
            subject().onError(e);
            return null;
        }
    }
    //endregion

    //region delegate only
    @Override
    public E get(int index) {
        return items().get(index);
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
        return items().subList(fromIndex, toIndex);
    }
    //endregion

    @Override
    public List<E> items() {
        return (List<E>) super.items();
    }

    @Override
    public String toString() {
        return items().toString();
    }
}
