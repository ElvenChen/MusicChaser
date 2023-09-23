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
        val postItem = EventData(
            eventId = "",
            eventName = eventName.value.toString(),
            eventDesc = eventDesc.value.toString(),
            eventPlace = eventPlace.value.toString(),
            eventLongitude = eventLongitude.value.toString().toFloat(),
            eventLatitude = eventLatitude.value.toString().toFloat(),
            eventAddress = eventAddress.value.toString(),
            eventDate = eventDate.value.toString().toLong(),
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