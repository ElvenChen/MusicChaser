package com.example.musicchaser.society.post

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SocietyPostViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    val threadName = MutableLiveData<String>()

    val threadType = MutableLiveData<String>()

    val threadContent = MutableLiveData<String>()



    fun postThread() {
        repository.postThread(
            UserManager.userId,
            threadName.value ?: "",
            threadType.value ?: "",
            threadContent.value ?: ""
        )
    }



    fun nothing() {}
}