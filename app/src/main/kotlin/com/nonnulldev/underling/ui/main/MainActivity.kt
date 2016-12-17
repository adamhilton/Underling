package com.nonnulldev.underling.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import com.nonnulldev.underling.UnderlingApp
import com.nonnulldev.underling.R
import com.nonnulldev.underling.injection.component.DaggerMainScreenComponent
import com.nonnulldev.underling.injection.component.MainScreenComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tvLevel)
    lateinit var tvLevel: TextView

    @Inject
    lateinit protected var viewModel: MainScreenViewModel

    lateinit private var mainScreenComponent: MainScreenComponent

    private var level: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        initMainScreenComponent()
        mainScreenComponent.inject(this)

        initUi()
    }

    private fun initUi() {
        updateLevel()
    }

    private fun updateLevel() {
        tvLevel.text = "$level"
    }
    
    private fun initMainScreenComponent() {
        mainScreenComponent = DaggerMainScreenComponent.builder()
                .appComponent(UnderlingApp.appComponent)
                .build()
    }

    @OnClick(R.id.btnRemoveLevel)
    fun onRemoveLevelButtonClicked() {
        level -= 1
        initUi()
    }

    @OnClick(R.id.btnAddLevel)
    fun onAddLevelButtonClicked() {
        level += 1
        initUi()
    }

}
