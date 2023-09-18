package com.example.musicchaser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistData(
    val artistId: String,
    val artistName: String,
    val artistDesc: String,
    val artistType: String,
    val artistMainPic: String
) : Parcelable