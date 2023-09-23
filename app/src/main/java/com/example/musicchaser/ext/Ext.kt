package com.example.musicchaser.ext


import android.util.Log
import java.sql.Date

import java.text.SimpleDateFormat



fun Long.toFormattedTime() : String {

    val dateFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
    val date = Date(this*1000)

    return dateFormat.format(date)
}

fun Long.toCommentFormattedTime() : String {

    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
    val date = Date(this*1000)

    return dateFormat.format(date)
}

fun Long.toFormattedMonth() : String {

    val dateFormat = SimpleDateFormat("M月")
    val date = Date(this*1000)

    return dateFormat.format(date)
}

fun Long.toFormattedDay() : String {

    val dateFormat = SimpleDateFormat("dd")
    val date = Date(this*1000)

    return dateFormat.format(date)
}

fun Long.toDottedFormattedTime() : String {

    val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")
    val date = Date(this*1000)

    return dateFormat.format(date)
}

fun Long.toFormattedTimeOfSettingInitDatePickerYear() : Int {

    val dateFormat = SimpleDateFormat("yyyy")
    val date = Date(this*1000)

    return dateFormat.format(date).toInt()
}

fun Long.toFormattedTimeOfSettingInitDatePickerMonth() : Int {

    val dateFormat = SimpleDateFormat("M")
    val date = Date(this*1000)

    return dateFormat.format(date).toInt()
}

fun Long.toFormattedTimeOfSettingInitDatePickerDay() : Int {

    val dateFormat = SimpleDateFormat("DD")
    val date = Date(this*1000)

    return dateFormat.format(date).toInt()
}

fun Long.toFormattedTimeOfSettingInitDatePickerHour() : Int {

    val dateFormat = SimpleDateFormat("HH")
    val date = Date(this*1000)

    return dateFormat.format(date).toInt()
}

fun Long.toFormattedTimeOfSettingInitDatePickerMinute() : Int {

    val dateFormat = SimpleDateFormat("mm")
    val date = Date(this*1000)

    return dateFormat.format(date).toInt()
}