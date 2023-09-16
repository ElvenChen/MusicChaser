package com.example.musicchaser.data.source

import com.example.musicchaser.data.User
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject
import javax.inject.Singleton

class DefaultMusicChaserRepository @Inject constructor(private val musicChaserRemoteDataSource: MusicChaserDataSource) :
    MusicChaserRepository {

    ////////// Profile API //////////
    // get user basic info
    override fun getUserBasicInfo(userId: String) {
        musicChaserRemoteDataSource.getUserBasicInfo(userId)
    }

    override fun editUserBasicInfo(userId: String, userNickname: String) {
        musicChaserRemoteDataSource.editUserBasicInfo(userId, userNickname)
    }


    ////////// Event API //////////
    override fun getEventList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getEventList(callback, handleSettingDataList)
    }

    override fun getSearchedEventList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getSearchedEventList(keyword,callback,handleSettingDataList)
    }
}