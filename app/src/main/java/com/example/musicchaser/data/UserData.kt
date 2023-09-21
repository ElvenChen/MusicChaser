package com.example.musicchaser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    var userId: String,
    var userName: String,
    var userNickname: String,
    var userEmail: String,
    var userBanned: Int
) : Parcelable

