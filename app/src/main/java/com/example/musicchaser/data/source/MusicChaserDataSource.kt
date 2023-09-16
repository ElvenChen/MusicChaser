package com.example.musicchaser.data.source

import com.example.musicchaser.data.User
import com.google.firebase.firestore.DocumentSnapshot


interface MusicChaserDataSource {

    ////////// Profile API //////////
    fun getUserBasicInfo(userId: String)

    fun editUserBasicInfo(userId: String, userNickname: String)

    ////////// Event API //////////
    fun getEventList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun getSearchedEventList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    )
}