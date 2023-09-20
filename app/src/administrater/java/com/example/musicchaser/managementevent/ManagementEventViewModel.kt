package com.example.musicchaser.managementevent

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

}