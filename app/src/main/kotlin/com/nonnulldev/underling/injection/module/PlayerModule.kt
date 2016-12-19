package com.nonnulldev.underling.injection.module

import com.nonnulldev.underling.injection.qualifier.PlayerName
import dagger.Module
import dagger.Provides

@Module
class PlayerModule constructor(private val playerName: String) {

    @Provides
    @PlayerName
    fun providesPlayer(): String {
        return playerName
    }

}

