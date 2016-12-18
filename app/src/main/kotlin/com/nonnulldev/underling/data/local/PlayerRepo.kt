package com.nonnulldev.underling.data.local

import com.nonnulldev.underling.data.model.Player

interface PlayerRepo {
    fun create(player: Player)
    fun getByName(name: String)
    fun getAll(): List<Player>
}

