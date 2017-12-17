package com.krupalshah.observablecollections;

import com.krupalshah.observablecollections.change.Change;
import com.krupalshah.observablecollections.change.Insertion;
import com.krupalshah.observablecollections.change.Removal;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Queue;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public class ObservableQueue<E> extends ObservableCollection<E> implements Queue<E> {

    protected ObservableQueue(@NonNull Queue<E> collection) {
        super(collection);
    }

    protected ObservableQueue(@NonNull Queue<E> collection, @NonNull Subject<Change> subject) {
        super(collection, subject);
    }

    //region custom implementation
    @Override
    public boolean offer(E element) {
        Collection<E> original = Collections.unmodifiableCollection(items());
        boolean changed;
        try {
            changed = items().offer(element);
        } catch (IllegalArgumentException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<E> inserted = Collections.singleton(element);
            Change<ObservableQueue<E>, Collection<E>> change = new Insertion<>(
                    this, original, inserted
            );
            subject().onNext(change);
        }
        return changed;
    }

    @Override
    public E remove() {
        Collection<E> original = Collections.unmodifiableCollection(items());
        try {
            E head = items().remove();
            Collection<E> removed = Collections.singleton(head);
            Change<ObservableQueue<E>, Collection<E>> change = new Removal<>(
                    this, original, removed
            );
            subject().onNext(change);
            return head;
        } catch (NoSuchElementException e) {
            subject().onError(e);
            return null;
        }
    }

    @Override
    public E poll() {
        Collection<E> original = Collections.unmodifiableCollection(items());
        E head = items().poll();
        if (head != null) {
            Collection<E> removed = Collections.singleton(head);
            Change<ObservableQueue<E>, Collection<E>> change = new Removal<>(
                    this, original, removed
            );
            subject().onNext(change);
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
