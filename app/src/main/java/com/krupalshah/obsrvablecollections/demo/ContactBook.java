package com.krupalshah.obsrvablecollections.demo;

import com.krupalshah.observablecollections.CollectionsFactory;
import com.krupalshah.observablecollections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 17-Dec-17.
 */

public class ContactBook {
    private static final ContactBook ourInstance = new ContactBook();
    private final ObservableList<Contact> mContactObservableList;

    public static ContactBook getInstance() {
        return ourInstance;
    }

    private ContactBook() {
        List<Contact> mContacts = new ArrayList<>();
        mContactObservableList = CollectionsFactory.observableList(mContacts);
    }

    public ObservableList<Contact> getContactObservableList() {
        return mContactObservableList;
    }

    public void addContact(Contact contact){
        mContactObservableList.add(contact);
    }

    public void removeContact(Contact contact){
        mContactObservableList.remove(contact);
    }

    public void updateContact(Contact oldContact, Contact newContact) {
        mContactObservableList.set(mContactObservableList.indexOf(oldContact),newContact);
    }
}
