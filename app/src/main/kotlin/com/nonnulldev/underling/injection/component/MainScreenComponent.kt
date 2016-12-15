package com.nonnulldev.underling.injection.component

import dagger.Component
import com.nonnulldev.underling.injection.scope.PerActivity
import com.nonnulldev.underling.ui.main.MainActivity

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class))
interface MainScreenComponent {
    fun inject(activity: MainActivity)
}
