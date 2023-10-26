package com.example.musicchaser.managementartistdetail.delete

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementArtistDetailDeleteViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var artist: ArtistData? = null



    fun deleteSelectedArtist(){
        repository.deleteSelectedArtist(artist!!.artistId)
    }



    fun nothing() {}
}