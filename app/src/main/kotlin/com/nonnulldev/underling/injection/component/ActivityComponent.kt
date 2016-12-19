package com.nonnulldev.underling.injection.component

import com.nonnulldev.underling.injection.scope.PerActivity
import com.nonnulldev.underling.ui.create.CreateActivity
import com.nonnulldev.underling.ui.main.MainActivity
import com.nonnulldev.underling.ui.player.PlayerActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: CreateActivity)
}
