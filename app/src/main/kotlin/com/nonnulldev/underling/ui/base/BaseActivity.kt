package com.nonnulldev.underling.ui.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected var subscriptions = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.clear()
    }
}

