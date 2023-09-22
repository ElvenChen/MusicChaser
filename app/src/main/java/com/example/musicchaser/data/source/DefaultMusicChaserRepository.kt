package com.example.musicchaser.data.source

import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.google.firebase.firestore.CollectionReference
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

    override fun getUserList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getUserList(callback, handleSettingDataList)
    }

    override fun getSearchedUserList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getSearchedUserList(keyword, callback, handleSettingDataList)
    }

    override fun getUserFavoriteEvent(userId: String): CollectionReference {
        return musicChaserRemoteDataSource.getUserFavoriteEvent(userId)
    }

    override fun getCompletedEventList(
        eventIdList: List<String>,
        handleCompletedEventListResult: (EventData) -> Unit,
        handleSettingEventData: () -> Unit
    ) {
        musicChaserRemoteDataSource.getCompletedEventList(
            eventIdList,
            handleCompletedEventListResult,
            handleSettingEventData
        )
    }

    override fun getUserFavoriteArtist(userId: String): CollectionReference {
        return musicChaserRemoteDataSource.getUserFavoriteArtist(userId)
    }

    override fun getCompletedArtistList(
        artistIdList: List<String>,
        handleCompletedArtistListResult: (ArtistData) -> Unit,
        handleSettingArtistData: () -> Unit
    ) {
        musicChaserRemoteDataSource.getCompletedArtistList(
            artistIdList,
            handleCompletedArtistListResult,
            handleSettingArtistData
        )
    }

    override fun deleteUserFavoriteEvent(userId: String, eventId: String) {
        musicChaserRemoteDataSource.deleteUserFavoriteEvent(userId, eventId)
    }

    override fun deleteUserFavoriteArtist(userId: String, artistId: String) {
        musicChaserRemoteDataSource.deleteUserFavoriteArtist(userId, artistId)
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

    override fun getEventPerformerList(
        eventId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingPerformerList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getEventPerformerList(
            eventId,
            callback,
            handleSettingPerformerList
        )
    }

    override fun getEventPerformerName(
        eventPerformerListWithNoArtistName: List<String>,
        handleCompletedEventPerformerListResult: (String) -> Unit,
        handleSettingPerformerDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getEventPerformerName(
            eventPerformerListWithNoArtistName,
            handleCompletedEventPerformerListResult,
            handleSettingPerformerDataList
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
        artistId: String,
        handleSettingArtistIsFavorite: () -> Unit,
        handleSettingArtistNotFavorite: () -> Unit
    ) {
        musicChaserRemoteDataSource.getIfArtistIsFavorite(
            userId,
            artistId,
            handleSettingArtistIsFavorite,
            handleSettingArtistNotFavorite
        )
    }

    override fun getArtistRecentEventList(
        artistId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingRecentEventList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getArtistRecentEventList(
            artistId,
            callback,
            handleSettingRecentEventList
        )
    }

    override fun getRecentEventName(
        dataListWithOnlyEventId: List<String>,
        handleCompletedRecentEventListResult: (EventData) -> Unit,
        handleSettingRecentEventData: () -> Unit
    ) {
        musicChaserRemoteDataSource.getRecentEventName(
            dataListWithOnlyEventId,
            handleCompletedRecentEventListResult,
            handleSettingRecentEventData
        )
    }

    ////////// Management API //////////
    ////////// Management API //////////
    ////////// Management API //////////

    ////////// Management Artist API //////////
    override fun editSelectedArtist(artist: ArtistData) {
        musicChaserRemoteDataSource.editSelectedArtist(artist)
    }

    override fun postNewArtist(artist: ArtistData) {
        musicChaserRemoteDataSource.postNewArtist(artist)
    }

    override fun deleteSelectedArtist(artistId: String) {
        musicChaserRemoteDataSource.deleteSelectedArtist(artistId)
    }
}