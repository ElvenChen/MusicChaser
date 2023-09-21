package com.example.musicchaser.managementuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.UserData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementUserViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var dataList = mutableListOf<UserData>()

    private val _dataListForAdapter = MutableLiveData<List<UserData>>()
    val dataListForAdapter: LiveData<List<UserData>>
        get() = _dataListForAdapter



    private val _navigateToEditUser = MutableLiveData<UserData?>()
    val navigateToEditUser: LiveData<UserData?>
        get() = _navigateToEditUser




    // handle edit user clicking navigation
    val displayUserEditDetails = fun(user: UserData) {
        _navigateToEditUser.value = user
    }

    // handle edit user clicking navigation completed
    fun displayUserEditDetailsCompleted() {
        _navigateToEditUser.value = null
    }





    // handle user list from fireStore
    private val handleGetUserListResult = fun(document: DocumentSnapshot?, exception: Exception?) {

        if (document != null) {
            val data = document.data

            val dataTobeAddToList = UserData(
                userId = (data!!["user_id"]).toString(),
                userName = (data["user_name"]).toString(),
                userNickname = (data["user_nickname"]).toString(),
                userEmail = (data["user_email"]).toString(),
                userBanned = (data["user_banned"]).toString().toInt(),
            )

            dataList.add(dataTobeAddToList)
        } else {
            if (exception != null) {
                Log.i("UserTest", "In EventViewModel something goes wrong : $exception")
            }
        }
    }

    // setting user list liveData for adapter after user list result is handled
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("ManagementUserTest", "User Data List = ${_dataListForAdapter.value}")
    }

    // get user list result
    fun getUserListResult() {
        dataList.clear()
        repository.getUserList(handleGetUserListResult, handleSettingDataList)
    }

    // get user list result of keyword-searching
    fun getSearchedUserListResult(keyword: String) {
        dataList.clear()
        repository.getSearchedUserList(keyword, handleGetUserListResult, handleSettingDataList)
    }

    init {
        getUserListResult()
    }
}