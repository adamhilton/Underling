package com.nonnulldev.underling.ui.main.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.nonnulldev.underling.data.model.Player
import android.view.LayoutInflater
import android.widget.TextView
import butterknife.BindView
import com.nonnulldev.underling.R


class PlayersAdapter(private val players: List<Player>) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.list_item_player, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.tvPlayerName?.text = players[position].Name
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.tvPlayerName)
        lateinit var tvPlayerName: TextView

        init {
            ButterKnife.bind(this, view)
        }
    }
}