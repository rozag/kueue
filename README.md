<h1 align="center">kueue</h1>

<p align="center">
  <a href="https://bintray.com/rozag/maven/kueue/_latestVersion">
        <img 
             src="https://img.shields.io/badge/kueue-1.0-brightgreen.svg" 
             alt="kueue">
  </a>
  <a href="https://bintray.com/rozag/maven/kueue-android/_latestVersion">
        <img 
             src="https://img.shields.io/badge/kueue--android-1.0-brightgreen.svg" 
             alt="kueue-android">
  </a>
</p>

Simple Kotlin async processing library for Android built on top of Java executors. This library was originally created as a part of sample app in [kozy-redux](https://github.com/rozag/kozy-redux) library. kueue is a small solution for handling async stuff in pet projects, sample apps, etc. For huge production apps you would probably use some serious libraries like [RxJava](https://github.com/ReactiveX/RxJava), etc.


## Quick start

Add the dependency to your application module's `build.gradle`.
```groovy
dependencies {
    implementation "com.github.rozag:kueue:1.0"
    
    // If you're going to use kueue in an Android app, also add this line
    implementation "com.github.rozag:kueue-android:1.0"
}
```

Create a kueue object. You can place it wherever you want - inside your Application class, for instance. You can also provide your kueue to other components via any DI framework.
```kotlin
val taskQueue = Kueue(
        workerExecutor = Executors.newFixedThreadPool(NUM_THREADS),
        callbackExecutor = MainThreadExecutor()
)
```

And now you can use it like this:
```kotlin
taskQueue.fromCallable { 
    // This will be executed on the workerExecutor
    someBlockingMethod() 
}
            .onComplete { result -> // onComplete callback is optional
                // This will be executed on the callbackExecutor
                handleResult(result) 
            }
            .onError { throwable -> // onError callback is also optional
                // This will be executed on the callbackExecutor
                handleError(throwable) 
            }
            .go()
```

You can also use the `perform` method of `Kueue`:
```kotlin
taskQueue.perform { onComplete, onError ->
    // This will be executed on the workerExecutor
    try {
        onComplete(someBlockingMethod())
    } catch (t: Throwable) {
        onError(t)
    }
}
            .onComplete { ... }
            .onError { ... }
            .go()
```

Also check out the [sample app](https://github.com/rozag/kueue/tree/master/sample/src/main/kotlin/com/github/rozag/kueue/android/sample).


## Contributing

If you find a bug - [create an issue](https://github.com/rozag/kueue/issues/new). It's your contribution. And PRs are always welcome.


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
