# Observable Collections
A thin wrapper around Java Collections using <b>RxJava2</b> in which you can observe the insertions,removals and modifications.<br/>Inspired from observable collections in JavaFX and C#.<br/>

<h3>Features</h3>
<ul>
<li><b>Compatible with Java 1.7 & Android</b></li>
<li>Implements standard collection interfaces such as List, Set, Queue and Map.</li>
<li>Support for observing insertions, modifications and removals.</li>
<li>All errors are handled. i.e No runtime crashes.</li>
<li>Minimal overhead with method count around hundread.</li>
<li>Uses RxJava Subject behind the scenes.</li>
<li>Fully extensible - you can create your own data structure and specify custom subject.</li>
</ul>

<h3>Usage</h3>
<ul>
<li>Use `CollectionsFactory` to wrap your collection with observable collection:</li>

```java
 List<Contact> mContacts = new ArrayList<>(); //your array list
 ObservableList<Contact> contactObservableList = CollectionsFactory.observableList(mContacts); //pass in observable... method
```

<li> get `Subject` and subscribe where you want to receive the changes.</li>

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


