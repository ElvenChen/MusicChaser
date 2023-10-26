package com.example.musicchaser.managementartist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ManagementArtistViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var dataList = mutableListOf<ArtistData>()

    private val _dataListForAdapter = MutableLiveData<List<ArtistData>>()
    val dataListForAdapter: LiveData<List<ArtistData>>
        get() = _dataListForAdapter



    private val _navigateToEditArtist = MutableLiveData<ArtistData?>()
    val navigateToEditArtist: LiveData<ArtistData?>
        get() = _navigateToEditArtist

    private val _navigateToDeleteArtist = MutableLiveData<ArtistData?>()
    val navigateToDeleteArtist: LiveData<ArtistData?>
        get() = _navigateToDeleteArtist





    // handle edit artist clicking navigation
    val displayArtistEditDetails = fun(artist: ArtistData) {
        _navigateToEditArtist.value = artist
    }

    // handle edit artist clicking navigation completed
    fun displayArtistEditDetailsCompleted() {
        _navigateToEditArtist.value = null
    }

    // handle delete artist clicking navigation
    val displayArtistDeleteDetails = fun(artist: ArtistData) {
        _navigateToDeleteArtist.value = artist
    }

    // handle delete artist clicking navigation completed
    fun displayArtistDeleteDetailsCompleted() {
        _navigateToDeleteArtist.value = null
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
                Log.i("ManagementArtistTest", "In ManagementArtistViewModel something goes wrong : $exception")
            }
        }
    }

    // setting artist list liveData for adapter after artist list result is handled
    private val handleSettingDataList = fun() {
        _dataListForAdapter.value = dataList
        Log.i("ManagementArtistTest", "ManagementArtist Data List = ${_dataListForAdapter.value}")
    }

    // get artist list result
    fun getArtistListResult() {
        dataList.clear()
        repository.getArtistList(handleGetArtistListResult, handleSettingDataList)
    }

    // get event list result of keyword-searching
    fun getSearchedArtistListResult(keyword: String) {
        dataList.clear()
        repository.getSearchedArtistList(keyword, handleGetArtistListResult, handleSettingDataList)
    }

    init {
        getArtistListResult()
    }

}