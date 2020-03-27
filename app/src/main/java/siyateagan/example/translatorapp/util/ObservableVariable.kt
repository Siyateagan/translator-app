package siyateagan.example.translatorapp.util

import io.reactivex.subjects.BehaviorSubject

class ObservableVariable<T>(defaultValue: T) {
    var value: T = defaultValue
        set(value) {
            field = value
            observable.onNext(value)
        }
    val observable = BehaviorSubject.createDefault(value)
}