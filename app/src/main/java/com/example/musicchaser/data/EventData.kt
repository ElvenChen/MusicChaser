package com.example.musicchaser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventData(
    val eventId : String,
    val eventName : String,
    val eventPlace : String,
    val eventLongitude : Float,
    val eventLatitude : Float,
    val eventAddress : String,
    val eventDate : Long,
    val eventWeather : String,
    val eventArea : String,
    val eventAttendant : Int,
    val eventUrl : String,
    val eventMainPic : String,
    val eventDesc : String,
    val eventComments : Int
) : Parcelable