package com.example.musicchaser.society.submission

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SocietySubmissionViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {


    val eventName = MutableLiveData<String>()

    val eventUrl = MutableLiveData<String>()

    val eventOtherNote = MutableLiveData<String>()


    fun postEventSubmission() {
        repository.postEventSubmission(
            UserManager.userId,
            eventName.value ?: "",
            eventUrl.value ?: "",
            eventOtherNote.value ?: ""
        )
    }


    fun nothing() {}
}