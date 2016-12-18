package com.nonnulldev.underling.injection.module

import com.nonnulldev.underling.data.local.PlayerRepo
import com.nonnulldev.underling.data.local.RealmPlayerRepo
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindPlayerRepo(playerRepo: RealmPlayerRepo) : PlayerRepo
}
