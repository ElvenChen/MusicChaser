package com.example.musicchaser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThreadData(
    val threadId : String,
    val threadName : String,
    val threadType : String,
    val threadDate : Long,
    val threadAuthor : String,
    val threadContent : String,
    val threadComments : Int
) : Parcelable
