# Observable Collections
 [ ![Download](https://api.bintray.com/packages/krupalshah55/ObservableCollections/observablecollections/images/download.svg) ](https://bintray.com/krupalshah55/ObservableCollections/observablecollections/_latestVersion)
 [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/krupalshah/ObservableCollections/blob/master/LICENSE)
<a href="http://www.methodscount.com/?lib=com.krupalshah%3Aobservablecollections%3A1.0.5"><img src="https://img.shields.io/badge/Methods and size-core: 178 | deps: 10295 | 18 KB-e91e63.svg"/></a>

A thin wrapper around Java Collections using **RxJava2** in which you can observe the insertions,removals and modifications.<br/>Inspired from observable collections in JavaFX observable properties in Swift.<br/>

### Features

* Compatible with Java 1.7+ and Android.
* Implements standard collection interfaces such as List, Set, Queue and Map.
* Support for observing insertions, modifications and removals.
* All errors are handled. i.e No runtime crashes.
* Minimal overhead with method count < 200.
* Uses RxJava Subject behind the scenes.
* Fully extensible - you can create your own data structure and specify custom subject.

### Download
* Gradle
```
dependencies {
    compile 'com.krupalshah:observablecollections:1.0.5'
}
```

* Maven
```
<dependency>
  <groupId>com.krupalshah</groupId>
  <artifactId>observablecollections</artifactId>
  <version>1.0.5</version>
  <type>pom</type>
</dependency>
```

### Usage

* Use [CollectionsFactory](https://github.com/krupalshah/ObservableCollections/blob/master/lib/src/main/java/com/krupalshah/observablecollections/CollectionsFactory.java) to wrap your collection with observable collection:

```java
 List<Contact> mContacts = new ArrayList<>(); //your collection
 ObservableList<Contact> contactObservableList = CollectionsFactory.observableList(mContacts); //pass in observable... method
```

* Call `subject()` and subscribe the subject where you want to observe the changes.

```java
contactObservableList
                .subject() //get subject
                .subscribe(new Consumer<Change>() { //you can apply schedulers if you want
                    @Override
                    public void accept(Change change) throws Exception {
                        onChangeDetected(change); //all changes will be received here
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
```

* You can determine what kind of change was performed by checking type of `Change<Source,Result>`.<br/>
`Insertion`, `Removal` and `Modification` all three extends `Change` and contains methods to get new/updated/removed items, their size and original collection before changes.

```java
  private void onChangeDetected(Change<ObservableList<Contact>, Collection<Contact>> change) {
        if (change instanceof Insertion) {
            //items inserted (i.e add/addAll/put/putAll/offer etc. called)
        } else if (change instanceof Modification) {
            //items updated (i.e set/put/putAll etc. called)
        } else if (change instanceof Removal) {
            //items removed (i.e remove/removeAll/poll/clear etc. called)
        }
   }
```
For more details, please have a look at `Change` and its subclasses [here](https://github.com/krupalshah/ObservableCollections/tree/master/lib/src/main/java/com/krupalshah/observablecollections/change) to know more methods.

* Internally, It uses [PublishSubject](http://reactivex.io/RxJava/javadoc/rx/subjects/PublishSubject.html) by default, but you can pass your custom subject in the second parameter of `observe...` methods:

```java
      BehaviorSubject<Change> behaviorSubject = BehaviorSubject.create();
      ObservableMap<String,String> observableMap = CollectionsFactory.observableMap(new ArrayMap<String, String>(), behaviorSubject);
```

### Sample
There is a sample Android app [here](https://github.com/krupalshah/ObservableCollections/tree/master/app) which only demonstrates `ObservableList` for now.<br/>`ChangeSourceFragment` changes the `ObservableList`, which is observed in `ChangeObserverFragment`.<br/>Also note that ObservableCollections is a Java library, not specific to only Android. So, it can be used with any Java/Android project.


### Licence
```
Copyright 2017 Krupal Shah

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
