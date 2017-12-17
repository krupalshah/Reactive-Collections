package com.krupalshah.obsrvablecollections.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krupalshah.observablecollections.ObservableList;

import java.util.Random;
import java.util.UUID;

/**
 * Created on 17-Dec-17.
 */

public class ChangeSourceFragment extends Fragment implements View.OnClickListener {

    private final ContactBook mContactBook = ContactBook.getInstance();

    public static ChangeSourceFragment newInstance() {
        ChangeSourceFragment fragment = new ChangeSourceFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_source, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_add).setOnClickListener(this);
        view.findViewById(R.id.btn_remove).setOnClickListener(this);
        view.findViewById(R.id.btn_set).setOnClickListener(this);
    }

    public void add() {
        Contact contact = new Contact();
        contact.setName(UUID.randomUUID().toString());
        Random random = new Random();
        contact.setPhone(String.valueOf(random.nextLong()));
        mContactBook.addContact(contact);
    }

    public void remove() {
        ObservableList<Contact> observableList = mContactBook.getContactObservableList();
        if(observableList.isEmpty()) return;
        Random random = new Random();
        Contact randomContact = observableList.get(random.nextInt(observableList.size()));
        mContactBook.removeContact(randomContact);
    }

    public void set() {
        ObservableList<Contact> observableList = mContactBook.getContactObservableList();
        if(observableList.isEmpty()) return;
        Random random = new Random();
        Contact randomContact = observableList.get(random.nextInt(observableList.size()));

        Contact newContact = new Contact();
        newContact.setName(UUID.randomUUID().toString());
        newContact.setPhone(String.valueOf(random.nextLong()));

        mContactBook.updateContact(randomContact, newContact);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_set:
                set();
                break;
            case R.id.btn_remove:
                remove();
                break;
        }
    }
}
