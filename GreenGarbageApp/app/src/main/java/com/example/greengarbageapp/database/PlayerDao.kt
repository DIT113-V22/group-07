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

    // Order from highest points to lowest, limit to 10 results, only include if player has earned points
    //Changed to show top 3
    @Query("SELECT * FROM player_table WHERE player_points > 0 ORDER BY player_distance DESC LIMIT 3")

    fun readAllData(): LiveData<List<Player>>

}