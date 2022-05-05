package com.example.project1.data.models


data class DefaultMemeResponse(
    val success: String,
    val data: MemesList
)
data class MemesList(
    val memes: List<DefaultMemeInfo>
)
data class DefaultMemeInfo(
    val id: String,
    val name: String,
    val url: String,
    val width: Int,
    val height: Int,
    val box_count: Int
)