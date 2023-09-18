package com.example.musicchaser.artistdetail

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel  @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {
    var artist: ArtistData? = null

    fun nothing() {}
}