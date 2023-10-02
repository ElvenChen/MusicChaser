package com.example.musicchaser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThreadCommentData(
    val threadCommentAuthorNickname : String,
    val threadCommentTime : Long,
    val threadCommentContent : String,
) : Parcelable