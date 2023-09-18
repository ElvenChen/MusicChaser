package com.example.musicchaser.data.source

import android.annotation.SuppressLint
import android.util.Log
import com.example.musicchaser.data.EventCommentData
import com.example.musicchaser.login.UserManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


private const val COLLECTION_USERS = "users"
private const val COLLECTION_USERS_SUB_COLLECTION_FAVORITE_EVENTS = "favorite_events"

private const val FIELD_USER_ID = "user_id"


private const val COLLECTION_EVENTS = "events"
private const val COLLECTION_EVENTS_SUB_COLLECTION_EVENT_COMMENTS = "event_comments"

private const val FIELD_EVENT_NAME = "event_name"
private const val FIELD_EVENT_DATE = "event_date"
private const val FIELD_EVENT_COMMENTS = "event_comments"
private const val FIELD_EVENT_COMMENT_TIME = "comment_time"


private const val COLLECTION_ARTISTS = "artists"

private const val FIELD_ARTIST_NAME = "artist_name"



@SuppressLint("StaticFieldLeak")
object MusicChaserRemoteDataSource : MusicChaserDataSource {

    private val db = FirebaseFirestore.getInstance()


    ////////// Profile API //////////
    // get user basic info
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

        collectionRef.whereLessThanOrEqualTo(searchField, keyword)
            .orderBy(searchField, Query.Direction.DESCENDING)
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
                    Log.i("EventTest", "Check isFavorite = NONONO!! ")
                }
            } else {
                val exception = task.exception
                handleSettingEventNotFavorite()
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

        collectionRef.whereLessThanOrEqualTo(searchField, keyword)
            .orderBy(searchField, Query.Direction.DESCENDING)
            .get().addOnSuccessListener { querySnapshot ->

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

}