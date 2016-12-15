package com.nonnulldev.underling

import android.app.Application
import android.support.annotation.CallSuper
import com.nonnulldev.underling.injection.component.AppComponent
import com.nonnulldev.underling.injection.component.DaggerAppComponent

open class App : Application() {

    companion object {
        @JvmStatic lateinit  var appComponent: AppComponent
            private set
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()
    }

    open protected fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder().build()
    }

}
