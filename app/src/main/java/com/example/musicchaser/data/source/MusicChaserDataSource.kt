package com.example.musicchaser.data.source

import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.ThreadCommentData
import com.example.musicchaser.data.ThreadData
import com.example.musicchaser.data.UserData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot


interface MusicChaserDataSource {

    ////////// Profile API //////////
    fun getUserBasicInfo(userId: String)

    fun editUserBasicInfo(userId: String, userNickname: String)

    fun getUserList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun getSearchedUserList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun getUserFavoriteEvent(userId: String) : CollectionReference

    fun getCompletedEventList(
        eventIdList: List<String>,
        handleCompletedEventListResult: (EventData) -> Unit,
        handleSettingEventData: () -> Unit
    )

    fun getUserFavoriteArtist(userId: String): CollectionReference

    fun getCompletedArtistList(
        artistIdList: List<String>,
        handleCompletedArtistListResult: (ArtistData) -> Unit,
        handleSettingArtistData: () -> Unit
    )

    fun deleteUserFavoriteEvent(
        userId: String,
        eventId: String,
    )

    fun deleteUserFavoriteArtist(
        userId: String,
        artistId: String,
    )


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

    fun getEventPerformerList(
        eventId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingPerformerList: () -> Unit
    )

    fun getEventPerformerName(
        eventPerformerListWithNoArtistName: List<String>,
        handleCompletedEventPerformerListResult: (String) -> Unit,
        handleSettingPerformerDataList: () -> Unit
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

    fun addFavoriteArtist(
        userId: String,
        artistId: String,
        handleSettingArtistIsFavorite: () -> Unit
    )

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

    fun getArtistRecentEventList(
        artistId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingRecentEventList: () -> Unit
    )

    fun getRecentEventName(
        dataListWithOnlyEventId: List<String>,
        handleCompletedRecentEventListResult: (EventData) -> Unit,
        handleSettingRecentEventData: () -> Unit
    )

    ////////// Society API //////////
    fun getThreadList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataListWithNoAuthorName: () -> Unit
    )

    fun getThreadAuthor(
        threadListWithNoAuthorName: List<ThreadData>,
        handleCompletedThreadListResult: (ThreadData) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun getSearchedThreadList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataListWithNoAuthorName: () -> Unit
    )

    fun postEventSubmission(
        userId: String,
        eventName: String,
        eventUrl: String,
        eventOtherNote: String
    )

    fun postThread(
        threadAuthorId: String,
        threadName: String,
        threadType: String,
        threadContent: String
    )

    fun getThreadComment(threadId: String): CollectionReference

    fun getThreadCommentAuthor(
        threadCommentListWithNoAuthorName: List<ThreadCommentData>,
        handleCompletedThreadCommentListResult: (ThreadCommentData) -> Unit,
        handleSettingDataList: () -> Unit
    )

    fun postCommentForThread(userId: String, threadId: String, commentContent: String)

    fun addThreadCommentAmounts(threadId: String)



    ////////// Management API //////////
    ////////// Management API //////////
    ////////// Management API //////////

    ////////// Management Event API //////////
    fun editSelectedEvent(event: EventData)

    fun postNewEvent(event: EventData)

    fun deleteSelectedEvent(eventId: String)

    fun getEventPerformerArtist(eventId: String): CollectionReference

    fun getFilteredArtistList(
        eventId : String,
        artistIdList: List<ArtistData>,
        handleFilteredArtistListResult: (ArtistData) -> Unit,
        sendFilteredArtistListForAdapter: () -> Unit
    )

    fun deleteEventPerformer(eventId: String, artistId:String)

    fun deleteArtistAttendEvent(artistId:String, eventId: String)

    fun postEventPerformer(eventId: String, artistId: String)

    fun postArtistAttendEvent(artistId: String, eventId: String)

    ////////// Management Artist API //////////

    fun editSelectedArtist(artist: ArtistData)

    fun postNewArtist(artist: ArtistData)

    fun deleteSelectedArtist(artistId: String)

    ////////// Management User API //////////

    fun changeSelectedUserBannedSituation(user: UserData)

}