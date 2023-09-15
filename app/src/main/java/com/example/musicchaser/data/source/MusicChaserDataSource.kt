package com.example.musicchaser.data.source

import com.example.musicchaser.data.User


interface MusicChaserDataSource {

    fun getUserBasicInfo(userId : String)

    fun editUserBasicInfo(userId : String, userNickname : String)
}