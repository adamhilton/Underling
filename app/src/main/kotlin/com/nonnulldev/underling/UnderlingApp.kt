package com.nonnulldev.underling

import android.app.Application
import android.support.annotation.CallSuper
import com.nonnulldev.underling.injection.component.AppComponent
import com.nonnulldev.underling.injection.component.DaggerAppComponent
import io.realm.Realm

open class UnderlingApp : Application() {

    companion object {
        @JvmStatic lateinit  var appComponent: AppComponent
            private set
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        appComponent = createAppComponent()
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder().build()
    }

}
