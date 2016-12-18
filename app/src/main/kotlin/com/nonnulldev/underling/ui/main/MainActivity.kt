package com.nonnulldev.underling.ui.main

import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.nonnulldev.underling.R
import com.nonnulldev.underling.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @BindView(R.id.tvLevel)
    lateinit var tvLevel: TextView

    @Inject
    lateinit protected var viewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        getActivityComponent().inject(this)

        initBindings()

        initUi()
    }

    private fun initUi() {
        viewModel.getLevel()
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    private fun initBindings() {
        subscriptions.addAll(
                viewModel.levelObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { it -> "$it" }
                        .subscribe { it -> updateLevel(it) }
        )
    }

    private fun updateLevel(level: String) {
        tvLevel.text = level
    }

    @OnClick(R.id.btnRemoveLevel)
    fun onRemoveLevelButtonClicked() {
        viewModel.removeLevel()
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    @OnClick(R.id.btnAddLevel)
    fun onAddLevelButtonClicked() {
        viewModel.addLevel()
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

}
