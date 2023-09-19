package com.example.musicchaser.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository):ViewModel() {

    // set user favorite event snapShotListener
    private var collectionRef : CollectionReference? = null



    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String>
        get() = _userNickname

    private val _userAccount = MutableLiveData<String>()
    val userAccount: LiveData<String>
        get() = _userAccount

    private var eventIdList = mutableListOf<String>()

    private val _userFavoriteEventIdList = MutableLiveData<List<String>>()
    val userFavoriteEventIdList: LiveData<List<String>>
        get() = _userFavoriteEventIdList

    private var eventDataList = mutableListOf<EventData>()

    private val _eventDataListForAdapter = MutableLiveData<List<EventData>>()
    val eventDataListForAdapter: LiveData<List<EventData>>
        get() = _eventDataListForAdapter


    private var artistIdList = mutableListOf<String>()

    private val _userFavoriteArtistIdList = MutableLiveData<List<String>>()
    val userFavoriteArtistIdList: LiveData<List<String>>
        get() = _userFavoriteArtistIdList

    private var artistDataList = mutableListOf<ArtistData>()

    private val _artistDataListForAdapter = MutableLiveData<List<ArtistData>>()
    val artistDataListForAdapter: LiveData<List<ArtistData>>
        get() = _artistDataListForAdapter




    private val _navigateToSelectedEvent = MutableLiveData<EventData?>()
    val navigateToSelectedEvent: LiveData<EventData?>
        get() = _navigateToSelectedEvent

    // handle event clicking navigation
    val displayEventDetails = fun(event: EventData) {
        _navigateToSelectedEvent.value = event
    }

    // handle event clicking navigation completed
    fun displayEventDetailsCompleted() {
        _navigateToSelectedEvent.value = null
    }

    private val _navigateToSelectedArtist = MutableLiveData<ArtistData?>()
    val navigateToSelectedArtist: LiveData<ArtistData?>
        get() = _navigateToSelectedArtist

    // handle artist clicking navigation
    val displayArtistDetails = fun(artist: ArtistData) {
        _navigateToSelectedArtist.value = artist
    }

    // handle artist clicking navigation completed
    fun displayArtistDetailsCompleted() {
        _navigateToSelectedArtist.value = null
    }





    // handle after-detail-getting event list
    private val handleCompletedEventListResult = fun(event: EventData) {
        eventDataList.add(event)
    }

    // setting completed event list liveData for recyclerView
    private val handleSettingEventData = fun() {
        _eventDataListForAdapter.value = eventDataList
        Log.i("UserFavoriteEvent", "Event list Completed Data = ${_eventDataListForAdapter.value}")
    }

    // handle after-detail-getting artist list
    private val handleCompletedArtistListResult = fun(artist: ArtistData) {
        artistDataList.add(artist)
    }

    // setting completed artist list liveData for recyclerView
    private val handleSettingArtistData = fun() {
        _artistDataListForAdapter.value = artistDataList
        Log.i("UserFavoriteArtist", "Artist list Completed Data = ${_artistDataListForAdapter.value}")
    }





    private fun getUserFavoriteEvent(){
        collectionRef = repository.getUserFavoriteEvent(UserManager.userId)

        collectionRef?.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.i("UserFavoriteEvent", "Listen failed", e)
                return@addSnapshotListener
            } else if (querySnapshot != null && !querySnapshot.metadata.hasPendingWrites()) {
                Log.i("UserFavoriteEvent", "Listener is triggered")
                eventIdList.clear()

                for (document in querySnapshot!!) {
                    val data = document.data
                    Log.i("UserFavoriteEvent", "Favorite event is here : $data")

                    val dataTobeAddToList = (data["event_id"]).toString()

                    eventIdList.add(dataTobeAddToList)
                }
            }
            _userFavoriteEventIdList.value = eventIdList
            Log.i("UserFavoriteEvent", "Favorite event list is here : ${_userFavoriteEventIdList.value}")
        }
    }

    private fun getUserFavoriteArtist(){
        collectionRef = repository.getUserFavoriteArtist(UserManager.userId)

        collectionRef?.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.i("UserFavoriteArtist", "Listen failed", e)
                return@addSnapshotListener
            } else if (querySnapshot != null && !querySnapshot.metadata.hasPendingWrites()) {
                Log.i("UserFavoriteArtist", "Listener is triggered")
                artistIdList.clear()

                for (document in querySnapshot!!) {
                    val data = document.data
                    Log.i("UserFavoriteArtist", "Favorite artist is here : $data")

                    val dataTobeAddToList = (data["artist_id"]).toString()

                    artistIdList.add(dataTobeAddToList)
                }
            }
            _userFavoriteArtistIdList.value = artistIdList
            Log.i("UserFavoriteArtist", "Favorite artist list is here : ${_userFavoriteArtistIdList.value}")
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

    fun getCompletedArtistList(artistIdList: List<String>) {
        artistDataList.clear()
        repository.getCompletedArtistList(
            artistIdList,
            handleCompletedArtistListResult,
            handleSettingArtistData
        )
    }

    init {
        Log.i("UserTest","UserManager.userNickname = ${UserManager.userNickname}")
        Log.i("UserTest","UserManager.userEmail = ${UserManager.userEmail}")
        _userNickname.value = UserManager.userNickname
        _userAccount.value = UserManager.userEmail

        getUserFavoriteEvent()
        getUserFavoriteArtist()
    }
}