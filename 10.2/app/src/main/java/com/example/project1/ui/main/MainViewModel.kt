package com.example.project1.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.project1.base.ParentViewModel
import com.example.project1.data.models.MemeData
import com.example.project1.data.models.MemeResponse
import com.example.project1.room.MemeDatabase
import com.example.project1.room.MemesRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : ParentViewModel() {

    var readAllData: LiveData<List<MemeData>>
    private val roomRepository: MemesRoomRepository

    init {
        val todoDao = MemeDatabase.getMemesDatabase(app).getMemeDao()
        roomRepository = MemesRoomRepository(todoDao)
        readAllData = roomRepository.getAllData
    }

    fun addToDatabase(item: MemeData?) {
        if (item != null) {
            viewModelScope.launch(Dispatchers.IO) {
                roomRepository.addMeme(
                    item
                )
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.clearDatabase()
        }
    }

    override fun handleError(e: Throwable) {

    }
}