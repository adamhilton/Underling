package com.nonnulldev.underling.data.local

import com.nonnulldev.underling.data.model.Player
import com.nonnulldev.underling.injection.scope.PerApplication
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Provider

@PerApplication
class RealmPlayerRepo @Inject constructor(private val realmProvider: Provider<Realm>) : PlayerRepo {

    override fun create(player: Player) {
        realmProvider.get().executeTransaction { r -> r.copyToRealmOrUpdate(player) }
    }

    override fun getByName(name: String): Player {
        return realmProvider.get().where(Player::class.java)
                .equalTo(Player::Name.name, name)
                .findFirst()
    }

    override fun getAll(): List<Player> {
        return realmProvider.get().where(Player::class.java)
                .findAll()
    }

    override fun removeLevel(player: Player) {
        realmProvider.get().beginTransaction()
        player.Level -= 1
        realmProvider.get().commitTransaction()
    }

    override fun addLevel(player: Player) {
        realmProvider.get().beginTransaction()
        player.Level += 1
        realmProvider.get().commitTransaction()
    }

}
