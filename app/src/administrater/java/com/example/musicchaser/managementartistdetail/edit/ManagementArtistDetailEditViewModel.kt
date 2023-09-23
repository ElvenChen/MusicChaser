package com.example.musicchaser.managementartistdetail.edit

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementArtistDetailEditViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var artist: ArtistData? = null

    val artistId = MutableLiveData<String>()
    val artistName = MutableLiveData<String>()
    val artistDesc = MutableLiveData<String>()
    val artistType = MutableLiveData<String>()
    val artistMainPic = MutableLiveData<String>()

    fun prepareEditFieldForView(){
        artistId.value = artist!!.artistId
        artistName.value = artist!!.artistName
        artistDesc.value = artist!!.artistDesc
        artistType.value = artist!!.artistType
        artistMainPic.value = artist!!.artistMainPic
    }



    fun editSelectedArtist(){
        val editedItem = ArtistData(
            artistId = artistId.value.toString(),
            artistName = artistName.value.toString(),
            artistDesc = artistDesc.value.toString(),
            artistType = artistType.value.toString(),
            artistMainPic = artistMainPic.value.toString()
        )
        Log.i("ArtistEdit","editedItem = $editedItem")

        repository.editSelectedArtist(editedItem)
    }
}