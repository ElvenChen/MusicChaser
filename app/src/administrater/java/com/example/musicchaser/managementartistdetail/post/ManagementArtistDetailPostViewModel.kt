package com.example.musicchaser.managementartistdetail.post

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementArtistDetailPostViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {



}