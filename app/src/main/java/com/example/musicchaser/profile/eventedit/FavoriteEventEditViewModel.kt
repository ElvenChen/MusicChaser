package com.example.musicchaser.profile.eventedit

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteEventEditViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {


    fun nothing() {}
}