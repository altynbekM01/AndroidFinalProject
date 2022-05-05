package com.example.project1.room

import androidx.lifecycle.LiveData
import com.example.project1.data.models.MemeData
import com.example.project1.data.models.MemeResponse

class MemesRoomRepository(private val memesDao: MemesDao) {

    val getAllData: LiveData<List<MemeData>> = memesDao.getAllData()

    suspend fun addMeme(memeData: MemeData){
        memesDao.addMeme(memeData)
    }

    fun clearDatabase(){
        memesDao.clearDatabase()
    }
}