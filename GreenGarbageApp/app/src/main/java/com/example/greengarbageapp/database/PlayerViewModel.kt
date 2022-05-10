package com.example.greengarbageapp.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel handles the communication between the repository and UI
class PlayerViewModel(application: Application): AndroidViewModel(application){


    private val readAllData: LiveData<List<Player>>
    private val repository: PlayerRepository

    init {
        val playerDao = PlayerDatabase.getDatabase(application).playerDao()
        repository = PlayerRepository(playerDao)
        readAllData = repository.readAllData
    }

    fun addPlayer(player: Player){
        viewModelScope.launch(Dispatchers.IO){ //Run the code on a background thread, so as not to launch database jobs from the main thread
            repository.addPlayer(player)
        }
    }
}