package com.example.project1.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project1.data.models.MemeData
import com.example.project1.data.models.MemeResponse

@Dao
interface MemesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMeme(memeData: MemeData)

    @Query("SELECT * FROM memes_table")
    fun getAllData(): LiveData<List<MemeData>>

    @Query("DELETE FROM memes_table")
    fun clearDatabase()
}