package com.example.greengarbageapp.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greengarbageapp.databinding.LeaderboardRowBinding

class LeaderboardAdapter: RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    private var playerList = emptyList<Player>()

    class LeaderboardViewHolder(val binding: LeaderboardRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        return LeaderboardViewHolder(LeaderboardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val currentPlayer = playerList[position]
        holder.binding.lbUsername.text = currentPlayer.playerName
        holder.binding.lbDistance.text = currentPlayer.distance.toString()
        holder.binding.lbPoints.text = currentPlayer.points.toString()

    }

    fun setData(player: List<Player>){
        this.playerList = player
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

}