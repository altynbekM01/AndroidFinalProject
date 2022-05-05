package com.example.project1.ui.memes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project1.additonal.ConnectionFailedException
import com.example.project1.additonal.launchSafe
import com.example.project1.base.ParentViewModel
import com.example.project1.data.models.CreateMeme
import com.example.project1.data.models.DefaultMemeInfo
import com.example.project1.data.models.DefaultMemeResponse
import com.example.project1.data.models.MemeResponse
import com.example.project1.network.repository.MemesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemesViewModel (private val memesRepository: MemesRepository) : ParentViewModel() {

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val memeResponse: MemeResponse?) : State()
        data class ResultDefaultMeme(val memesInfo: DefaultMemeResponse?) : State()
        data class Error(val error: String?) : State()
        data class IntError(val error: Int) : State()
    }

    private val _liveData = MutableLiveData<State>()
    val liveData: LiveData<State>
        get() = _liveData

    private val _list = MutableLiveData<State>()
    val list: LiveData<State>
        get() = _list

    fun getMemesDefaultList() {
        _list.value =
            State.ShowLoading
        uiScope.launchSafe(::handleError) {
            val result = withContext(Dispatchers.IO) {
                memesRepository.getMemesList()
            }
            _list.postValue(
                State.ResultDefaultMeme(result)
            )
        }
        _list.value =
            State.HideLoading

    }

    fun createNewMeme(memeBody: CreateMeme){
        _liveData.value =
            State.ShowLoading
        uiScope.launchSafe(::handleError) {
            val result = withContext(Dispatchers.IO) {
                memesRepository.postMeme(memeBody)
            }
            _liveData.postValue(
                State.Result(result)
            )
        }
        _liveData.value =
            State.HideLoading
    }

    override fun handleError(e: Throwable) {
        _liveData.value = State.HideLoading
        if (e is ConnectionFailedException) {
            _liveData.value = State.IntError(e.messageInt)
        } else {
            _liveData.value = State.Error(e.localizedMessage)
        }
    }
}