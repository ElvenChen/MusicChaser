package com.example.musicchaser.managementuserdetail.edit

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.UserData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementUserDetailEditViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var user: UserData? = null


}