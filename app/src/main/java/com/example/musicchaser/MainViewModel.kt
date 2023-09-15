package com.example.musicchaser

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.data.source.MusicChaserRepository
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MusicChaserRepository):
    ViewModel() {

        init {
            repository.getUserBasicInfo("3bjQEWKeFmaVxvirIQgz")
        }
}
