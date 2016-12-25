package com.nonnulldev.underling.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.nonnulldev.underling.R
import com.nonnulldev.underling.data.model.Player
import com.nonnulldev.underling.ui.base.NonPlayerBaseActivity
import com.nonnulldev.underling.ui.create.CreateActivity
import com.nonnulldev.underling.ui.main.recyclerview.PlayersAdapter
import com.nonnulldev.underling.ui.player.PlayerActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject


class MainActivity : NonPlayerBaseActivity() {

    @BindView(R.id.rvPlayers)
    lateinit var rvPlayers: RecyclerView

    @Inject
    lateinit protected var viewModel: MainScreenViewModel

    private var players: List<Player> = emptyList()
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
                playersUpdatedSubscription(),
                playerClickedSubscription()
        )
    }

    private fun playerClickedSubscription(): Disposable? {
        return playersAdapter.getPositionClicks()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it -> startPlayerActivity(it) }
    }

    private fun playersUpdatedSubscription(): Disposable? {
        return viewModel.playersObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it -> updatePlayers(it) }
    }

    private fun  startPlayerActivity(playerName: String) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(PlayerActivity.PLAYER_NAME, playerName)
        startActivity(intent)
    }

    private fun initUi() {

        rvPlayers.layoutManager = LinearLayoutManager(this)

        val itemTouchHelper = ItemTouchHelper(createItemTouchHelperCallBack())
        itemTouchHelper.attachToRecyclerView(rvPlayers)

        viewModel.loadPlayers()
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    private fun createItemTouchHelperCallBack(): ItemTouchHelper.Callback {
        return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                if (viewHolder != null) {
                    removePlayer(viewHolder.adapterPosition)
                }
            }
        }
    }

    private fun updatePlayers(players: List<Player>) {
        this.players = players
        playersAdapter.setItems(players)
        rvPlayers.adapter = playersAdapter
    }

    private fun removePlayer(position: Int) {
        val removedPlayer = players[position]
        subscriptions.add(
            viewModel.deletePlayer(players[position])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext {
                        playersAdapter.removeItem(position)
                    }.subscribe()
        )
        showUndoDeletedPlayerSnackbar(position, removedPlayer)
    }

    private fun showUndoDeletedPlayerSnackbar(position: Int, removedPlayer: Player) {
        Snackbar.make(findViewById(R.id.activity_main), "", Snackbar.LENGTH_LONG)
                .setAction("Undo") {
                    subscriptions.add(
                            viewModel.createPlayer(removedPlayer)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe { undoDeletedPlayer(position, removedPlayer) }
                    )
                }
                .setActionTextColor(Color.MAGENTA)
                .show()
    }

    private fun undoDeletedPlayer(position: Int, removedPlayer: Player) {
        (this.players as MutableList).add(position, removedPlayer)
        playersAdapter.notifyItemInserted(position)
    }

    @OnClick(R.id.btnCreateNewPlayer)
    fun onCreateNewPlayerButtonClicked() {
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }
}
