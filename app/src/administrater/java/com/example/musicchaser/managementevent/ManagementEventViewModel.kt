package com.example.musicchaser.managementevent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var dataList = mutableListOf<EventData>()

    private val _dataListForAdapter = MutableLiveData<List<EventData>>()
    val dataListForAdapter: LiveData<List<EventData>>
        get() = _dataListForAdapter



    private val _navigateToEditEvent = MutableLiveData<EventData?>()
    val navigateToEditEvent: LiveData<EventData?>
        get() = _navigateToEditEvent

    private val _navigateToDeleteEvent = MutableLiveData<EventData?>()
    val navigateToDeleteEvent: LiveData<EventData?>
        get() = _navigateToDeleteEvent

    private val _navigateToAddEventPerformer = MutableLiveData<EventData?>()
    val navigateToAddEventPerformer: LiveData<EventData?>
        get() = _navigateToAddEventPerformer





    // handle edit event clicking navigation
    val displayEventEditDetails = fun(artist: EventData) {
        _navigateToEditEvent.value = artist
    }

    // handle edit event clicking navigation completed
    fun displayEventEditDetailsCompleted() {
        _navigateToEditEvent.value = null
    }

    // handle delete event clicking navigation
    val displayEventDeleteDetails = fun(event: EventData) {
        _navigateToDeleteEvent.value = event
    }

    // handle delete event clicking navigation completed
    fun displayEventDeleteDetailsCompleted() {
        _navigateToDeleteEvent.value = null
    }

    // handle add event performer clicking navigation
    val displayAddEventPerformerDetails = fun(event: EventData) {
        _navigateToAddEventPerformer.value = event
    }

    // handle add event performer clicking navigation completed
    fun displayAddEventPerformerDetailsCompleted() {
        _navigateToAddEventPerformer.value = null
    }





    // handle event list from fireStore
    private val handleGetEventListResult = fun(document: DocumentSnapshot?, exception: Exception?) {

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

            dataList.add(dataTobeAddToList)
        } else {
            if (exception != null) {
                Log.i("EventTest", "In EventViewModel something goes wrong : $exception")
            }
        }
    }

    // setting event list liveData for adapter after event list result is handled
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("ManagementEventTest", "Event Data List = ${_dataListForAdapter.value}")
    }

    // get event list result
    fun getEventListResult() {
        dataList.clear()
        repository.getEventList(handleGetEventListResult, handleSettingDataList)
    }

    // get event list result of keyword-searching
    fun getSearchedEventListResult(keyword: String) {
        dataList.clear()
        repository.getSearchedEventList(keyword, handleGetEventListResult, handleSettingDataList)
    }

    init {
        getEventListResult()
    }
}