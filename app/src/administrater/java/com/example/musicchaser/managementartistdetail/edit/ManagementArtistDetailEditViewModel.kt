package com.example.musicchaser.managementartistdetail.edit

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementArtistDetailEditViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var artist: ArtistData? = null



}