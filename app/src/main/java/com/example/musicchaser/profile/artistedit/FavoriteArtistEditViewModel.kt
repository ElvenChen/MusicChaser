package com.example.musicchaser.profile.artistedit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.data.source.MusicChaserRemoteDataSource.getUserFavoriteEvent
import com.example.musicchaser.login.UserManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteArtistEditViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    private var artistCollectionRef : CollectionReference? = null

    // set user favorite artist snapShotListener
    private var favoriteArtistListenerRegistration: ListenerRegistration? = null
    private fun stopArtistListening() {
        favoriteArtistListenerRegistration?.remove()
    }



    private var artistIdList = mutableListOf<String>()

    private val _userFavoriteArtistIdList = MutableLiveData<List<String>>()
    val userFavoriteArtistIdList: LiveData<List<String>>
        get() = _userFavoriteArtistIdList

    private var artistDataList = mutableListOf<ArtistData>()

    private val _artistDataListForAdapter = MutableLiveData<List<ArtistData>>()
    val artistDataListForAdapter: LiveData<List<ArtistData>>
        get() = _artistDataListForAdapter



    // handle after-detail-getting artist list
    private val handleCompletedArtistListResult = fun(artist: ArtistData) {
        artistDataList.add(artist)
    }

    // setting completed artist list liveData for recyclerView
    private val handleSettingArtistData = fun() {
        _artistDataListForAdapter.value = artistDataList
        Log.i("UserFavoriteArtistEdit", "Artist list Completed Data = ${_artistDataListForAdapter.value}")
    }



    private fun getUserFavoriteArtist(){
        artistCollectionRef = repository.getUserFavoriteArtist(UserManager.userId)

        favoriteArtistListenerRegistration = artistCollectionRef?.addSnapshotListener { querySnapshot, e ->
            if (e != null) {
                Log.i("UserFavoriteArtistEdit", "Listen failed", e)
                return@addSnapshotListener
            } else if (querySnapshot != null && !querySnapshot.metadata.hasPendingWrites()) {
                Log.i("UserFavoriteArtistEdit", "Listener is triggered")
                artistIdList.clear()

                for (document in querySnapshot!!) {
                    val data = document.data
                    Log.i("UserFavoriteArtistEdit", "Favorite artist is here : $data")

                    val dataTobeAddToList = (data["artist_id"]).toString()

                    artistIdList.add(dataTobeAddToList)
                }
                Log.i("UserFavoriteArtistEdit", "Test")
            }
            _userFavoriteArtistIdList.value = artistIdList
            Log.i("UserFavoriteArtistEdit", "Favorite artist list is here : ${_userFavoriteArtistIdList.value}")
        }
    }

    fun getCompletedArtistList(artistIdList: List<String>) {
        artistDataList.clear()
        repository.getCompletedArtistList(
            artistIdList,
            handleCompletedArtistListResult,
            handleSettingArtistData
        )
    }

    val deleteUserFavoriteArtist = fun(artistId:String) {
        repository.deleteUserFavoriteArtist(
            UserManager.userId,
            artistId
        )
    }



    fun nothing() {}

    init {
        getUserFavoriteArtist()
    }

    override fun onCleared() {
        super.onCleared()
        stopArtistListening()
    }
}