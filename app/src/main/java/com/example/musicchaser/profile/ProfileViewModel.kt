package com.example.musicchaser.profile

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(repository: DefaultMusicChaserRepository):ViewModel() {
}