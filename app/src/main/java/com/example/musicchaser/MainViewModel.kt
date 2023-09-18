package com.example.musicchaser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.MusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MusicChaserRepository):
    ViewModel() {

        init {
            repository.getUserBasicInfo("3bjQEWKeFmaVxvirIQgz")
        }
}
