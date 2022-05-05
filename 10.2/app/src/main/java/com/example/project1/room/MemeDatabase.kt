package com.example.project1.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project1.data.models.MemeData

@Database(entities = [MemeData::class], version = 1, exportSchema = false)
abstract class MemeDatabase: RoomDatabase() {

    abstract fun getMemeDao(): MemesDao

    companion object{
        @Volatile
        private var INSTANCE: MemeDatabase? = null

        fun getMemesDatabase(context: Context): MemeDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemeDatabase::class.java,
                    "memes_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}