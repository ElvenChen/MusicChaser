package com.example.musicchaser.managementuserdetail.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.UserData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementUserDetailEditViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var user: UserData? = null

    fun changeSelectedUserBannedSituation(bannedSituation:Int){

        val editedItem = UserData(
            userId = user!!.userId,
            userEmail = user!!.userEmail,
            userName = user!!.userName,
            userNickname = user!!.userNickname,
            userBanned = bannedSituation
        )
        Log.i("UserEdit","editedItem = $editedItem")

        repository.changeSelectedUserBannedSituation(editedItem)
    }


}