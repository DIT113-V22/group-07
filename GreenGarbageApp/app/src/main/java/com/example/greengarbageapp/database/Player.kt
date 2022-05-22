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

    override fun toString(): String {
        return "Player(id=$id, playerName=$playerName, distance=$distance, points=$points)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false
        if (playerName != other.playerName) return false
        if (distance != other.distance) return false
        if (points != other.points) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (playerName?.hashCode() ?: 0)
        result = 31 * result + distance
        result = 31 * result + points
        return result
    }


}



