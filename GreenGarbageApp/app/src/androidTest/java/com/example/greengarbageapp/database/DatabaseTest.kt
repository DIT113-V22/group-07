package com.example.greengarbageapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.greengarbageapp.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest

class DatabaseTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: PlayerDatabase
    private lateinit var dao: PlayerDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), PlayerDatabase::class.java).allowMainThreadQueries().build()
        dao = database.playerDao()
    }

    @After
    fun close(){
        database.close()
    }


    // Checks if insertion in to the database works
    @Test
    fun insertion() = runBlocking {
        val player = Player(1, "Christopher", 10, 10)
        dao.addPlayer(player)
        val allPlayers = dao.readAllData().getOrAwaitValue()
        assertThat(allPlayers).contains(player)
    }


    // Checks if database retrieves players in the correct order (descending based on points)
    @Test
    fun sorted() = runBlocking {
        val playerList: ArrayList<Player> = ArrayList()
        playerList.add(Player(1, "Player1", 500, 32))
        playerList.add(Player(2, "Player2", 999, 200))
        playerList.add(Player(3, "Player3", 1500, 77))
        playerList.forEach{
            database.playerDao().addPlayer(it)
        }
        val databasePlayers = database.playerDao().readAllData().getOrAwaitValue()
        assert(databasePlayers == playerList.sortedWith(compareByDescending { it.points }))
    }

}