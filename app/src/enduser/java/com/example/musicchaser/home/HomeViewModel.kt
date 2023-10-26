package com.example.musicchaser.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    ///// Hot Event /////
    private var hotEventDataList = mutableListOf<EventData>()

    private val _hotEventDataListForAdapter = MutableLiveData<List<EventData>>()
    val hotEventDataListForAdapter: LiveData<List<EventData>>
        get() = _hotEventDataListForAdapter

    ///// Hot Event Dot /////
    private val listForGalleryDot = mutableListOf<String>()

    private val _listForGalleryDotAdapter = MutableLiveData<List<String>>()
    val listForGalleryDotAdapter: LiveData<List<String>>
        get() = _listForGalleryDotAdapter

    var galleryDotAmount = 0



    ///// Favorite Artist Relative Event /////
    private var relativeEventDataList = mutableListOf<EventData>()

    private val _relativeEventDataListForAdapter = MutableLiveData<List<EventData>>()
    val relativeEventDataListForAdapter: LiveData<List<EventData>>
        get() = _relativeEventDataListForAdapter



    ///// Potential Favorite Artist /////
    var potentialArtistDataList = mutableListOf<ArtistData>()

    private val _potentialArtistDataListForAdapter = MutableLiveData<List<ArtistData>>()
    val potentialArtistDataListForAdapter: LiveData<List<ArtistData>>
        get() = _potentialArtistDataListForAdapter






    ///// navigation preparation for Hot Event /////

    private val _navigateToSelectedHotEvent = MutableLiveData<EventData?>()
    val navigateToSelectedHotEvent: LiveData<EventData?>
        get() = _navigateToSelectedHotEvent


    // handle hot event clicking navigation
    val displayHotEventDetails = fun(event: EventData) {
        _navigateToSelectedHotEvent.value = event
    }

    // handle hot event clicking navigation completed
    fun displayHotEventDetailsCompleted() {
        _navigateToSelectedHotEvent.value = null
    }



    ///// navigation preparation for Relative Event /////

    private val _navigateToSelectedRelativeEvent = MutableLiveData<EventData?>()
    val navigateToSelectedRelativeEvent: LiveData<EventData?>
        get() = _navigateToSelectedRelativeEvent


    // handle hot event clicking navigation
    val displayRelativeEventDetails = fun(event: EventData) {
        _navigateToSelectedRelativeEvent.value = event
    }

    // handle Relative event clicking navigation completed
    fun displayRelativeEventDetailsCompleted() {
        _navigateToSelectedRelativeEvent.value = null
    }



    ///// navigation preparation for potential Favorite Artist /////

    private val _navigateToSelectedPotentialArtist = MutableLiveData<ArtistData?>()
    val navigateToSelectedPotentialArtist: LiveData<ArtistData?>
        get() = _navigateToSelectedPotentialArtist


    // handle potential Favorite Artist clicking navigation
    val displayPotentialArtistDetails = fun(artist: ArtistData) {
        _navigateToSelectedPotentialArtist.value = artist
    }

    // handle potential Favorite Artist clicking navigation completed
    fun displayPotentialArtistDetailsCompleted() {
        _navigateToSelectedPotentialArtist.value = null
    }









    ///// Hot Event /////
    // handle hot event list from fireStore
    private val handleGetHotEventListResult = fun(document: DocumentSnapshot?, exception: Exception?) {

        if (document != null) {
            val data = document.data

            val dataTobeAddToList = EventData(
                eventId = (data!!["event_id"]).toString(),
                eventName = (data["event_name"]).toString(),
                eventPlace = (data["event_place"]).toString(),
                eventLongitude = (data["event_longitude"]).toString().toFloat(),
                eventLatitude = (data["event_latitude"]).toString().toFloat(),
                eventAddress = (data["event_address"]).toString(),
                eventDate = (data["event_date"]).toString().toLong(),
                eventWeather = (data["event_weather"]).toString(),
                eventArea = (data["event_area"]).toString(),
                eventAttendant = (data["event_attendant"]).toString().toInt(),
                eventUrl = (data["event_url"]).toString(),
                eventMainPic = (data["event_main_pic"]).toString(),
                eventDesc = (data["event_desc"]).toString(),
                eventComments = (data["event_comments"]).toString().toInt()
            )

            hotEventDataList.add(dataTobeAddToList)
            hotEventDataList.shuffle()
        } else {
            if (exception != null) {
                Log.i("HotEventTest", "HotEvent goes wrong : $exception")
            }
        }
    }

    // setting hot event list liveData for adapter after event list result is handled
    private val handleSettingHotEventDataList = fun() {
        _hotEventDataListForAdapter.value = hotEventDataList
        Log.i("HotEventTest", "HotEvent Data List = ${_hotEventDataListForAdapter.value}")
    }



    ///// Favorite Artist Relative Event /////
    // handle relative event list from fireStore
    private val handleGetRelativeEventListResult = fun(document: DocumentSnapshot?, exception: Exception?) {

        if (document != null) {
            val data = document.data

            val dataTobeAddToList = EventData(
                eventId = (data!!["event_id"]).toString(),
                eventName = (data["event_name"]).toString(),
                eventPlace = (data["event_place"]).toString(),
                eventLongitude = (data["event_longitude"]).toString().toFloat(),
                eventLatitude = (data["event_latitude"]).toString().toFloat(),
                eventAddress = (data["event_address"]).toString(),
                eventDate = (data["event_date"]).toString().toLong(),
                eventWeather = (data["event_weather"]).toString(),
                eventArea = (data["event_area"]).toString(),
                eventAttendant = (data["event_attendant"]).toString().toInt(),
                eventUrl = (data["event_url"]).toString(),
                eventMainPic = (data["event_main_pic"]).toString(),
                eventDesc = (data["event_desc"]).toString(),
                eventComments = (data["event_comments"]).toString().toInt()
            )

            relativeEventDataList.add(dataTobeAddToList)
            relativeEventDataList.shuffle()
        } else {
            if (exception != null) {
                Log.i("RelativeEventTest", "RelativeEvent goes wrong : $exception")
            }
        }
    }

    // setting relative event list liveData for adapter after event list result is handled
    private val handleSettingRelativeEventDataList = fun() {
        _relativeEventDataListForAdapter.value = relativeEventDataList
        Log.i("RelativeEventTest", "RelativeEvent Data List = ${_relativeEventDataListForAdapter.value}")
    }



    ///// Potential Favorite Artist /////
    // handle Potential Favorite Artist list from fireStore
    private val handleGetPotentialArtistListResult = fun(document: DocumentSnapshot?, exception: Exception?) {

        if (document != null) {
            val data = document.data

            val dataTobeAddToList = ArtistData(
                artistId = (data!!["artist_id"]).toString(),
                artistName = (data["artist_name"]).toString(),
                artistDesc = (data["artist_desc"]).toString(),
                artistType = (data["artist_type"]).toString(),
                artistMainPic = (data["artist_main_pic"]).toString()
            )

            potentialArtistDataList.add(dataTobeAddToList)
            potentialArtistDataList.shuffle()
        } else {
            if (exception != null) {
                Log.i("PotentialArtistTest", "PotentialArtist goes wrong : $exception")
            }
        }
    }

    // setting potential favorite artist list liveData for adapter after artist list result is handled
    private val handleSettingPotentialArtistDataList = fun() {
        _potentialArtistDataListForAdapter.value = potentialArtistDataList
        Log.i("PotentialArtistTest", "PotentialArtist Data List = ${_potentialArtistDataListForAdapter.value}")
    }








    ///// Hot Event /////
    // get hot event list result
    private fun getHotEventListResult() {
        hotEventDataList.clear()
        repository.getEventList(handleGetHotEventListResult, handleSettingHotEventDataList)
    }



    ///// Hot Event Dot/////
    fun getListForGalleryDotAdapter(currentItemIndex: Int) {
        listForGalleryDot.clear()

        for (index in 0 until (galleryDotAmount)) {
            listForGalleryDot.add("stroked")
        }
        listForGalleryDot[(currentItemIndex % galleryDotAmount)] = "solid"

        Log.i("detailDot", "$listForGalleryDot")
        _listForGalleryDotAdapter.value = listForGalleryDot
    }



    ///// Favorite Artist Relative Event /////
    // get favorite artist relative event list result
    private fun getRelativeEventListResult() {
        relativeEventDataList.clear()
        repository.getEventList(handleGetRelativeEventListResult, handleSettingRelativeEventDataList)
    }



    ///// Potential Favorite Artist /////
    // get potential favorite artist list result
    private fun getPotentialArtistListResult() {
        potentialArtistDataList.clear()
        repository.getArtistList(handleGetPotentialArtistListResult, handleSettingPotentialArtistDataList)
    }







    init {
        getHotEventListResult()
        getRelativeEventListResult()
        getPotentialArtistListResult()
    }
}