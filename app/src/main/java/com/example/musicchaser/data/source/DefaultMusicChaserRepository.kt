package com.example.musicchaser.data.source

import com.example.musicchaser.data.User
import javax.inject.Inject
import javax.inject.Singleton

class DefaultMusicChaserRepository @Inject constructor( private val musicChaserRemoteDataSource: MusicChaserDataSource ) : MusicChaserRepository {

    ////////// ProfilePage function //////////
    // get user basic info
    override fun getUserBasicInfo(userId: String) {
        musicChaserRemoteDataSource.getUserBasicInfo(userId)
    }

    override fun editUserBasicInfo(userId: String, userNickname : String) {
        musicChaserRemoteDataSource.editUserBasicInfo(userId,userNickname)
    }


}