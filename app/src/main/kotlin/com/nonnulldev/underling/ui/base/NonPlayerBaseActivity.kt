package com.nonnulldev.underling.ui.base

import android.os.Bundle
import com.nonnulldev.underling.UnderlingApp
import com.nonnulldev.underling.injection.component.ActivityComponent
import com.nonnulldev.underling.injection.component.DaggerActivityComponent

abstract class NonPlayerBaseActivity : BaseActivity() {

    lateinit private var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityComponent()
    }

    private fun initActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(UnderlingApp.appComponent)
                .build()
    }

    fun getActivityComponent(): ActivityComponent {
        return activityComponent
    }
}

