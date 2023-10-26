package com.example.musicchaser.managementartistdetail.post

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementArtistDetailPostViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    val artistName = MutableLiveData<String>()
    val artistDesc = MutableLiveData<String>()
    val artistType = MutableLiveData<String>()
    val artistMainPic = MutableLiveData<String>()


    fun postNewArtist(){
        val postItem = ArtistData(
            artistId = "",
            artistName = artistName.value!!,
            artistDesc = artistDesc.value!!,
            artistType = artistType.value!!,
            artistMainPic = artistMainPic.value!!
        )
        Log.i("ArtistPost","postItem = $postItem")

        repository.postNewArtist(postItem)
    }

}