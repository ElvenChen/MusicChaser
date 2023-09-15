package com.example.musicchaser.profile.basicinfoedit

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicInfoEditViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    val nickname = MutableLiveData<String>()


    fun finishNicknameEdit() {
        repository.editUserBasicInfo(UserManager.userId, nickname.value ?: "")
    }


    fun nothing() {}
}