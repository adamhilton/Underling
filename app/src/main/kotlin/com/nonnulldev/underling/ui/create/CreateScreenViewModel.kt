package com.nonnulldev.underling.ui.create

import com.nonnulldev.underling.data.local.PlayerRepo
import com.nonnulldev.underling.data.model.Player
import com.nonnulldev.underling.injection.scope.PerActivity
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@PerActivity
class CreateScreenViewModel @Inject constructor(private val playerRepo: PlayerRepo) {

    private val playerNameIsValidSubject = BehaviorSubject.create<Boolean>()
    private val playerWasCreatedSubject = BehaviorSubject.create<Boolean>()

    fun validatePlayerName(name: String): Observable<Boolean> {
        var playerNameIsValid = Observable.just(true)

        if(name.isEmpty() || name.isBlank()) {
            playerNameIsValid = Observable.just(false)
        }

        return playerNameIsValid
                .doOnNext { it -> playerNameIsValidSubject.onNext(it) }
    }

    fun createPlayer(name: String): Observable<Boolean> {

        var playerCreated = Observable.just(true)

        try {
            playerRepo.create(Player(name))
        } catch (e: Exception) {
            playerCreated = Observable.just(false)
        }

        return playerCreated
                .doOnNext { it -> playerWasCreatedSubject.onNext(it) }
    }

    fun getPlayerNameIsValidObservable(): Observable<Boolean> {
        return playerNameIsValidSubject
    }

    fun getPlayerWasCreatedObservable(): Observable<Boolean> {
        return playerWasCreatedSubject
    }
}