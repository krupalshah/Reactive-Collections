package com.krupalshah.observablecollections;

import com.krupalshah.observablecollections.change.Change;
import com.krupalshah.observablecollections.change.Insertion;
import com.krupalshah.observablecollections.change.Removal;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.Subject;

/**
 * Base class for ObservableList,ObservableSet and ObservableQueue.
 * Implements {@link Collection}.
 *
 * @param <E> type of element in collection
 */
public class ObservableCollection<E> extends BaseObservable<Change> implements Collection<E> {

    private Collection<E> mCollection;

    protected ObservableCollection(@NonNull Collection<E> collection) {
        super();
        mCollection = collection;
    }

    protected ObservableCollection(@NonNull Collection<E> collection, @NonNull Subject<Change> subject) {
        super(subject);
        mCollection = collection;
    }

    //region custom implementation
    @Override
    public boolean add(E element) {
        Collection<E> original = Collections.unmodifiableCollection(mCollection);
        boolean changed;
        try {
            changed = mCollection.add(element);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<E> inserted = Collections.singleton(element);
            Change<ObservableCollection<E>, Collection<E>> change = new Insertion<>(
                    this, original, inserted
            );
            subject().onNext(change);
        }
        return changed;
    }

    @Override
    public boolean remove(Object object) {
        Collection<E> original = Collections.unmodifiableCollection(mCollection);
        boolean changed;
        try {
            changed = mCollection.remove(object);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<Object> removed = Collections.singleton(object);
            Change<ObservableCollection<E>, Collection<?>> change = new Removal<>(
                    this, original, removed
            );
            subject().onNext(change);
        }
        return changed;
    }


    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Collection<E> original = Collections.unmodifiableCollection(mCollection);
        boolean changed;
        try {
            changed = mCollection.addAll(collection);
        } catch (UnsupportedOperationException | IllegalArgumentException | IllegalStateException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<E> inserted = Collections.unmodifiableCollection(collection);
            Change<ObservableCollection<E>, Collection<E>> change = new Insertion<>(
                    this, original, inserted
            );
            subject().onNext(change);
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Collection<?> original = Collections.unmodifiableCollection(mCollection);
        boolean changed;
        try {
            changed = mCollection.removeAll(collection);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<?> removed = Collections.unmodifiableCollection(collection);
            Change<ObservableCollection<E>, Collection<?>> change = new Removal<>(
                    this, original, removed
            );
            subject().onNext(change);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Collection<?> original = Collections.unmodifiableCollection(mCollection);
        boolean changed;
        try {
            changed = mCollection.retainAll(collection);
        } catch (UnsupportedOperationException | ClassCastException | NullPointerException e) {
            changed = false;
            subject().onError(e);
        }
        if (changed) {
            Collection<?> removedSet = new LinkedHashSet<>(original);
            removedSet.removeAll(mCollection);
            Change<ObservableCollection<E>, Collection<?>> change = new Removal<>(
                    this, original, removedSet
            );
            subject().onNext(change);
        }
        return changed;
    }

    @Override
    public void clear() {
        Collection<E> original = Collections.unmodifiableCollection(mCollection);
        try {
            mCollection.clear();
            Change<ObservableCollection<E>, Collection<E>> change = new Removal<>(
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

    public Collection<E> items() {
        return mCollection;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ObservableCollection<?> that = (ObservableCollection<?>) o;

        return mCollection.equals(that.mCollection);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + mCollection.hashCode();
        return result;
    }
}
