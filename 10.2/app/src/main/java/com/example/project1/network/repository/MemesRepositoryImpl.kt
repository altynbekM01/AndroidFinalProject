package com.example.project1.network.repository

import com.example.project1.data.models.CreateMeme
import com.example.project1.data.models.DefaultMemeResponse
import com.example.project1.data.models.MemeResponse
import com.example.project1.network.api.MemesApi

class MemesRepositoryImpl(private val memesApi: MemesApi) : MemesRepository {

    override suspend fun postMeme(memeBody: CreateMeme): MemeResponse? {
        return memesApi.postMeme(
            template_id = memeBody.template_id,
            username = memeBody.username,
            password = memeBody.password,
            text0 = memeBody.text0,
            text1 = memeBody.text1
        ).await().body()
    }

    override suspend fun getMemesList(): DefaultMemeResponse? {
        return memesApi.getMemesList().await().body()
    }
}