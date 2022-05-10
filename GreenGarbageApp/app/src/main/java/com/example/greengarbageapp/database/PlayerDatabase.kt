package com.example.greengarbageapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Player::class], version = 1, exportSchema = false)
abstract class PlayerDatabase: RoomDatabase() { //Singleton, only one instance

    abstract fun playerDao(): PlayerDao

    companion object{
        @Volatile
        private var INSTANCE: PlayerDatabase? = null

        fun getDatabase(context: Context): PlayerDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){ // if tempInstance is not null, it already exists so return that instance
                return tempInstance
            }
            synchronized(this){ // if tempInstance is null, we create a new instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDatabase::class.java,
                    "player_database"
                )
                    .fallbackToDestructiveMigration() // database is cleared when we change to a new version
                    .build() //  creates the database and initializes it
                INSTANCE = instance
                return instance
            }
        }
    }

}