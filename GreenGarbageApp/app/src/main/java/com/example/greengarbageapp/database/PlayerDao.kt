package com.example.greengarbageapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao // DAO (data access object) contains the methods used for accessing the database
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayer(player: Player)

    @Query("SELECT * FROM player_table ORDER BY player_points ASC") //order from highest points to lowest
    fun readAllData(): LiveData<List<Player>>

}