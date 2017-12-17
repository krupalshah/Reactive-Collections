package com.krupalshah.observablecollections;

import java.util.NoSuchElementException;
import java.util.Queue;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public class ObservableQueue<E> extends ObservableCollection<E> implements Queue<E> {

    public ObservableQueue(@NonNull Queue<E> collection) {
        super(collection);
    }

    public ObservableQueue(@NonNull Queue<E> collection, @NonNull Subject<Change> subject) {
        super(collection, subject);
    }

    //region custom implementation
    @Override
    public boolean offer(E element) {
        boolean changed;
        try {
            changed = items().offer(element);
        } catch (IllegalArgumentException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            subject().onNext(new Change());
        }
        return changed;
    }

    @Override
    public E remove() {
        try {
            E head = items().remove();
            subject().onNext(new Change());
            return head;
        } catch (NoSuchElementException e) {
            subject().onError(e);
            return null;
        }
    }

    @Override
    public E poll() {
        E head = items().poll();
        if (head != null) {
            subject().onNext(new Change());
        }
        return head;
    }
    //endregion

    //region delegate only
    @Override
    public E element() {
        return items().element();
    }

    @Override
    public E peek() {
        return items().peek();
    }

    @Override
    public Queue<E> items() {
        return (Queue<E>) super.items();
    }
    //endregion
}
