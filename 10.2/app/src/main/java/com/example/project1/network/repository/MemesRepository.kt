package com.example.project1.network.repository

import com.example.project1.data.models.CreateMeme
import com.example.project1.data.models.DefaultMemeResponse
import com.example.project1.data.models.MemeResponse

interface MemesRepository {

    suspend fun postMeme(memeBody: CreateMeme): MemeResponse?

    suspend fun getMemesList(): DefaultMemeResponse?

}