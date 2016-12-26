package com.nonnulldev.underling.data.local

import com.nonnulldev.underling.data.model.Player

interface PlayerRepo {
    fun create(player: Player)
    fun getByName(name: String): Player
    fun getAll(): List<Player>
    fun remove(player: Player): Player
    fun removeLevel(player: Player)
    fun addLevel(player: Player)
    fun removeGear(player: Player)
    fun addGear(player: Player)
}

