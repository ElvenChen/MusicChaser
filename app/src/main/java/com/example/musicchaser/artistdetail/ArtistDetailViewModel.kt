package com.example.musicchaser.artistdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel  @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {
    var artist: ArtistData? = null

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    var dataListWithOnlyEventId = mutableListOf<String>()

    private val _dataListForGetEventDetailCall = MutableLiveData<List<String>>()
    val dataListForGetEventDetailCall: LiveData<List<String>>
        get() = _dataListForGetEventDetailCall

    private var recentEventDataList = mutableListOf<EventData>()

    private val _recentEventDataForView = MutableLiveData<EventData>()
    val recentEventDataForView: LiveData<EventData>
        get() = _recentEventDataForView




    private val handleSettingArtistIsFavorite = fun() {
        _isFavorite.value = true
    }

    private val handleSettingArtistNotFavorite = fun() {
        _isFavorite.value = false
    }

    // handle artist attend-event list from fireStore
    private val handleGetArtistRecentEventListResult =
        fun(document: DocumentSnapshot?, exception: Exception?) {

            if (document != null) {
                val data = document.data


                val dataTobeAddToList = (data!!["attend_event_id"]).toString()

                Log.i("ArtistRecentEventTest", "Event ID = $dataTobeAddToList")

                dataListWithOnlyEventId.add(dataTobeAddToList)
            } else {
                if (exception != null) {
                    Log.i("ArtistRecentEventTest", "In ArtistDetailViewModel something goes wrong : $exception")
                }
            }
        }

    // setting artist attend-event list liveData for making next function call to transfer event ID into event full-detail
    private val handleSettingDataListWithOnlyEventId = fun() {
        _dataListForGetEventDetailCall.value = dataListWithOnlyEventId
    }

    // handle after-detail-getting event list
    private val handleCompletedRecentEventListResult = fun(event: EventData) {
        recentEventDataList.add(event)
    }

    // setting completed recent event list liveData for view after all done
    private val handleSettingRecentEventData = fun() {
        recentEventDataList.sortBy { it.eventDate }
        Log.i("ArtistRecentEventTest", "**Recent Event list Completed Data = $recentEventDataList")

        if(recentEventDataList.isNotEmpty()){
            _recentEventDataForView.value = recentEventDataList[0]
        }
        Log.i("ArtistRecentEventTest", "Recent Event Completed Data = ${_recentEventDataForView.value}")
    }





    fun addFavoriteArtist() {
        repository.addFavoriteArtist(
            UserManager.userId,
            artist!!.artistId,
            handleSettingArtistIsFavorite
        )
    }

    fun deleteFavoriteArtist() {
        repository.deleteFavoriteArtist(
            UserManager.userId,
            artist!!.artistId,
            handleSettingArtistNotFavorite
        )
    }

    fun getIfArtistIsFavorite() {
        repository.getIfArtistIsFavorite(
            UserManager.userId,
            artist!!.artistId,
            handleSettingArtistIsFavorite,
            handleSettingArtistNotFavorite
        )
    }

    fun getArtistRecentEventList(){
        dataListWithOnlyEventId.clear()
        repository.getArtistRecentEventList(
            artist!!.artistId,
            handleGetArtistRecentEventListResult,
            handleSettingDataListWithOnlyEventId
        )
    }

    fun getCompletedArtistRecentEventList(dataListWithOnlyEventId: List<String>) {
        recentEventDataList.clear()
        repository.getRecentEventName(
            dataListWithOnlyEventId,
            handleCompletedRecentEventListResult,
            handleSettingRecentEventData
        )
    }

    fun nothing() {}
}