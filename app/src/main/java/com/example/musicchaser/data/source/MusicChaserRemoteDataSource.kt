package com.example.musicchaser.data.source

import android.annotation.SuppressLint
import android.util.Log
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.UserData
import com.example.musicchaser.login.UserManager
import com.example.musicchaser.login.UserManager.userId
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.checkerframework.checker.units.qual.C


private const val COLLECTION_USERS = "users"
private const val COLLECTION_USERS_SUB_COLLECTION_FAVORITE_EVENTS = "favorite_events"
private const val COLLECTION_USERS_SUB_COLLECTION_FAVORITE_ARTISTS = "favorite_artists"

private const val FIELD_USER_ID = "user_id"
private const val FIELD_USER_NAME = "user_name"


private const val COLLECTION_EVENTS = "events"
private const val COLLECTION_EVENTS_SUB_COLLECTION_EVENT_COMMENTS = "event_comments"
private const val COLLECTION_EVENTS_SUB_COLLECTION_EVENT_PERFORMERS = "event_performers"

private const val FIELD_EVENT_ID = "event_id"
private const val FIELD_EVENT_NAME = "event_name"
private const val FIELD_EVENT_DESC = "event_desc"
private const val FIELD_EVENT_PLACE = "event_place"
private const val FIELD_EVENT_LONGITUDE = "event_longitude"
private const val FIELD_EVENT_LATITUDE = "event_latitude"
private const val FIELD_EVENT_ADDRESS = "event_address"
private const val FIELD_EVENT_DATE = "event_date"
private const val FIELD_EVENT_AREA = "event_area"
private const val FIELD_EVENT_URL = "event_url"
private const val FIELD_EVENT_MAIN_PIC = "event_main_pic"
private const val FIELD_EVENT_COMMENTS = "event_comments"
private const val FIELD_EVENT_COMMENT_TIME = "comment_time"
private const val FIELD_ATTEND_ARTIST_ID = "attend_artist_id"


private const val COLLECTION_ARTISTS = "artists"
private const val COLLECTION_ARTISTS_SUB_COLLECTION_ATTEND_EVENTS = "attend_events"

private const val FIELD_ARTIST_ID = "artist_id"
private const val FIELD_ARTIST_NAME = "artist_name"


@SuppressLint("StaticFieldLeak")
object MusicChaserRemoteDataSource : MusicChaserDataSource {

    private val db = FirebaseFirestore.getInstance()


    ////////// Profile API //////////
    override fun getUserBasicInfo(userId: String) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
        docRef.get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val data = document.data

                    Log.i("UserTest", "$data")

                    UserManager.userId = data?.get("user_id").toString()
                    UserManager.userName = data?.get("user_name").toString()
                    UserManager.userNickname = data?.get("user_nickname").toString()
                    UserManager.userEmail = data?.get("user_email").toString()
                    UserManager.userBanned = data?.get("user_banned").toString().toInt()

                } else {
                    Log.i("UserTest", "no document of this.")
                }
            } else {
                val exception = task.exception
                if (exception != null) {
                    Log.i("UserTest", "something goes wrong !")
                }
            }
        }
    }

    override fun editUserBasicInfo(userId: String, userNickname: String) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)

        val data = hashMapOf(
            "user_id" to UserManager.userId,
            "user_name" to UserManager.userName,
            "user_nickname" to userNickname,
            "user_email" to UserManager.userEmail,
            "user_banned" to UserManager.userBanned
        )

        docRef.set(data)

        UserManager.userNickname = userNickname
        Log.i("UserTest", "UserManager = $UserManager")
    }

    override fun getUserList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_USERS)

        collectionRef.get().addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot.documents) {
                val data = document.data

                Log.i("UserTest", "New User is here : $data")
                callback(document, null)
            }

            handleSettingDataList()
        }
            .addOnFailureListener { exception ->
                Log.i("UserTest", "Something goes wrong")
                callback(null, exception)
            }
    }

    override fun getSearchedUserList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_USERS)
        val searchField = FIELD_USER_NAME
        Log.i("UserTest", "keyword : $keyword")

        collectionRef.get().addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot.documents) {
                val data = document.data

                if (((data!!["user_name"]).toString()).contains(keyword)) {
                    Log.i("UserTest", "New User is here : $data")
                    callback(document, null)
                }
            }
            handleSettingDataList()
        }
            .addOnFailureListener { exception ->
                Log.i("UserTest", "Something goes wrong")
                callback(null, exception)
            }
    }

    override fun getUserFavoriteEvent(userId: String): CollectionReference {
        return db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_EVENTS)
    }

    override fun getCompletedEventList(
        eventIdList: List<String>,
        handleCompletedEventListResult: (EventData) -> Unit,
        handleSettingEventData: () -> Unit
    ) {
        if (eventIdList.isEmpty()) {
            handleSettingEventData()
        } else {
            eventIdList.forEach {
                val collectionRef = db.collection(COLLECTION_EVENTS)
                val searchField = FIELD_EVENT_ID

                collectionRef.whereEqualTo(searchField, it)
                    .get().addOnSuccessListener { querySnapshot ->

                        for (document in querySnapshot.documents) {
                            val data = document.data
                            Log.i("UserFavoriteEvent", "Event Content =  $data")

                            val dataTobeAddToList = EventData(
                                eventId = (data!!["event_id"]).toString(),
                                eventName = (data["event_name"]).toString(),
                                eventPlace = (data["event_place"]).toString(),
                                eventLongitude = (data["event_longitude"]).toString().toFloat(),
                                eventLatitude = (data["event_latitude"]).toString().toFloat(),
                                eventAddress = (data["event_address"]).toString(),
                                eventDate = (data["event_date"]).toString().toLong(),
                                eventWeather = (data["event_weather"]).toString(),
                                eventArea = (data["event_area"]).toString(),
                                eventAttendant = (data["event_attendant"]).toString().toInt(),
                                eventUrl = (data["event_url"]).toString(),
                                eventMainPic = (data["event_main_pic"]).toString(),
                                eventDesc = (data["event_desc"]).toString(),
                                eventComments = (data["event_comments"]).toString().toInt()
                            )

                            handleCompletedEventListResult(dataTobeAddToList)
                        }
                        handleSettingEventData()
                    }
                    .addOnFailureListener { exception ->
                        Log.i("UserFavoriteEvent", "Something goes wrong")
                    }
            }
        }
    }

    override fun getUserFavoriteArtist(userId: String): CollectionReference {
        return db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_ARTISTS)
    }

    override fun getCompletedArtistList(
        artistIdList: List<String>,
        handleCompletedArtistListResult: (ArtistData) -> Unit,
        handleSettingArtistData: () -> Unit
    ) {
        if (artistIdList.isEmpty()) {
            handleSettingArtistData()
        } else {
            artistIdList.forEach {
                val collectionRef = db.collection(COLLECTION_ARTISTS)
                val searchField = FIELD_ARTIST_ID

                collectionRef.whereEqualTo(searchField, it)
                    .get().addOnSuccessListener { querySnapshot ->

                        for (document in querySnapshot.documents) {
                            val data = document.data
                            Log.i("UserFavoriteArtist", "Artist Content =  $data")

                            val dataTobeAddToList = ArtistData(
                                artistId = (data!!["artist_id"]).toString(),
                                artistName = (data["artist_name"]).toString(),
                                artistDesc = (data["artist_desc"]).toString(),
                                artistType = (data["artist_type"]).toString(),
                                artistMainPic = (data["artist_main_pic"]).toString()
                            )

                            handleCompletedArtistListResult(dataTobeAddToList)
                        }
                        handleSettingArtistData()
                    }
                    .addOnFailureListener { exception ->
                        Log.i("UserFavoriteArtist", "Something goes wrong")
                    }
            }
        }
    }

    override fun deleteUserFavoriteEvent(userId: String, eventId: String) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_EVENTS).document(eventId)

        docRef.delete().addOnSuccessListener {
            Log.i("UserFavoriteEventEdit", "delete favorite Event successfully!! ")
        }.addOnFailureListener { e ->
            Log.i("UserFavoriteEventEdit", "delete favorite Event fail!! ")
        }
    }

    override fun deleteUserFavoriteArtist(userId: String, artistId: String) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_ARTISTS).document(artistId)

        docRef.delete().addOnSuccessListener {
            Log.i("UserFavoriteArtistEdit", "delete favorite Artistsuccessfully!! ")
        }.addOnFailureListener { e ->
            Log.i("UserFavoriteArtistEdit", "delete favorite Artistfail!! ")
        }
    }


    ////////// Event API //////////
    override fun getEventList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_EVENTS)

        collectionRef.orderBy(FIELD_EVENT_DATE, Query.Direction.DESCENDING)
            .get().addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    val data = document.data

                    Log.i("EventTest", "New Event is here : $data")
                    callback(document, null)
                }

                handleSettingDataList()
            }
            .addOnFailureListener { exception ->
                Log.i("EventTest", "Something goes wrong")
                callback(null, exception)
            }
    }

    override fun getSearchedEventList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_EVENTS)
        val searchField = FIELD_EVENT_NAME
        Log.i("EventTest", "keyword : $keyword")

        collectionRef.get().addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot.documents) {
                val data = document.data

                if (((data!!["event_name"]).toString()).contains(keyword)) {
                    Log.i("EventTest", "New Event is here : $data")
                    callback(document, null)
                }
            }
            handleSettingDataList()
        }
            .addOnFailureListener { exception ->
                Log.i("EventTest", "Something goes wrong")
                callback(null, exception)
            }
    }

    override fun addFavoriteEvent(
        userId: String,
        eventId: String,
        handleSettingEventIsFavorite: () -> Unit
    ) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_EVENTS).document(eventId)

        val data = hashMapOf(
            "event_id" to eventId
        )

        docRef.set(data).addOnSuccessListener {
            handleSettingEventIsFavorite()
            Log.i("EventTest", "add favorite successfully!! ")
        }.addOnFailureListener { e ->
            Log.i("EventTest", "add favorite fail!! ")
        }
    }

    override fun deleteFavoriteEvent(
        userId: String,
        eventId: String,
        handleSettingEventNotFavorite: () -> Unit
    ) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_EVENTS).document(eventId)

        docRef.delete().addOnSuccessListener {
            handleSettingEventNotFavorite()
            Log.i("EventTest", "delete favorite successfully!! ")
        }.addOnFailureListener { e ->
            Log.i("EventTest", "delete favorite fail!! ")
        }
    }

    override fun getIfEventIsFavorite(
        userId: String,
        eventId: String,
        handleSettingEventIsFavorite: () -> Unit,
        handleSettingEventNotFavorite: () -> Unit
    ) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_EVENTS).document(eventId)

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot.exists()) {
                    val data = documentSnapshot.data
                    handleSettingEventIsFavorite()
                    Log.i("EventTest", "Check isFavorite = YES!!   data = $data ")
                } else {
                    handleSettingEventNotFavorite()
                    Log.i("EventTest", "Check isFavorite = NONONO!! ")
                }
            } else {
                val exception = task.exception
                Log.i("EventTest", "Something goes wrong")
            }
        }
    }

    override fun postCommentForEvent(userId: String, eventId: String, commentContent: String) {
        val docRef = db.collection(COLLECTION_EVENTS).document(eventId)
            .collection(COLLECTION_EVENTS_SUB_COLLECTION_EVENT_COMMENTS).document()

        val data = hashMapOf(
            "comment_id" to docRef.id,
            "comment_author_id" to userId,
            "comment_time" to FieldValue.serverTimestamp(),
            "comment_content" to commentContent
        )

        docRef.set(data)
    }

    override fun addCommentAmounts(eventId: String) {
        val docRef = db.collection(COLLECTION_EVENTS).document(eventId)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val currentCommentsAmount = documentSnapshot.getLong(FIELD_EVENT_COMMENTS)

                if (currentCommentsAmount != null) {
                    val updatedAmount = currentCommentsAmount + 1

                    val updates = hashMapOf<String, Any>(
                        FIELD_EVENT_COMMENTS to updatedAmount
                    )

                    docRef.update(updates)
                        .addOnSuccessListener {
                            Log.i("EventTest", "This field is updated successfully!!")
                        }
                        .addOnFailureListener { e ->
                            Log.i("EventTest", "This field is fail to update")
                        }
                } else {
                    Log.i("EventTest", "This field is null, can't be updated")
                }
            } else {
                Log.i("EventTest", "This event is not exist")
            }
        }
            .addOnFailureListener { e ->
                Log.i("EventTest", "Something goes wrong")
            }
    }

    override fun getEventCommentList(
        eventId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_EVENTS).document(eventId)
            .collection(COLLECTION_EVENTS_SUB_COLLECTION_EVENT_COMMENTS)

        collectionRef.orderBy(FIELD_EVENT_COMMENT_TIME, Query.Direction.DESCENDING)
            .get().addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    val data = document.data

                    Log.i("EventTest", "New Comment is here : $data")
                    callback(document, null)
                }
                handleSettingDataList()
            }
            .addOnFailureListener { exception ->
                Log.i("EventTest", "Something goes wrong")
                callback(null, exception)
            }
    }

    override fun getCommentAuthor(
        eventCommentListWithNoAuthorName: List<EventCommentData>,
        handleCompletedEventCommentListResult: (EventCommentData) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        eventCommentListWithNoAuthorName.forEach {
            val collectionRef = db.collection(COLLECTION_USERS)
            val searchField = FIELD_USER_ID

            collectionRef.whereEqualTo(searchField, it.eventCommentAuthorNickname)
                .get().addOnSuccessListener { querySnapshot ->

                    for (document in querySnapshot.documents) {
                        val data = document.data

                        Log.i("EventTest", "Author Content =  $data")

                        val dataTobeAddToList = EventCommentData(
                            eventCommentAuthorNickname = (data!!["user_nickname"]).toString(),
                            eventCommentTime = it.eventCommentTime,
                            eventCommentContent = it.eventCommentContent
                        )
                        handleCompletedEventCommentListResult(dataTobeAddToList)
                    }
                    handleSettingDataList()
                }
                .addOnFailureListener { exception ->
                    Log.i("EventTest", "Something goes wrong")
                }
        }
    }

    override fun getEventPerformerList(
        eventId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingPerformerList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_EVENTS).document(eventId)
            .collection(COLLECTION_EVENTS_SUB_COLLECTION_EVENT_PERFORMERS)

        collectionRef.get().addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot.documents) {
                val data = document.data

                Log.i("EventPerformerTest", "New Performer is here : $data")
                callback(document, null)
            }
            handleSettingPerformerList()
        }.addOnFailureListener { exception ->
            Log.i("EventPerformerTest", "Something goes wrong")
            callback(null, exception)
        }
    }

    override fun getEventPerformerName(
        eventPerformerListWithNoArtistName: List<String>,
        handleCompletedEventPerformerListResult: (String) -> Unit,
        handleSettingPerformerDataList: () -> Unit
    ) {
        eventPerformerListWithNoArtistName.forEach {
            val collectionRef = db.collection(COLLECTION_ARTISTS)
            val searchField = FIELD_ARTIST_ID

            collectionRef.whereEqualTo(searchField, it)
                .get().addOnSuccessListener { querySnapshot ->

                    for (document in querySnapshot.documents) {
                        val data = document.data
                        Log.i("EventPerformerTest", "Artist Content =  $data")

                        val dataTobeAddToList = (data!!["artist_name"]).toString()

                        handleCompletedEventPerformerListResult(dataTobeAddToList)
                    }
                    handleSettingPerformerDataList()
                }
                .addOnFailureListener { exception ->
                    Log.i("EventPerformerTest", "Something goes wrong")
                }
        }
    }

    ////////// Artist API //////////
    override fun getArtistList(
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_ARTISTS)

        collectionRef.get().addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot.documents) {
                val data = document.data

                Log.i("ArtistTest", "New Artist is here : $data")
                callback(document, null)
            }

            handleSettingDataList()
        }
            .addOnFailureListener { exception ->
                Log.i("ArtistTest", "Something goes wrong")
                callback(null, exception)
            }
    }

    override fun getSearchedArtistList(
        keyword: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingDataList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_ARTISTS)
        val searchField = FIELD_ARTIST_NAME
        Log.i("ArtistTest", "keyword : $keyword")

        collectionRef.get().addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot.documents) {
                val data = document.data

                if (((data!!["artist_name"]).toString()).contains(keyword)) {
                    Log.i("ArtistTest", "New Artist is here : $data")
                    callback(document, null)
                }
            }
            handleSettingDataList()
        }
            .addOnFailureListener { exception ->
                Log.i("ArtistTest", "Something goes wrong")
                callback(null, exception)
            }
    }

    override fun addFavoriteArtist(
        userId: String,
        artistId: String,
        handleSettingArtistIsFavorite: () -> Unit
    ) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_ARTISTS).document(artistId)

        val data = hashMapOf(
            "artist_id" to artistId
        )

        docRef.set(data).addOnSuccessListener {
            handleSettingArtistIsFavorite()
            Log.i("ArtistTest", "add artist successfully!! ")
        }.addOnFailureListener { e ->
            Log.i("ArtistTest", "add artist fail!! ")
        }
    }

    override fun deleteFavoriteArtist(
        userId: String,
        artistId: String,
        handleSettingArtistNotFavorite: () -> Unit
    ) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_ARTISTS).document(artistId)

        docRef.delete().addOnSuccessListener {
            handleSettingArtistNotFavorite()
            Log.i("ArtistTest", "delete artist successfully!! ")
        }.addOnFailureListener { e ->
            Log.i("ArtistTest", "delete artist fail!! ")
        }
    }

    override fun getIfArtistIsFavorite(
        userId: String,
        artistId: String,
        handleSettingArtistIsFavorite: () -> Unit,
        handleSettingArtistNotFavorite: () -> Unit
    ) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
            .collection(COLLECTION_USERS_SUB_COLLECTION_FAVORITE_ARTISTS).document(artistId)

        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val documentSnapshot = task.result
                if (documentSnapshot.exists()) {
                    val data = documentSnapshot.data
                    handleSettingArtistIsFavorite()
                    Log.i("ArtistTest", "Check isFavorite = YES!!   data = $data ")
                } else {
                    handleSettingArtistNotFavorite()
                    Log.i("ArtistTest", "Check isFavorite = NONONO!! ")
                }
            } else {
                val exception = task.exception
                Log.i("ArtistTest", "Something goes wrong")
            }
        }
    }

    override fun getArtistRecentEventList(
        artistId: String,
        callback: (DocumentSnapshot?, Exception?) -> Unit,
        handleSettingRecentEventList: () -> Unit
    ) {
        val collectionRef = db.collection(COLLECTION_ARTISTS).document(artistId)
            .collection(COLLECTION_ARTISTS_SUB_COLLECTION_ATTEND_EVENTS)

        collectionRef.get().addOnSuccessListener { querySnapshot ->

            for (document in querySnapshot.documents) {
                val data = document.data

                Log.i("ArtistRecentEventTest", "Recent event is here : $data")
                callback(document, null)
            }
            handleSettingRecentEventList()
        }.addOnFailureListener { exception ->
            Log.i("ArtistRecentEventTest", "Something goes wrong")
            callback(null, exception)
        }
    }

    override fun getRecentEventName(
        dataListWithOnlyEventId: List<String>,
        handleCompletedRecentEventListResult: (EventData) -> Unit,
        handleSettingRecentEventData: () -> Unit
    ) {
        dataListWithOnlyEventId.forEach {
            val collectionRef = db.collection(COLLECTION_EVENTS)
            val searchField = FIELD_EVENT_ID

            collectionRef.whereEqualTo(searchField, it)
                .get().addOnSuccessListener { querySnapshot ->

                    for (document in querySnapshot.documents) {
                        val data = document.data
                        Log.i("ArtistRecentEventTest", "Recent event Content =  $data")

                        val dataTobeAddToList = EventData(
                            eventId = (data!!["event_id"]).toString(),
                            eventName = (data["event_name"]).toString(),
                            eventPlace = (data["event_place"]).toString(),
                            eventLongitude = (data["event_longitude"]).toString().toFloat(),
                            eventLatitude = (data["event_latitude"]).toString().toFloat(),
                            eventAddress = (data["event_address"]).toString(),
                            eventDate = (data["event_date"]).toString().toLong(),
                            eventWeather = (data["event_weather"]).toString(),
                            eventArea = (data["event_area"]).toString(),
                            eventAttendant = (data["event_attendant"]).toString().toInt(),
                            eventUrl = (data["event_url"]).toString(),
                            eventMainPic = (data["event_main_pic"]).toString(),
                            eventDesc = (data["event_desc"]).toString(),
                            eventComments = (data["event_comments"]).toString().toInt()
                        )

                        handleCompletedRecentEventListResult(dataTobeAddToList)
                    }
                    handleSettingRecentEventData()
                }
                .addOnFailureListener { exception ->
                    Log.i("ArtistRecentEventTest", "Something goes wrong")
                }
        }
    }


    ////////// Management API //////////
    ////////// Management API //////////
    ////////// Management API //////////

    ////////// Management Event API //////////
    override fun editSelectedEvent(event: EventData) {
        val docRef = db.collection(COLLECTION_EVENTS).document(event.eventId)

        docRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {

                if (documentSnapshot != null) {

                    val updates = hashMapOf<String, Any>(
                        FIELD_EVENT_NAME to event.eventName,
                        FIELD_EVENT_DESC to event.eventDesc,
                        FIELD_EVENT_PLACE to event.eventPlace,
                        FIELD_EVENT_LONGITUDE to event.eventLongitude,
                        FIELD_EVENT_LATITUDE to event.eventLatitude,
                        FIELD_EVENT_ADDRESS to event.eventAddress,
                        FIELD_EVENT_DATE to event.eventDate,
                        FIELD_EVENT_AREA to event.eventArea,
                        FIELD_EVENT_URL to event.eventUrl,
                        FIELD_EVENT_MAIN_PIC to event.eventMainPic
                    )

                    docRef.update(updates)
                        .addOnSuccessListener {
                            Log.i("EventTest", "This field is updated successfully!!")
                        }
                        .addOnFailureListener { e ->
                            Log.i("EventTest", "This field is fail to update")
                        }
                } else {
                    Log.i("EventTest", "This field is null, can't be updated")
                }
            } else {
                Log.i("EventTest", "This event is not exist")
            }
        }
            .addOnFailureListener { e ->
                Log.i("EventTest", "Something goes wrong")
            }
    }

    override fun postNewEvent(event: EventData) {
        val docRef = db.collection(COLLECTION_EVENTS).document()

        val data = hashMapOf(
            "event_id" to docRef.id,
            "event_name" to event.eventName,
            "event_desc" to event.eventDesc,
            "event_place" to event.eventPlace,
            "event_longitude" to event.eventLongitude,
            "event_latitude" to event.eventLatitude,
            "event_address" to event.eventAddress,
            "event_date" to event.eventDate,
            "event_weather" to event.eventWeather,
            "event_area" to event.eventArea,
            "event_attendant" to event.eventAttendant,
            "event_url" to event.eventUrl,
            "event_main_pic" to event.eventMainPic,
            "event_comments" to event.eventComments
        )

        docRef.set(data).addOnSuccessListener {
            Log.i("EventPost", "Post Successfully")
        }.addOnFailureListener {
            Log.i("EventPost", "Post fail")
        }
    }

    override fun deleteSelectedEvent(eventId: String) {
        val docRef = db.collection(COLLECTION_EVENTS).document(eventId)

        docRef.delete().addOnSuccessListener {
            Log.i("EventDelete", "Delete Successfully")
        }.addOnFailureListener { e ->
            Log.i("EventDelete", "Delete fail")
        }
    }

    override fun getEventPerformerArtist(eventId: String): CollectionReference {
        return db.collection(COLLECTION_EVENTS).document(eventId)
            .collection(COLLECTION_EVENTS_SUB_COLLECTION_EVENT_PERFORMERS)
    }

    override fun getFilteredArtistList(
        eventId: String,
        artistIdList: List<ArtistData>,
        handleFilteredArtistListResult: (ArtistData) -> Unit,
        sendFilteredArtistListForAdapter: () -> Unit
    ) {
        if (artistIdList.isEmpty()) {
            sendFilteredArtistListForAdapter()
        } else {
            artistIdList.forEach {
                val docRef = db.collection(COLLECTION_EVENTS).document(eventId).collection(
                    COLLECTION_EVENTS_SUB_COLLECTION_EVENT_PERFORMERS
                ).document(it.artistId)

                docRef.get().addOnSuccessListener { querySnapshot ->

                    if (querySnapshot.data == null) {
                        handleFilteredArtistListResult(it)
                        Log.i(
                            "AddPerformerTest",
                            "Performer is not yet added to the event : ${it.artistName}"
                        )
                    } else {
                        Log.i(
                            "AddPerformerTest",
                            "This performer has attend this event already : ${it.artistName}"
                        )
                    }
                    sendFilteredArtistListForAdapter()
                }
                    .addOnFailureListener { exception ->
                        Log.i("AddPerformerTest", "Something goes wrong")
                    }
            }
        }
    }

    override fun deleteEventPerformer(eventId: String, artistId: String) {
        val docRef = db.collection(COLLECTION_EVENTS).document(eventId).collection(
            COLLECTION_EVENTS_SUB_COLLECTION_EVENT_PERFORMERS
        ).document(artistId)

        docRef.delete().addOnSuccessListener {
            Log.i("PerformerDelete", "Delete Performer Successfully")
        }.addOnFailureListener { e ->
            Log.i("PerformerDelete", "Delete Performer fail")
        }
    }

    override fun deleteArtistAttendEvent(artistId: String, eventId: String) {
        val docRef = db.collection(COLLECTION_ARTISTS).document(artistId).collection(
            COLLECTION_ARTISTS_SUB_COLLECTION_ATTEND_EVENTS
        ).document(eventId)

        docRef.delete().addOnSuccessListener {
            Log.i("PerformerDelete", "Delete Artist attend event Successfully")
        }.addOnFailureListener { e ->
            Log.i("PerformerDelete", "Delete Artist attend event fail")
        }
    }

    override fun postEventPerformer(eventId: String, artistId: String) {
        val docRef = db.collection(COLLECTION_EVENTS).document(eventId).collection(
            COLLECTION_EVENTS_SUB_COLLECTION_EVENT_PERFORMERS).document(artistId)

        val data = hashMapOf(
            "attend_artist_id" to artistId
        )

        docRef.set(data).addOnSuccessListener {
            Log.i("PerformerPost", "Post Performer Successfully")
        }.addOnFailureListener {
            Log.i("PerformerPost", "Post Performer fail")
        }
    }

    override fun postArtistAttendEvent(artistId: String, eventId: String) {
        val docRef = db.collection(COLLECTION_ARTISTS).document(artistId).collection(
            COLLECTION_ARTISTS_SUB_COLLECTION_ATTEND_EVENTS).document(eventId)

        val data = hashMapOf(
            "attend_event_id" to eventId
        )

        docRef.set(data).addOnSuccessListener {
            Log.i("PerformerPost", "Post Artist attend event Successfully")
        }.addOnFailureListener {
            Log.i("PerformerPost", "Post Artist attend event fail")
        }
    }


    ////////// Management Artist API //////////

    override fun editSelectedArtist(artist: ArtistData) {
        val docRef = db.collection(COLLECTION_ARTISTS).document(artist.artistId)

        val data = hashMapOf(
            "artist_id" to artist.artistId,
            "artist_name" to artist.artistName,
            "artist_desc" to artist.artistDesc,
            "artist_type" to artist.artistType,
            "artist_main_pic" to artist.artistMainPic
        )

        docRef.set(data).addOnSuccessListener {
            Log.i("ArtistEdit", "Edit Successfully")
        }.addOnFailureListener {
            Log.i("ArtistEdit", "Edit fail")
        }
    }

    override fun postNewArtist(artist: ArtistData) {
        val docRef = db.collection(COLLECTION_ARTISTS).document()

        val data = hashMapOf(
            "artist_id" to docRef.id,
            "artist_name" to artist.artistName,
            "artist_desc" to artist.artistDesc,
            "artist_type" to artist.artistType,
            "artist_main_pic" to artist.artistMainPic
        )

        docRef.set(data).addOnSuccessListener {
            Log.i("ArtistPost", "Post Successfully")
        }.addOnFailureListener {
            Log.i("ArtistPost", "Post fail")
        }
    }

    override fun deleteSelectedArtist(artistId: String) {
        val docRef = db.collection(COLLECTION_ARTISTS).document(artistId)

        docRef.delete().addOnSuccessListener {
            Log.i("ArtistDelete", "Delete Successfully")
        }.addOnFailureListener { e ->
            Log.i("ArtistDelete", "Delete fail")
        }
    }

    ////////// Management User API //////////

    override fun changeSelectedUserBannedSituation(user: UserData) {
        val docRef = db.collection(COLLECTION_USERS).document(user.userId)

        val data = hashMapOf(
            "user_id" to user.userId,
            "user_email" to user.userEmail,
            "user_name" to user.userName,
            "user_nickname" to user.userNickname,
            "user_banned" to user.userBanned
        )

        docRef.set(data).addOnSuccessListener {
            Log.i("UserEdit", "Edit Successfully")
        }.addOnFailureListener {
            Log.i("UserEdit", "Edit fail")
        }
    }

}