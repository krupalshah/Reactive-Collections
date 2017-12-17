# Observable Collections
A thin wrapper around Java Collections using **RxJava2** in which you can observe the insertions,removals and modifications.<br/>Inspired from observable collections in JavaFX and C#.<br/>

###Features

* **Compatible with Java 1.7 & Android** 
* Implements standard collection interfaces such as List, Set, Queue and Map.
* Support for observing insertions, modifications and removals.
* All errors are handled. i.e No runtime crashes.
* Minimal overhead with method count around hundread.
* Uses RxJava Subject behind the scenes.
* Fully extensible - you can create your own data structure and specify custom subject.

###Usage

* Use [CollectionsFactory](https://github.com/krupalshah/ObservableCollections/blob/master/lib/src/main/java/com/krupalshah/observablecollections/CollectionsFactory.java) to wrap your collection with observable collection:

```java
 List<Contact> mContacts = new ArrayList<>(); //your array list
 ObservableList<Contact> contactObservableList = CollectionsFactory.observableList(mContacts); //pass in observable... method
```

* Call `subject()` and subscribe where you want to receive the changes.

```java
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
```


