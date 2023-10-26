package com.example.musicchaser.managementeventdetail.edit

import android.os.Build.VERSION_CODES.M
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventDetailEditViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var event: EventData? = null

    val eventId = MutableLiveData<String>()
    val eventName = MutableLiveData<String>()
    val eventDesc = MutableLiveData<String>()
    val eventPlace = MutableLiveData<String>()
    val eventLongitude = MutableLiveData<String>()
    val eventLatitude = MutableLiveData<String>()
    val eventAddress = MutableLiveData<String>()
    val eventDate = MutableLiveData<Long>()
    val eventWeather = MutableLiveData<String>()
    val eventArea = MutableLiveData<String>()
    val eventAttendant = MutableLiveData<String>()
    val eventUrl = MutableLiveData<String>()
    val eventMainPic = MutableLiveData<String>()
    val eventComments = MutableLiveData<String>()


    fun prepareEditFieldForView(){
        eventId.value = event!!.eventId
        eventName.value = event!!.eventName
        eventDesc.value = event!!.eventDesc
        eventPlace.value = event!!.eventPlace
        eventLongitude.value = event!!.eventLongitude.toString()
        eventLatitude.value = event!!.eventLatitude.toString()
        eventAddress.value = event!!.eventAddress
        eventDate.value = event!!.eventDate
        eventWeather.value = event!!.eventWeather
        eventArea.value = event!!.eventArea
        eventAttendant.value = event!!.eventAttendant.toString()
        eventUrl.value = event!!.eventUrl
        eventMainPic.value = event!!.eventMainPic
        eventComments.value = event!!.eventComments.toString()
    }

    fun editSelectedEvent(){

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

        val editedItem = EventData(
            eventId = event!!.eventId,
            eventName = eventName.value.toString(),
            eventDesc = eventDesc.value.toString(),
            eventPlace = eventPlace.value.toString(),
            eventLongitude = eventLongitudeValue,
            eventLatitude = eventLatitudeValue,
            eventAddress = eventAddress.value.toString(),
            eventDate = eventDateValue,
            eventWeather = event!!.eventWeather,
            eventArea = eventArea.value.toString(),
            eventAttendant = event!!.eventAttendant,
            eventUrl = eventUrl.value.toString(),
            eventMainPic = eventMainPic.value.toString(),
            eventComments = event!!.eventComments
        )
        Log.i("EventEdit","editedItem = $editedItem")

        repository.editSelectedEvent(editedItem)
    }

}