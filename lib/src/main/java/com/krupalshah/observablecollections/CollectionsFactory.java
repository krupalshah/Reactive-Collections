package com.krupalshah.observablecollections;

import com.krupalshah.observablecollections.change.Change;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import io.reactivex.subjects.Subject;

/**
 * Created on 17-Dec-17.
 */

public class CollectionsFactory {

    public static <E> ObservableCollection<E> observableCollection(Collection<E> collection) {
        return new ObservableCollection<>(collection);
    }

    public static <E> ObservableCollection<E> observableCollection(Collection<E> collection, Subject<Change> subject) {
        return new ObservableCollection<>(collection, subject);
    }

    public static <E> ObservableList<E> observableList(List<E> list) {
        return new ObservableList<>(list);
    }

    public static <E> ObservableList<E> observableList(List<E> list, Subject<Change> subject) {
        return new ObservableList<>(list, subject);
    }

    public static <E> ObservableSet<E> observableSet(Set<E> set) {
        return new ObservableSet<>(set);
    }

    public static <E> ObservableSet<E> observableSet(Set<E> set, Subject<Change> subject) {
        return new ObservableSet<>(set, subject);
    }

    public static <E> ObservableQueue<E> observableQueue(Queue<E> queue) {
        return new ObservableQueue<>(queue);
    }

    public static <E> ObservableQueue<E> observableQueue(Queue<E> queue, Subject<Change> subject) {
        return new ObservableQueue<>(queue, subject);
    }

    public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map) {
        return new ObservableMap<>(map);
    }

    public static <K, V> ObservableMap<K, V> observableMap(Map<K, V> map, Subject<Change> subject) {
        return new ObservableMap<>(map, subject);
    }
}
