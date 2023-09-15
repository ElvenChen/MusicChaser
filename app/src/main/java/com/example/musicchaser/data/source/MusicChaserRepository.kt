package com.example.musicchaser.data.source

import com.example.musicchaser.data.User

interface MusicChaserRepository {

    fun getUserBasicInfo(userId : String)

    fun editUserBasicInfo(userId : String, userNickname : String)
}