package com.nonnulldev.underling.injection.component

import com.nonnulldev.underling.data.local.PlayerRepo
import com.nonnulldev.underling.injection.module.PlayerModule
import com.nonnulldev.underling.injection.scope.PerPlayer
import com.nonnulldev.underling.ui.player.PlayerActivity
import dagger.Component

@PerPlayer
@Component(dependencies = arrayOf(AppComponent::class, PlayerModule::class))
interface PlayerComponent {
    fun inject(activity: PlayerActivity)
}