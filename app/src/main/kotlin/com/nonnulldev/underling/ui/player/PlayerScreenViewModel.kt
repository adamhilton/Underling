package com.nonnulldev.underling.ui.player

import com.nonnulldev.underling.data.local.PlayerRepo
import com.nonnulldev.underling.data.model.Player
import com.nonnulldev.underling.injection.qualifier.PlayerName
import com.nonnulldev.underling.injection.scope.PerPlayer
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@PerPlayer
class PlayerScreenViewModel @Inject constructor(
        private val playerRepo: PlayerRepo, @PlayerName playerName: String) {

    private var player: Player
    init {
        player = playerRepo.getByName(playerName)
    }

    private val levelSubject = BehaviorSubject.create<Int>()

    fun getLevel(): Observable<Int> {
        return Observable.just(player.Level)
                .doOnNext { it -> levelSubject.onNext(it) }
    }

    fun removeLevel(): Observable<Int> {
        playerRepo.removeLevel(player)
        return getLevel()
    }

    fun addLevel(): Observable<Int> {
        playerRepo.addLevel(player)
        return getLevel()
    }

    fun levelObservable(): Observable<Int> {
        return levelSubject
    }

}

