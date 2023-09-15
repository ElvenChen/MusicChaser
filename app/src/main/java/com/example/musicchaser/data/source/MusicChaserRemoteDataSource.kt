package com.example.musicchaser.data.source

import android.annotation.SuppressLint
import android.util.Log
import com.example.musicchaser.data.User
import com.example.musicchaser.login.UserManager
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


private const val COLLECTION_USERS = "users"

@SuppressLint("StaticFieldLeak")
object MusicChaserRemoteDataSource : MusicChaserDataSource {

    private val db = FirebaseFirestore.getInstance()



    ////////// Profile Page API //////////
    // get user basic info
    override fun getUserBasicInfo(userId: String) {
        val docRef = db.collection(COLLECTION_USERS).document(userId)
        docRef.get().addOnCompleteListener { task: Task<DocumentSnapshot> ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    val data = document.data

                    Log.i("UserTest","$data")

                    UserManager.userId = data?.get("user_id").toString()
                    UserManager.userName = data?.get("user_name").toString()
                    UserManager.userNickname = data?.get("user_nickname").toString()
                    UserManager.userEmail = data?.get("user_email").toString()
                    UserManager.userBanned = data?.get("user_banned").toString().toInt()

                } else {
                    Log.i("UserTest","no document of this.")
                }
            } else {
                val exception = task.exception
                if (exception != null) {
                    Log.i("UserTest","something goes wrong !")
                }
            }
        }
    }

    override fun editUserBasicInfo(userId: String, userNickname : String) {
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
        Log.i("UserTest","UserManager = $UserManager")
    }


}