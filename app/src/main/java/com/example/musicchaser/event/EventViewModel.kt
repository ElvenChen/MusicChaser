package com.example.musicchaser.event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.bumptech.glide.request.RequestOptions
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var dataList = mutableListOf<EventData>()

    private val _dataListForAdapter = MutableLiveData<List<EventData>>()
    val dataListForAdapter: LiveData<List<EventData>>
        get() = _dataListForAdapter


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
                eventMonth = (data["event_month"]).toString(),
                eventDay = (data["event_day"]).toString(),
                eventWeather = (data["event_weather"]).toString(),
                eventArea = (data["event_area"]).toString(),
                eventAttendant = (data["event_attendant"]).toString().toInt(),
                eventUrl = (data["event_url"]).toString(),
                eventMainPic = (data["event_main_pic"]).toString(),
                eventDesc = (data["event_desc"]).toString(),
                eventPerformers = (data["event_performers"]) as List<String>,
                eventComments = (data["event_comments"]).toString().toInt()
            )

            dataList.add(dataTobeAddToList)
        } else {
            if (exception != null) {
                Log.i("EventTest", "In EventViewModel something goes wrong : $exception")
            }
        }
    }

    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("EventTest", "Event Data List = ${_dataListForAdapter.value}")
    }

    private fun getEventListResult() {
        dataList.clear()
        repository.getEventList(handleGetEventListResult, handleSettingDataList)
    }

    fun getSearchedEventListResult(keyword: String) {
        dataList.clear()
        repository.getSearchedEventList(keyword,handleGetEventListResult, handleSettingDataList)
    }

    init {
        getEventListResult()
    }
}