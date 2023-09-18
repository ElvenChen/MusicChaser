package com.example.musicchaser.data.source

import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
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

    fun addFavoriteEvent(userId: String, eventId: String, handleSettingEventIsFavorite: () -> Unit)

    fun deleteFavoriteEvent(
        userId: String,
        eventId: String,
        handleSettingEventNotFavorite: () -> Unit
    )

    fun getIfEventIsFavorite(
        userId: String,
        eventId: String,
        handleSettingEventIsFavorite: () -> Unit,
        handleSettingEventNotFavorite: () -> Unit
    )

    fun postCommentForEvent(userId: String, eventId: String, commentContent: String)

    fun addCommentAmounts(eventId: String)

    fun getEventCommentList(
        eventId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun getCommentAuthor(
        eventCommentListWithNoAuthorName: List<EventCommentData>,
        handleCompletedEventCommentListResult: (EventCommentData) -> Unit,
        handleSettingDataList: () -> Unit
    )

    ////////// Artist API //////////
    fun getArtistList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun getSearchedArtistList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun addFavoriteArtist(userId: String, artistId: String, handleSettingArtistIsFavorite: () -> Unit)

    fun deleteFavoriteArtist(
        userId: String,
        artistId: String,
        handleSettingArtistNotFavorite: () -> Unit
    )

    fun getIfArtistIsFavorite(
        userId: String,
        artistId: String,
        handleSettingArtistIsFavorite: () -> Unit,
        handleSettingArtistNotFavorite: () -> Unit
    )
}