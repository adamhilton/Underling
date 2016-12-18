package com.nonnulldev.underling.ui.create

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nonnulldev.underling.R
import javax.inject.Inject

class CreateActivity : AppCompatActivity() {

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
