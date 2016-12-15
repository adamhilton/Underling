package com.nonnulldev.underling.injection.component

import dagger.Component
import com.nonnulldev.underling.injection.module.DataModule
import com.nonnulldev.underling.injection.scope.PerApplication
import com.nonnulldev.underling.data.remote.DataService

@PerApplication
@Component(modules = arrayOf(DataModule::class))
interface AppComponent {

    fun dataService(): DataService

}

