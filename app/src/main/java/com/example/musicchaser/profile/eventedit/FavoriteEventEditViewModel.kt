package com.example.musicchaser.profile.eventedit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteEventEditViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    private var eventCollectionRef : CollectionReference? = null

    // set user favorite event snapShotListener
    private var favoriteEventListenerRegistration: ListenerRegistration? = null
    private fun stopEventListening() {
        favoriteEventListenerRegistration?.remove()
    }



    private var eventIdList = mutableListOf<String>()

    private val _userFavoriteEventIdList = MutableLiveData<List<String>>()
    val userFavoriteEventIdList: LiveData<List<String>>
        get() = _userFavoriteEventIdList

    private var eventDataList = mutableListOf<EventData>()

    private val _eventDataListForAdapter = MutableLiveData<List<EventData>>()
    val eventDataListForAdapter: LiveData<List<EventData>>
        get() = _eventDataListForAdapter



    // handle after-detail-getting event list
    private val handleCompletedEventListResult = fun(event: EventData) {
        eventDataList.add(event)
    }

    // setting completed event list liveData for recyclerView
    private val handleSettingEventData = fun() {
        _eventDataListForAdapter.value = eventDataList
        Log.i("UserFavoriteEventEdit", "Event list Completed Data = ${_eventDataListForAdapter.value}")
    }



    private fun getUserFavoriteEvent(){
        eventCollectionRef = repository.getUserFavoriteEvent(UserManager.userId)

        favoriteEventListenerRegistration = eventCollectionRef?.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.i("UserFavoriteEventEdit", "Listen failed", e)
                return@addSnapshotListener
            } else if (querySnapshot != null && !querySnapshot.metadata.hasPendingWrites()) {
                Log.i("UserFavoriteEventEdit", "Listener is triggered")
                eventIdList.clear()

                for (document in querySnapshot!!) {
                    val data = document.data
                    Log.i("UserFavoriteEventEdit", "Favorite event is here : $data")

                    val dataTobeAddToList = (data["event_id"]).toString()

                    eventIdList.add(dataTobeAddToList)
                }
                Log.i("UserFavoriteEventEdit", "Test")
            }
            _userFavoriteEventIdList.value = eventIdList
            Log.i("UserFavoriteEventEdit", "Favorite event list is here : ${_userFavoriteEventIdList.value}")
        }
    }

    fun getCompletedEventList(eventIdList: List<String>) {
        eventDataList.clear()
        repository.getCompletedEventList(
            eventIdList,
            handleCompletedEventListResult,
            handleSettingEventData
        )
    }

    val deleteUserFavoriteEvent = fun(eventId:String) {
        repository.deleteUserFavoriteEvent(
            UserManager.userId,
            eventId
        )
    }

    val subtractEventAttendantAmounts = fun(eventId:String) {
        repository.subtractEventAttendantAmounts(
            eventId
        )
    }



    fun nothing() {}

    init {
        getUserFavoriteEvent()
    }

    override fun onCleared() {
        super.onCleared()
        stopEventListening()
    }
}