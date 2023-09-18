package com.example.musicchaser.artistdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.login.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel  @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {
    var artist: ArtistData? = null


    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite





    private val handleSettingArtistIsFavorite = fun() {
        _isFavorite.value = true
    }

    private val handleSettingArtistNotFavorite = fun() {
        _isFavorite.value = false
    }

    fun addFavoriteArtist() {
        repository.addFavoriteArtist(
            UserManager.userId,
            artist!!.artistId,
            handleSettingArtistIsFavorite
        )
    }

    fun deleteFavoriteArtist() {
        repository.deleteFavoriteArtist(
            UserManager.userId,
            artist!!.artistId,
            handleSettingArtistNotFavorite
        )
    }

    fun getIfArtistIsFavorite() {
        repository.getIfArtistIsFavorite(
            UserManager.userId,
            artist!!.artistId,
            handleSettingArtistIsFavorite,
            handleSettingArtistNotFavorite
        )
    }

    fun nothing() {}
}