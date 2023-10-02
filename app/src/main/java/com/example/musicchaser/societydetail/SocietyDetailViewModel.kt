package com.example.musicchaser.societydetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.ThreadCommentData
import com.example.musicchaser.data.ThreadData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


private const val FIELD_THREAD_COMMENT_TIME = "thread_comment_time"

@HiltViewModel
class SocietyDetailViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {
    var thread: ThreadData? = null

    private var threadCommentCollectionRef: CollectionReference? = null

    // set thread comment list snapShotListener
    private var threadCommentListenerRegistration: ListenerRegistration? = null
    private fun stopThreadCommentListening() {
        threadCommentListenerRegistration?.remove()
    }



    var dataListWithNoAuthorName = mutableListOf<ThreadCommentData>()

    private val _dataListForGetAuthorNameCall = MutableLiveData<List<ThreadCommentData>>()
    val dataListForGetAuthorNameCall: LiveData<List<ThreadCommentData>>
        get() = _dataListForGetAuthorNameCall

    private var dataList = mutableListOf<ThreadCommentData>()

    private val _dataListForAdapter = MutableLiveData<List<ThreadCommentData>>()
    val dataListForAdapter: LiveData<List<ThreadCommentData>>
        get() = _dataListForAdapter





    // handle after-nickname-transfer thread comments list
    private val handleCompletedThreadCommentListResult = fun(eventCommentData: ThreadCommentData) {
        dataList.add(eventCommentData)
    }

    // setting completed thread comment list liveData for adapter after all done
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i(
            "ThreadCommentTest",
            "Thread Comment Completed Data List = ${_dataListForAdapter.value}"
        )
    }





    fun getThreadCommentList() {
        threadCommentCollectionRef = repository.getThreadComment(thread!!.threadId)

        threadCommentListenerRegistration = threadCommentCollectionRef?.orderBy(
            FIELD_THREAD_COMMENT_TIME,
            Query.Direction.DESCENDING
        )?.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.i("ThreadCommentTest", "Listen failed", e)
                return@addSnapshotListener
            } else if (querySnapshot != null && !querySnapshot.metadata.hasPendingWrites()) {
                Log.i("ThreadCommentTest", "Listener is triggered")
                dataListWithNoAuthorName.clear()

                for (document in querySnapshot!!) {
                    val data = document.data
                    Log.i("ThreadCommentTest", "Thread comment is here : $data")

                    val originalTime = data!!["thread_comment_time"] as Timestamp
                    val threadCommentTime = originalTime.toDate().time / 1000

                    val dataTobeAddToList = ThreadCommentData(
                        threadCommentAuthorNickname = (data!!["thread_comment_author_id"]).toString(),
                        threadCommentTime = threadCommentTime,
                        threadCommentContent = (data["thread_comment_content"]).toString()
                    )

                    dataListWithNoAuthorName.add(dataTobeAddToList)
                }
            }
            _dataListForGetAuthorNameCall.value = dataListWithNoAuthorName
            Log.i(
                "ThreadCommentTest",
                "Thread Comment List is here : ${_dataListForGetAuthorNameCall.value}"
            )
        }
    }

    fun getCompletedThreadCommentList(threadCommentListWithNoAuthorName: List<ThreadCommentData>) {
        dataList.clear()
        repository.getThreadCommentAuthor(
            threadCommentListWithNoAuthorName,
            handleCompletedThreadCommentListResult,
            handleSettingDataList
        )
    }

    override fun onCleared() {
        super.onCleared()
        stopThreadCommentListening()
    }
}