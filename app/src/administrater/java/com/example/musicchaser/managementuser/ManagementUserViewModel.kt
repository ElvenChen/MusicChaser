package com.example.musicchaser.managementuser

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementUserViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

}