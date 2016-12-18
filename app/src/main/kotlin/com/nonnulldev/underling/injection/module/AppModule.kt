package com.nonnulldev.underling.injection.module

import com.nonnulldev.underling.injection.scope.PerApplication
import dagger.Module
import dagger.Provides
import io.realm.BuildConfig
import io.realm.Realm
import io.realm.RealmConfiguration

@Module
class AppModule {

    @Provides
    @PerApplication
    fun provideRealmConfiguration(): RealmConfiguration {
        var builder = RealmConfiguration.Builder()
        if (BuildConfig.DEBUG) {
            builder = builder.deleteRealmIfMigrationNeeded()
        }
        return builder.build()
    }

    @Provides
    fun provideRealm(realmConfiguration: RealmConfiguration): Realm {
        return Realm.getInstance(realmConfiguration)
    }

}
