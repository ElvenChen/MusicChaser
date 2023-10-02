package com.example.musicchaser.societydetail.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.ThreadData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SocietyDetailCommentViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var thread: ThreadData? = null

    val commentContent = MutableLiveData<String>()



    fun postCommentForThread() {
        repository.postCommentForThread(
            UserManager.userId,
            thread!!.threadId,
            commentContent.value ?: ""
        )
    }

    fun addThreadCommentAmounts() {
        repository.addThreadCommentAmounts(thread!!.threadId)
    }



    fun nothing() {}
}