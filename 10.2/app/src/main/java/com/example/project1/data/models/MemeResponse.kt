package com.example.project1.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MemeResponse(
    val success: String,
    val data: MemeData?
)

@Entity(tableName = "memes_table")
data class MemeData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var url: String,
    var page_url: String
)
