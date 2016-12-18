package com.nonnulldev.underling.ui.main

import com.nonnulldev.underling.data.local.PlayerRepo
import com.nonnulldev.underling.data.model.Player
import com.nonnulldev.underling.injection.scope.PerActivity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@PerActivity
class MainScreenViewModel @Inject constructor(private val playerRepo: PlayerRepo) {

    private val playersSubject = BehaviorSubject.create<List<Player>>()

    fun loadPlayers(): Observable<List<Player>> {
        return Observable.just(playerRepo.getAll())
                .doOnNext { it -> playersSubject.onNext(it) }
    }

    fun playersObservable(): Observable<List<Player>> {
        return playersSubject
    }

}
