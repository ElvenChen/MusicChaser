package com.example.musicchaser.eventdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {
    var event: EventData? = null

    private var _isFavorite = MutableLiveData<Boolean>()

    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    var dataListWithNoAuthorName = mutableListOf<EventCommentData>()

    private val _dataListForGetAuthorNameCall = MutableLiveData<List<EventCommentData>>()
    val dataListForGetAuthorNameCall: LiveData<List<EventCommentData>>
        get() = _dataListForGetAuthorNameCall

    var dataList = mutableListOf<EventCommentData>()

    private val _dataListForAdapter = MutableLiveData<List<EventCommentData>>()
    val dataListForAdapter: LiveData<List<EventCommentData>>
        get() = _dataListForAdapter


    private val handleSettingEventIsFavorite = fun() {
        _isFavorite.value = true
    }

    private val handleSettingEventNotFavorite = fun() {
        _isFavorite.value = false
    }

    // handle event comments list from fireStore
    private val handleGetEventCommentListResult =
        fun(document: DocumentSnapshot?, exception: Exception?) {

            if (document != null) {
                val data = document.data

                val originalTime = data!!["comment_time"] as Timestamp
                val eventCommentTime = originalTime.toDate().time / 1000

                val dataTobeAddToList = EventCommentData(
                    eventCommentAuthorNickname = (data!!["comment_author_id"]).toString(),
                    eventCommentTime = eventCommentTime,
                    eventCommentContent = (data["comment_content"]).toString()
                )
                Log.i("EventTest", "Event Comment = $dataTobeAddToList")

                dataListWithNoAuthorName.add(dataTobeAddToList)
            } else {
                if (exception != null) {
                    Log.i("EventTest", "In EventViewModel something goes wrong : $exception")
                }
            }
        }

    // setting event comments list liveData for making next function call to transfer author ID into Author's nickname
    private val handleSettingDataListWithNoAuthorName = fun() {
        _dataListForGetAuthorNameCall.value = dataListWithNoAuthorName
    }

    // handle after-nickname-transfer event comments list
    private val handleCompletedEventCommentListResult = fun(eventCommentData: EventCommentData) {
        dataList.add(eventCommentData)
    }

    // setting completed event comment list liveData for adapter after all done
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("EventTest", "Event Comment Completed Data List = ${_dataListForAdapter.value}")
    }


    fun addFavoriteEvent() {
        repository.addFavoriteEvent(
            UserManager.userId,
            event!!.eventId,
            handleSettingEventIsFavorite
        )
    }

    fun deleteFavoriteEvent() {
        repository.deleteFavoriteEvent(
            UserManager.userId,
            event!!.eventId,
            handleSettingEventNotFavorite
        )
    }

    fun getIfEventIsFavorite() {
        repository.getIfEventIsFavorite(
            UserManager.userId,
            event!!.eventId,
            handleSettingEventIsFavorite,
            handleSettingEventNotFavorite
        )
    }

    fun getEventCommentListWithNoAuthorName() {
        dataListWithNoAuthorName.clear()
        repository.getEventCommentList(
            event!!.eventId,
            handleGetEventCommentListResult,
            handleSettingDataListWithNoAuthorName
        )
    }

    fun getCompletedEventCommentList(eventCommentListWithNoAuthorName: List<EventCommentData>) {
        dataList.clear()
        repository.getCommentAuthor(
            eventCommentListWithNoAuthorName,
            handleCompletedEventCommentListResult,
            handleSettingDataList
        )
    }
}