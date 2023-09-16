package com.example.musicchaser.data

data class EventData(
    val eventId : String,
    val eventName : String,
    val eventPlace : String,
    val eventLongitude : Float,
    val eventLatitude : Float,
    val eventAddress : String,
    val eventDate : Long,
    val eventMonth : String,
    val eventDay : String,
    val eventWeather : String,
    val eventArea : String,
    val eventAttendant : Int,
    val eventUrl : String,
    val eventMainPic : String,
    val eventDesc : String,
    val eventPerformers : List<String>,
    val eventComments : Int
)