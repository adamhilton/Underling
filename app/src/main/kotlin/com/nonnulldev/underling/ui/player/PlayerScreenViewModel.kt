package com.nonnulldev.underling.ui.player

import com.nonnulldev.underling.injection.scope.PerActivity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@PerActivity
class PlayerScreenViewModel @Inject constructor() {

    private var level = 0

    private val levelSubject = BehaviorSubject.create<Int>()

    fun getLevel(): Observable<Int> {
        return Observable.just(level)
                .doOnNext { it -> levelSubject.onNext(it) }
    }

    fun removeLevel(): Observable<Int> {
        level -= 1
        return getLevel()
    }

    fun addLevel(): Observable<Int> {
        level += 1
        return getLevel()
    }

    fun levelObservable(): Observable<Int> {
        return levelSubject
    }

}

