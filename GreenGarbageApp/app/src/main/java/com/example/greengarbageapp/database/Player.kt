package com.example.greengarbageapp.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "player_table")
 class Player {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    // these fields correspond to columns in the table
    var id: Int = 0
    var playerName: String? = null
    @ColumnInfo(name = "player_distance")
    var distance: Int = 0
    @ColumnInfo(name = "player_points")
    var points: Int = 0
        @Ignore
     constructor()

    constructor(id: Int, playerName: String, distance: Int, points: Int){
        this.id = id
        this.playerName = playerName
        this.distance = distance
        this.points = points
    }


 }



