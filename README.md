# Reactive Collections
![Release](https://github.com/krupalshah/Reactive-Collections/actions/workflows/release.yml/badge.svg)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Reactive%20Collections-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6574)

A thin wrapper around Java Collections using RxJava2 in which you can observe the insertions,removals and modifications.<br/>Inspired from observable collections in JavaFX and observable properties in Swift.<br/>

### Features

* Compatible with Java 1.7+ and Android.
* Implements standard collection interfaces such as List, Set, Queue and Map.
* Support for observing insertions, modifications and removals.
* All errors are handled. i.e No runtime crashes.
* Minimal overhead with method count < 200.
* Fully extensible - ability to create your own data structure and specify custom subject.

### ~~Download~~ [NOTE: Since Bintray has stopped further support, please use jar directly from releases or add lib as a module!]
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

1. Use [CollectionsFactory](https://github.com/krupalshah/ObservableCollections/blob/master/lib/src/main/java/com/krupalshah/observablecollections/CollectionsFactory.java) to wrap your collection with observable collection:

```java
 List<Contact> mContacts = new ArrayList<>(); //your collection
 ObservableList<Contact> contactObservableList = CollectionsFactory.observableList(mContacts); //pass in observable... method
```

2. Call `subject()` and subscribe the subject where you want to observe the changes.

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

3. You can determine what kind of change was performed by checking type of `Change<Source,Result>`.<br/>
`Insertion`, `Removal` and `Modification` all three extends `Change` and contains methods to get new/updated/removed items, their size and original collection before changes.

```java
   private void onChangeDetected(Change change) {
        if (change instanceof Insertion) {
            //items inserted (i.e add/addAll/put/putAll/offer etc. called)
        } else if (change instanceof Modification) {
            //items updated (i.e set/put/putAll etc. called)
        } else if (change instanceof Removal) {
            //items removed (i.e remove/removeAll/poll/clear etc. called)
        }
   }
```
<br/>You will get information about what exactly has been changed by calling following methods:
* For all changes : 
  * `getAssociatedCollection()` will return source observable collection for which changes are detected.
  * `getOriginalItems()` will return original items before change.
  * `sizeOfOriginalItems()` will return size of original items before change.
* For `Insertion` : 
  * `getInsertedItems()` will return `Collection` of added items. The collection may be immutable.
  * `sizeOfInsertedItems()` will return size of added items.

* For `Removal` and `Modification`, similar methods have been defined to get collection of removed/updated items and their size.

For more details, please have a look at `Change` and its subclasses [here](https://github.com/krupalshah/ObservableCollections/tree/master/lib/src/main/java/com/krupalshah/observablecollections/change).

* Internally, the library uses [PublishSubject](http://reactivex.io/RxJava/javadoc/rx/subjects/PublishSubject.html) by default. But, you can pass your custom subject in the second parameter of `observe...` methods:
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
