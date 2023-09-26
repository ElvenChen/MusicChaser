package com.example.musicchaser.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var dataList = mutableListOf<EventData>()

    private val _dataListForAdapter = MutableLiveData<List<EventData>>()
    val dataListForAdapter: LiveData<List<EventData>>
        get() = _dataListForAdapter

    private val listForGalleryDot = mutableListOf<String>()

    private val _listForGalleryDotAdapter = MutableLiveData<List<String>>()
    val listForGalleryDotAdapter: LiveData<List<String>>
        get() = _listForGalleryDotAdapter

    var galleryDotAmount = 0




//    private val _navigateToSelectedEvent = MutableLiveData<EventData?>()
//    val navigateToSelectedEvent: LiveData<EventData?>
//        get() = _navigateToSelectedEvent
//
//
//    // handle event clicking navigation
//    val displayEventDetails = fun(event: EventData) {
//        _navigateToSelectedEvent.value = event
//    }
//
//    // handle event clicking navigation completed
//    fun displayEventDetailsCompleted() {
//        _navigateToSelectedEvent.value = null
//    }









    // handle hot event list from fireStore
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

    // setting hot event list liveData for adapter after event list result is handled
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("detailDot", "Event Data List = ${_dataListForAdapter.value}")
    }

    // get hot event list result
    private fun getEventListResult() {
        dataList.clear()
        repository.getEventList(handleGetEventListResult, handleSettingDataList)
    }



    fun getListForGalleryDotAdapter(currentItemIndex: Int) {
        listForGalleryDot.clear()

        for (index in 0 until (galleryDotAmount)) {
            listForGalleryDot.add("stroked")
        }
        listForGalleryDot[(currentItemIndex % galleryDotAmount)] = "solid"

        Log.i("detailDot", "$listForGalleryDot")
        _listForGalleryDotAdapter.value = listForGalleryDot
    }


    init {
        getEventListResult()
    }
}