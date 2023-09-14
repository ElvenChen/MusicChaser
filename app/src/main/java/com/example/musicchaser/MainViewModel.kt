package com.example.musicchaser

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {
        init {
            Log.i("mainViewModel","HIHIHI")

            Log.i("mainViewModel","${repository}")
        }
}
