package com.nonnulldev.underling.injection.module

import dagger.Binds
import dagger.Module
import com.nonnulldev.underling.data.remote.DataService
import com.nonnulldev.underling.data.remote.LocalDataService

@Module
abstract class DataModule {
    @Binds
    abstract fun bindDataService(dataService: LocalDataService) : DataService
}
