package com.nonnulldev.underling.ui.player

import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.nonnulldev.underling.R
import com.nonnulldev.underling.UnderlingApp
import com.nonnulldev.underling.data.model.Player
import com.nonnulldev.underling.injection.component.ActivityComponent
import com.nonnulldev.underling.injection.component.DaggerPlayerComponent
import com.nonnulldev.underling.injection.component.PlayerComponent
import com.nonnulldev.underling.injection.module.PlayerModule
import com.nonnulldev.underling.ui.base.BaseActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PlayerActivity : BaseActivity() {

    companion object {
        @JvmStatic val PLAYER_NAME = "PLAYER_NAME"
    }

    @BindView(R.id.tvLevel)
    lateinit var tvLevel: TextView

    lateinit private var playerComponent: PlayerComponent

    @Inject
    lateinit protected var viewModel: PlayerScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        ButterKnife.bind(this)

        initPlayerComponent()
        playerComponent.inject(this)

        initBindings()

        initUi()
    }

    private fun initPlayerComponent() {
        val playerName = intent.extras.getString(PLAYER_NAME)

        if(playerName == null) {
            throw NullPointerException("Player name in ${PlayerActivity::class.simpleName} cannot be null")
        }
        playerComponent = DaggerPlayerComponent.builder()
                .appComponent(UnderlingApp.appComponent)
                .playerModule(PlayerModule(playerName))
                .build()
    }

    private fun initBindings() {
        subscriptions.addAll(
                viewModel.levelObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .map { it -> "$it" }
                        .subscribe { it -> updateLevel(it) }
        )
    }

    private fun initUi() {
        viewModel.getLevel()
                .subscribeOn(Schedulers.io())
                .subscribe()
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
