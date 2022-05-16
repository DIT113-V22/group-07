package com.example.greengarbageapp.database

import androidx.lifecycle.LiveData

class PlayerRepository (private val playerDao: PlayerDao){

    val readAllData: LiveData<List<Player>> = playerDao.readAllData()

    suspend fun addPlayer(player: Player){
        playerDao.addPlayer(player)
    }
}