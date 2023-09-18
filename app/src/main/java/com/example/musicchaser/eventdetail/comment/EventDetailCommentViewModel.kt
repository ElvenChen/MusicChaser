package com.example.musicchaser.eventdetail.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EventDetailCommentViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var event: EventData? = null

    val commentContent = MutableLiveData<String>()

    fun postCommentForEvent() {
        repository.postCommentForEvent(
            UserManager.userId,
            event!!.eventId,
            commentContent.value ?: ""
        )
    }

    fun addCommentAmounts() {
        repository.addCommentAmounts(event!!.eventId)
    }


    fun nothing() {}
}