package com.example.musicchaser.artist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var dataList = mutableListOf<ArtistData>()

    private val _dataListForAdapter = MutableLiveData<List<ArtistData>>()
    val dataListForAdapter: LiveData<List<ArtistData>>
        get() = _dataListForAdapter

    private val _navigateToSelectedArtist = MutableLiveData<ArtistData?>()
    val navigateToSelectedArtist: LiveData<ArtistData?>
        get() = _navigateToSelectedArtist


    // handle artist clicking navigation
    val displayArtistDetails = fun(artist: ArtistData) {
        _navigateToSelectedArtist.value = artist
    }

    // handle artist clicking navigation completed
    fun displayArtistDetailsCompleted() {
        _navigateToSelectedArtist.value = null
    }


    // handle artist list from fireStore
    private val handleGetArtistListResult = fun(document: DocumentSnapshot?, exception: Exception?) {

        if (document != null) {
            val data = document.data

            val dataTobeAddToList = ArtistData(
                artistId = (data!!["artist_id"]).toString(),
                artistName = (data["artist_name"]).toString(),
                artistDesc = (data["artist_desc"]).toString(),
                artistType = (data["artist_type"]).toString(),
                artistMainPic = (data["artist_main_pic"]).toString()
            )

            dataList.add(dataTobeAddToList)
        } else {
            if (exception != null) {
                Log.i("ArtistTest", "In ArtistViewModel something goes wrong : $exception")
            }
        }
    }

    // setting artist list liveData for adapter after artist list result is handled
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("ArtistTest", "Artist Data List = ${_dataListForAdapter.value}")
    }

    // get artist list result
    private fun getArtistListResult() {
        dataList.clear()
        repository.getArtistList(handleGetArtistListResult, handleSettingDataList)
    }

    // get artist list result of keyword-searching
    fun getSearchedArtistListResult(keyword: String) {
        dataList.clear()
        repository.getSearchedArtistList(keyword, handleGetArtistListResult, handleSettingDataList)
    }

    init {
        getArtistListResult()
    }

}