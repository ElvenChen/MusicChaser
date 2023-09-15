package com.example.musicchaser.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository):ViewModel() {

    private val _userNickname = MutableLiveData<String>()
    val userNickname: LiveData<String>
        get() = _userNickname


    private val _userAccount = MutableLiveData<String>()
    val userAccount: LiveData<String>
        get() = _userAccount


    init {
        Log.i("UserTest","UserManager.userNickname = ${UserManager.userNickname}")
        Log.i("UserTest","UserManager.userEmail = ${UserManager.userEmail}")
        _userNickname.value = UserManager.userNickname
        _userAccount.value = UserManager.userEmail
    }
}