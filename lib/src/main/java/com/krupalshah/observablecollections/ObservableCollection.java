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
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    public Collection<E> items(){
        return mCollection;
    }

    public Subject<Change> observe(){
        return mSubject;
    }
}
