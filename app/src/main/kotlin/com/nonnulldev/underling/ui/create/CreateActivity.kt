package com.nonnulldev.underling.ui.create

import android.os.Bundle
import com.nonnulldev.underling.R
import com.nonnulldev.underling.ui.base.BaseActivity
import javax.inject.Inject

class CreateActivity : BaseActivity() {

    @Inject
    lateinit protected var viewModel: CreateScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        initCreateScreenViewModel()
    }

    private fun initCreateScreenViewModel() {

    }
}
