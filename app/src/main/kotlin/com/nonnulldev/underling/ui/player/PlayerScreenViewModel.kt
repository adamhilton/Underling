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
    private val gearSubject = BehaviorSubject.create<Int>()
    private val totalLevelSubject = BehaviorSubject.create<Int>()

    fun getName(): Observable<String> {
        return Observable.just(player.Name)
    }

    fun getLevel(): Observable<Int> {
        return Observable.just(player.Level)
                .doOnNext { it -> levelSubject.onNext(it) }
    }

    fun getGear(): Observable<Int> {
        return Observable.just(player.Gear)
                .doOnNext { it -> gearSubject.onNext(it) }
    }

    fun getTotalLevel(): Observable<Int> {
        val totalLevel = player.Level + player.Gear
        return Observable.just(totalLevel)
                .doOnNext { it -> totalLevelSubject.onNext(it) }
    }

    fun removeLevel(): Observable<Int> {
        if(player.Level > 0) {
            playerRepo.removeLevel(player)
        }
        return getLevel()
    }

    fun addLevel(): Observable<Int> {
        playerRepo.addLevel(player)
        return getLevel()
    }

    fun removeGear(): Observable<Int> {
        if(player.Gear > 0) {
            playerRepo.removeGear(player)
        }
        return getGear()
    }

    fun addGear(): Observable<Int> {
        playerRepo.addGear(player)
        return getGear()
    }

    fun levelObservable(): Observable<Int> {
        return levelSubject
    }

    fun gearObservable(): Observable<Int> {
        return gearSubject
    }

    fun totalLevelObservable(): Observable<Int> {
        return totalLevelSubject
    }

}

