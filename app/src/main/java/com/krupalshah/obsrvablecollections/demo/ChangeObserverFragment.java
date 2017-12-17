package com.krupalshah.obsrvablecollections.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krupalshah.observablecollections.ObservableList;
import com.krupalshah.observablecollections.change.Change;
import com.krupalshah.observablecollections.change.Insertion;
import com.krupalshah.observablecollections.change.Modification;
import com.krupalshah.observablecollections.change.Removal;

import java.util.Collection;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 17-Dec-17.
 */

public class ChangeObserverFragment extends Fragment {

    private static final String TAG = "EventReceiverFragment";
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private final ContactBook mContactBook = ContactBook.getInstance();
    private TextView mTxtEvent;
    private TextView mTxtSize;

    public static ChangeObserverFragment newInstance() {
        ChangeObserverFragment fragment = new ChangeObserverFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_receiver, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        observeChanges();
    }

    private void findViews(View view) {
        mTxtEvent = view.findViewById(R.id.txt_event);
        mTxtSize = view.findViewById(R.id.txt_size);
    }

    private void observeChanges() {
        ObservableList<Contact> contactObservableList = mContactBook.getContactObservableList();
        contactObservableList
                .subject()
                .subscribe(new Consumer<Change>() {
                    @Override
                    public void accept(Change change) throws Exception {
                        onChangeDetected(change);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });

        contactObservableList
                .subject()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Change>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Change change) {
                        onChangeDetected(change);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        mTxtEvent.setText("Error while changing contacts");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void onChangeDetected(Change<ObservableList<Contact>, Collection<Contact>> change) {
        Log.d(TAG, "onChangeDetected() called with: change = [" + change + "]");
        if (change instanceof Insertion) {
            int size = ((Insertion) change).sizeOfInsertedItems();
            mTxtEvent.setText(size + " contact inserted");
        } else if (change instanceof Modification) {
            int size = ((Modification) change).sizeOfModifiedItems();
            mTxtEvent.setText(size + " contact updated");
        } else if (change instanceof Removal) {
            int size = ((Removal) change).sizeOfRemovedItems();
            mTxtEvent.setText(size + " contact removed");
        } else {
            mTxtEvent.setText("Unknown change in contacts");
        }

        ObservableList<Contact> associatedCollection = change.getAssociatedCollection();
        int currentSize = associatedCollection.size();
        mTxtSize.setText("List size : " + currentSize);
    }
}
