package com.example.musicchaser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventCommentData(
    val eventCommentAuthorNickname : String,
    val eventCommentTime : Long,
    val eventCommentContent : String,
) : Parcelable