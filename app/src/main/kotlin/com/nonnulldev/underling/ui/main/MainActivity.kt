package com.nonnulldev.underling.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.nonnulldev.underling.R
import com.nonnulldev.underling.data.model.Player
import com.nonnulldev.underling.ui.base.BaseActivity
import com.nonnulldev.underling.ui.base.NonPlayerBaseActivity
import com.nonnulldev.underling.ui.create.CreateActivity
import com.nonnulldev.underling.ui.main.recyclerview.PlayersAdapter
import com.nonnulldev.underling.ui.player.PlayerActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActivity : NonPlayerBaseActivity() {

    @BindView(R.id.rvPlayers)
    lateinit var rvPlayers: RecyclerView

    @Inject
    lateinit protected var viewModel: MainScreenViewModel

    private val playersAdapter = PlayersAdapter(emptyList())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        getActivityComponent().inject(this)

        initBindings()

        initUi()
    }

    private fun initBindings() {
        subscriptions.addAll(
                viewModel.playersObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { it -> updatePlayers(it) },

                playersAdapter.getPositionClicks()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { it -> startPlayerActivity(it) }
        )
    }

    private fun  startPlayerActivity(playerName: String) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(PlayerActivity.PLAYER_NAME, playerName)
        startActivity(intent)
    }

    private fun initUi() {

        rvPlayers.layoutManager = LinearLayoutManager(this)

        viewModel.loadPlayers()
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    private fun updatePlayers(players: List<Player>) {
        playersAdapter.setItems(players)
        rvPlayers.adapter = playersAdapter
    }

    @OnClick(R.id.btnCreateNewPlayer)
    fun onCreateNewPlayerButtonClicked() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }

}
