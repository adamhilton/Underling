package com.nonnulldev.underling.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nonnulldev.underling.UnderlingApp
import com.nonnulldev.underling.injection.component.ActivityComponent
import com.nonnulldev.underling.injection.component.DaggerActivityComponent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    lateinit private var activityComponent: ActivityComponent

    protected var subscriptions = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initMainScreenComponent()
    }

    private fun initMainScreenComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(UnderlingApp.appComponent)
                .build()
    }

    fun getActivityComponent(): ActivityComponent {
        return activityComponent
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
}

