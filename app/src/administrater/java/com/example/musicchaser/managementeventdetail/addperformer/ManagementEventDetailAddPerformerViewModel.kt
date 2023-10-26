package com.example.musicchaser.managementeventdetail.addperformer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventDetailAddPerformerViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var event: EventData? = null

    private var eventPerformerArtistCollectionRef : CollectionReference? = null

    // set event performer artist snapShotListener
    private var eventPerformerArtistListenerRegistration: ListenerRegistration? = null
    private fun stopEventPerformerArtistListening() {
        eventPerformerArtistListenerRegistration?.remove()
    }



    private var eventPerformerArtistIdList = mutableListOf<String>()

    private val _eventPerformerArtistIdList = MutableLiveData<List<String>>()
    val pendingEventPerformerArtistIdList: LiveData<List<String>>
        get() = _eventPerformerArtistIdList

    private var eventPerformerArtistDataList = mutableListOf<ArtistData>()

    private val _eventPerformerArtistDataListForAdapter = MutableLiveData<List<ArtistData>>()
    val eventPerformerArtistDataListForAdapter: LiveData<List<ArtistData>>
        get() = _eventPerformerArtistDataListForAdapter



    private val _navigateToDeletePerformer = MutableLiveData<ArtistData?>()
    val navigateToDeletePerformer: LiveData<ArtistData?>
        get() = _navigateToDeletePerformer

    // handle delete performer clicking navigation
    val displayPerformerDeleteDetails = fun(artist: ArtistData) {
        _navigateToDeletePerformer.value = artist
    }

    // handle delete performer clicking navigation completed
    fun displayPerformerDeleteDetailsCompleted() {
        _navigateToDeletePerformer.value = null
    }



    // handle after-detail-getting event performer artist list
    private val handleCompletedEventPerformerArtistListResult = fun(artist: ArtistData) {
        eventPerformerArtistDataList.add(artist)
    }

    // setting completed event performer artist list liveData for recyclerView
    private val handleSettingEventPerformerArtistData = fun() {
        _eventPerformerArtistDataListForAdapter.value = eventPerformerArtistDataList
        Log.i("EventPerformerArtist", "EventPerformerArtist list Completed Data = ${_eventPerformerArtistDataListForAdapter.value}")
    }



    fun getEventPerformerArtist(){
        eventPerformerArtistCollectionRef = repository.getEventPerformerArtist(event!!.eventId)

        eventPerformerArtistListenerRegistration = eventPerformerArtistCollectionRef?.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.i("UserFavoriteArtist", "Listen failed", e)
                return@addSnapshotListener
            } else if (querySnapshot != null) {
                Log.i("EventPerformerArtist", "Listener is triggered")
                eventPerformerArtistIdList.clear()

                for (document in querySnapshot!!) {
                    val data = document.data
                    Log.i("EventPerformerArtist", "EventPerformerArtist is here : $data")

                    val dataTobeAddToList = (data["attend_artist_id"]).toString()

                    eventPerformerArtistIdList.add(dataTobeAddToList)
                }
            }
            _eventPerformerArtistIdList.value = eventPerformerArtistIdList
            Log.i("EventPerformerArtist", "EventPerformerArtist list is here : ${_eventPerformerArtistIdList.value}")
        }
    }

    fun getCompletedEventPerformerArtistList(artistIdList: List<String>) {
        eventPerformerArtistDataList.clear()
        repository.getCompletedArtistList(
            artistIdList,
            handleCompletedEventPerformerArtistListResult,
            handleSettingEventPerformerArtistData
        )
    }



    override fun onCleared() {
        super.onCleared()
        stopEventPerformerArtistListening()
        Log.i("Elven", "stopEventPerformerArtistListening() triggered")
    }

}