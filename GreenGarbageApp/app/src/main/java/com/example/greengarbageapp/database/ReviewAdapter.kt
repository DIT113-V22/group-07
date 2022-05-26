package com.example.greengarbageapp.database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greengarbageapp.database.LeaderboardAdapter
import com.example.greengarbageapp.database.Player
import com.example.greengarbageapp.databinding.LeaderboardRowBinding
import com.example.greengarbageapp.databinding.ReviewboardRowBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private var playerList = emptyList<Player>()

    class ReviewViewHolder(val binding: ReviewboardRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(ReviewboardRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val currentPlayer = playerList[position]
        holder.binding.lbUsername.text = currentPlayer.playerName
        holder.binding.lbReview.text = currentPlayer.review.toString()
    }

    fun setData(player: List<Player>){
        this.playerList = player
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return playerList.size
    }
}