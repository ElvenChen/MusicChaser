package com.example.musicchaser.managementeventdetail.post

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventDetailPostViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {



}