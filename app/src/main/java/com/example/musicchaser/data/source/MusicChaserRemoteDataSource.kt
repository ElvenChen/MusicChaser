package com.example.musicchaser.data.source

import android.annotation.SuppressLint
import android.util.Log
import com.example.musicchaser.data.User
import com.example.musicchaser.login.UserManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


private const val COLLECTION_USERS = "users"
private const val COLLECTION_EVENTS = "events"
private const val FIELD_EVENT_NAME = "event_name"
private const val FIELD_EVENT_DATE = "event_date"


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

}