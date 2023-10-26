package com.example.musicchaser.managementeventdetail.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventDetailPostViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    val eventName = MutableLiveData<String>()
    val eventDesc = MutableLiveData<String>()
    val eventPlace = MutableLiveData<String>()
    val eventLongitude = MutableLiveData<String>()
    val eventLatitude = MutableLiveData<String>()
    val eventAddress = MutableLiveData<String>()
    val eventDate = MutableLiveData<Long>()
    val eventArea = MutableLiveData<String>()
    val eventUrl = MutableLiveData<String>()
    val eventMainPic = MutableLiveData<String>()


    fun postNewEvent(){

        val eventLongitudeValue = if(eventLongitude.value == null || eventLongitude.value == ""){
            0.00F
        } else {
            eventLongitude.value.toString().toFloat()
        }

        val eventLatitudeValue = if(eventLatitude.value == null || eventLatitude.value == ""){
            0.00F
        } else {
            eventLatitude.value.toString().toFloat()
        }

        val eventDateValue = if(eventDate.value != null){
            eventDate.value.toString().toLong()
        } else {
            0L
        }


        val postItem = EventData(
            eventId = "",
            eventName = eventName.value.toString(),
            eventDesc = eventDesc.value.toString(),
            eventPlace = eventPlace.value.toString(),
            eventLongitude = eventLongitudeValue,
            eventLatitude = eventLatitudeValue,
            eventAddress = eventAddress.value.toString(),
            eventDate = eventDateValue,
            eventWeather = "",
            eventArea = eventArea.value.toString(),
            eventAttendant = 0,
            eventUrl = eventUrl.value.toString(),
            eventMainPic = eventMainPic.value.toString(),
            eventComments = 0
        )
        Log.i("EventPost","postItem = $postItem")

        repository.postNewEvent(postItem)
    }
}