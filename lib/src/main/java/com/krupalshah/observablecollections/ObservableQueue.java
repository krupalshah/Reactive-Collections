package com.krupalshah.observablecollections;

import java.util.Queue;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public class ObservableQueue<E> extends ObservableCollection<E> implements Queue<E>{

    public ObservableQueue(@NonNull Queue<E> collection) {
        super(collection);
    }

    public ObservableQueue(@NonNull Queue<E> collection, @NonNull Subject<Change> subject) {
        super(collection, subject);
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public Queue<E> items() {
        return (Queue<E>) super.items();
    }
}
