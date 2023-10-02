package com.example.musicchaser.data.source

import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.ThreadCommentData
import com.example.musicchaser.data.ThreadData
import com.example.musicchaser.data.UserData
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

    ////////// Society API //////////
    override fun getThreadList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataListWithNoAuthorName: () -> Unit
    ) {
        musicChaserRemoteDataSource.getThreadList(callback, handleSettingDataListWithNoAuthorName)
    }

    override fun getThreadAuthor(
        threadListWithNoAuthorName: List<ThreadData>,
        handleCompletedThreadListResult: (ThreadData) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getThreadAuthor(
            threadListWithNoAuthorName,
            handleCompletedThreadListResult,
            handleSettingDataList
        )
    }

    override fun getSearchedThreadList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataListWithNoAuthorName: () -> Unit
    ) {
        musicChaserRemoteDataSource.getSearchedThreadList(
            keyword,
            callback,
            handleSettingDataListWithNoAuthorName
        )
    }

    override fun postEventSubmission(
        userId: String,
        eventName: String,
        eventUrl: String,
        eventOtherNote: String
    ) {
        musicChaserRemoteDataSource.postEventSubmission(userId, eventName, eventUrl, eventOtherNote)
    }

    override fun postThread(
        threadAuthorId: String,
        threadName: String,
        threadType: String,
        threadContent: String
    ) {
        musicChaserRemoteDataSource.postThread(
            threadAuthorId,
            threadName,
            threadType,
            threadContent
        )
    }

    override fun getThreadComment(threadId: String): CollectionReference {
        return musicChaserRemoteDataSource.getThreadComment(threadId)
    }

    override fun getThreadCommentAuthor(
        threadCommentListWithNoAuthorName: List<ThreadCommentData>,
        handleCompletedThreadCommentListResult: (ThreadCommentData) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        musicChaserRemoteDataSource.getThreadCommentAuthor(
            threadCommentListWithNoAuthorName,
            handleCompletedThreadCommentListResult,
            handleSettingDataList
        )
    }

    override fun postCommentForThread(userId: String, threadId: String, commentContent: String) {
        musicChaserRemoteDataSource.postCommentForThread(userId, threadId, commentContent)
    }

    override fun addThreadCommentAmounts(threadId: String) {
        musicChaserRemoteDataSource.addThreadCommentAmounts(threadId)
    }


    ////////// Management API //////////
    ////////// Management API //////////
    ////////// Management API //////////

    ////////// Management Event API //////////
    override fun editSelectedEvent(event: EventData) {
        musicChaserRemoteDataSource.editSelectedEvent(event)
    }

    override fun postNewEvent(event: EventData) {
        musicChaserRemoteDataSource.postNewEvent(event)
    }

    override fun deleteSelectedEvent(eventId: String) {
        musicChaserRemoteDataSource.deleteSelectedEvent(eventId)
    }

    override fun getEventPerformerArtist(eventId: String): CollectionReference {
        return musicChaserRemoteDataSource.getEventPerformerArtist(eventId)
    }

    override fun getFilteredArtistList(
        eventId: String,
        artistIdList: List<ArtistData>,
        handleFilteredArtistListResult: (ArtistData) -> Unit,
        sendFilteredArtistListForAdapter: () -> Unit
    ) {
        musicChaserRemoteDataSource.getFilteredArtistList(
            eventId,
            artistIdList,
            handleFilteredArtistListResult,
            sendFilteredArtistListForAdapter
        )
    }

    override fun deleteEventPerformer(eventId: String, artistId: String) {
        musicChaserRemoteDataSource.deleteEventPerformer(eventId, artistId)
    }

    override fun deleteArtistAttendEvent(artistId: String, eventId: String) {
        musicChaserRemoteDataSource.deleteArtistAttendEvent(artistId, eventId)
    }

    override fun postEventPerformer(eventId: String, artistId: String) {
        musicChaserRemoteDataSource.postEventPerformer(eventId, artistId)
    }

    override fun postArtistAttendEvent(artistId: String, eventId: String) {
        musicChaserRemoteDataSource.postArtistAttendEvent(artistId, eventId)
    }

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

    ////////// Management User API //////////

    override fun changeSelectedUserBannedSituation(user: UserData) {
        musicChaserRemoteDataSource.changeSelectedUserBannedSituation(user)
    }
}