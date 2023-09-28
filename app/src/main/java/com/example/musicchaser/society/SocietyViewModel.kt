package com.example.musicchaser.society

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.ThreadData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SocietyViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var dataListWithNoAuthorName = mutableListOf<ThreadData>()

    private val _dataListForGetAuthorNameCall = MutableLiveData<List<ThreadData>>()
    val dataListForGetAuthorNameCall: LiveData<List<ThreadData>>
        get() = _dataListForGetAuthorNameCall

    private var dataList = mutableListOf<ThreadData>()

    private val _dataListForAdapter = MutableLiveData<List<ThreadData>>()
    val dataListForAdapter: LiveData<List<ThreadData>>
        get() = _dataListForAdapter





    private val _navigateToSelectedThread = MutableLiveData<ThreadData?>()
    val navigateToSelectedThread: LiveData<ThreadData?>
        get() = _navigateToSelectedThread


    // handle thread clicking navigation
    val displayThreadDetails = fun(thread: ThreadData) {
        _navigateToSelectedThread.value = thread
    }

    // handle thread clicking navigation completed
    fun displayThreadDetailsCompleted() {
        _navigateToSelectedThread.value = null
    }





    // handle thread list from fireStore
    private val handleGetThreadList = fun(document: DocumentSnapshot?, exception: Exception?) {

        if (document != null) {
            val data = document.data

            val originalTime = data!!["thread_date"] as Timestamp
            val eventCommentTime = originalTime.toDate().time / 1000

            val dataTobeAddToList = ThreadData(
                threadId = (data!!["thread_id"]).toString(),
                threadName = (data["thread_name"]).toString(),
                threadType = (data["thread_type"]).toString(),
                threadDate = eventCommentTime,
                threadAuthor = (data["thread_author_id"]).toString(),
                threadContent = (data["thread_content"]).toString(),
                threadComments = (data["thread_comments"]).toString().toInt()
            )

            dataListWithNoAuthorName.add(dataTobeAddToList)
        } else {
            if (exception != null) {
                Log.i("ThreadTest", "In SocietyViewModel something goes wrong : $exception")
            }
        }
    }

    // setting thread list liveData for making next function call to transfer author ID into author's name
    private val handleSettingDataListWithNoAuthorName = fun() {
        _dataListForGetAuthorNameCall.value = dataListWithNoAuthorName
        Log.i("ThreadTest", "Thread Data List = ${_dataListForGetAuthorNameCall.value}")
    }

    // handle after-nickname-transfer thread list
    private val handleCompletedThreadListResult = fun(threadData: ThreadData) {
        dataList.add(threadData)
    }

    // setting completed thread list liveData for adapter after all done
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("ThreadTest", "Thread Completed Data List = ${_dataListForAdapter.value}")
    }






    fun getThreadListWithNoAuthorName() {
        dataListWithNoAuthorName.clear()
        repository.getThreadList(
            handleGetThreadList,
            handleSettingDataListWithNoAuthorName
        )
    }

    fun getCompletedThreadList(threadListWithNoAuthorName: List<ThreadData>) {
        dataList.clear()
        repository.getThreadAuthor(
            threadListWithNoAuthorName,
            handleCompletedThreadListResult,
            handleSettingDataList
        )
    }

    // get thread list result of keyword-searching
    fun getSearchedThreadListResult(keyword: String) {
        dataListWithNoAuthorName.clear()
        repository.getSearchedThreadList(keyword, handleGetThreadList, handleSettingDataListWithNoAuthorName)
    }



    fun nothing() {
    }

    init {
        getThreadListWithNoAuthorName()
    }
}