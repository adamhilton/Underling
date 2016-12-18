package com.nonnulldev.underling.injection.component

import com.nonnulldev.underling.data.local.PlayerRepo
import dagger.Component
import com.nonnulldev.underling.injection.module.DataModule
import com.nonnulldev.underling.injection.scope.PerApplication
import com.nonnulldev.underling.injection.module.AppModule

@PerApplication
@Component(modules = arrayOf(AppModule::class, DataModule::class))
interface AppComponent {

    fun playerRepo(): PlayerRepo

}

