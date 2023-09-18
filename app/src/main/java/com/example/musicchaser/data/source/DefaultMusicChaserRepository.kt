package com.example.musicchaser.data.source

import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
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
        musicChaserRemoteDataSource.getSearchedEventList(keyword, callback, handleSettingDataList)
    }

    override fun addFavoriteEvent(
        userId: String,
        eventId: String,
        handleSettingEventIsFavorite: () -> Unit
    ) {
        musicChaserRemoteDataSource.addFavoriteEvent(userId, eventId, handleSettingEventIsFavorite)
    }

    override fun deleteFavoriteEvent(
        userId: String,
        eventId: String,
        handleSettingEventNotFavorite: () -> Unit
    ) {
        musicChaserRemoteDataSource.deleteFavoriteEvent(
            userId,
            eventId,
            handleSettingEventNotFavorite
        )
    }

    override fun getIfEventIsFavorite(
        userId: String,
        eventId: String,
        handleSettingEventIsFavorite: () -> Unit,
        handleSettingEventNotFavorite: () -> Unit
    ) {
        musicChaserRemoteDataSource.getIfEventIsFavorite(
            userId,
            eventId,
            handleSettingEventIsFavorite,
            handleSettingEventNotFavorite
        )
    }

    override fun postCommentForEvent(userId: String, eventId: String, commentContent: String) {
        musicChaserRemoteDataSource.postCommentForEvent(userId, eventId, commentContent)
    }

    override fun addCommentAmounts(eventId: String) {
        musicChaserRemoteDataSource.addCommentAmounts(eventId)
    }

    override fun getEventCommentList(
        eventId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getEventCommentList(eventId, callback, handleSettingDataList)
    }

    override fun getCommentAuthor(
        eventCommentListWithNoAuthorName: List<EventCommentData>,
        handleCompletedEventCommentListResult: (EventCommentData) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getCommentAuthor(
            eventCommentListWithNoAuthorName,
            handleCompletedEventCommentListResult,
            handleSettingDataList
        )
    }

    ////////// Artist API //////////
    override fun getArtistList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getArtistList(callback, handleSettingDataList)
    }

    override fun getSearchedArtistList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getSearchedArtistList(keyword, callback, handleSettingDataList)
    }

    override fun addFavoriteArtist(
        userId: String,
        artistId: String,
        handleSettingArtistIsFavorite: () -> Unit
    ) {
        musicChaserRemoteDataSource.addFavoriteArtist(
            userId,
            artistId,
            handleSettingArtistIsFavorite
        )
    }

    override fun deleteFavoriteArtist(
        userId: String,
        artistId: String,
        handleSettingArtistNotFavorite: () -> Unit
    ) {
        musicChaserRemoteDataSource.deleteFavoriteArtist(
            userId,
            artistId,
            handleSettingArtistNotFavorite
        )
    }

    override fun getIfArtistIsFavorite(
        userId: String,
        artist: String,
        handleSettingArtistIsFavorite: () -> Unit,
        handleSettingArtistNotFavorite: () -> Unit
    ) {
        musicChaserRemoteDataSource.getIfArtistIsFavorite(
            userId,
            artist,
            handleSettingArtistIsFavorite,
            handleSettingArtistNotFavorite
        )
    }
}