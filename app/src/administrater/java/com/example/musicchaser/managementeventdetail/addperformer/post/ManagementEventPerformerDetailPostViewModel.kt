package com.example.musicchaser.managementeventdetail.addperformer.post

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
class ManagementEventPerformerDetailPostViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var event: EventData? = null

    var unfilteredDataList = mutableListOf<ArtistData>()

    private val _unfilteredDataListForFilter = MutableLiveData<List<ArtistData>>()
    val unfilteredDataListForFilter: LiveData<List<ArtistData>>
        get() = _unfilteredDataListForFilter


    var filteredDataList = mutableListOf<ArtistData>()

    private val _filteredDataListForAdapter = MutableLiveData<List<ArtistData>>()
    val filteredDataListForAdapter: LiveData<List<ArtistData>>
        get() = _filteredDataListForAdapter






    // handle artist list from fireStore
    private val handleGetSearchedArtistListResult =
        fun(document: DocumentSnapshot?, exception: Exception?) {

            if (document != null) {
                val data = document.data

                val dataTobeAddToList = ArtistData(
                    artistId = (data!!["artist_id"]).toString(),
                    artistName = (data["artist_name"]).toString(),
                    artistDesc = (data["artist_desc"]).toString(),
                    artistType = (data["artist_type"]).toString(),
                    artistMainPic = (data["artist_main_pic"]).toString()
                )

                unfilteredDataList.add(dataTobeAddToList)
            } else {
                if (exception != null) {
                    Log.i("AddPerformerTest", "In ViewModel something goes wrong : $exception")
                }
            }
        }

    // setting artist list liveData after artist list result is handled for next filter function call
    private val handleSettingDataList = fun() {
        _unfilteredDataListForFilter.value = unfilteredDataList
        Log.i(
            "AddPerformerTest",
            "unfilteredDataListForFilter = ${_unfilteredDataListForFilter.value}"
        )
    }

    // handle after-filtered artist list
    private val handleFilteredArtistListResult = fun(artist: ArtistData) {
        filteredDataList.add(artist)
    }

    // setting all-filtered artist list liveData for recyclerView
    private val sendFilteredArtistListForAdapter = fun() {
        _filteredDataListForAdapter.value = filteredDataList
        Log.i("AddPerformerTest", "sendFilteredArtistListForAdapter = ${_filteredDataListForAdapter.value}")
    }



    // get artist list result of keyword-searching
    fun getSearchedArtistListResult(keyword: String) {
        Log.i("AddPerformerTest", "1. viewModel.getSearchedArtistListResult(query)")
        unfilteredDataList.clear()
        repository.getSearchedArtistList(
            keyword,
            handleGetSearchedArtistListResult,
            handleSettingDataList
        )
    }

    // get artist list result
    fun getFilteredArtistList(artistIdList: List<ArtistData>) {
        filteredDataList.clear()
        repository.getFilteredArtistList(
            event!!.eventId,
            artistIdList,
            handleFilteredArtistListResult,
            sendFilteredArtistListForAdapter
        )
    }

    val postEventPerformer = fun(artist: ArtistData) {
        repository.postEventPerformer(
            event!!.eventId,
            artist.artistId
        )
    }

    val postArtistAttendEvent = fun(artist: ArtistData) {
        repository.postArtistAttendEvent(
            artist.artistId,
            event!!.eventId
        )
    }

    val deleteItemFromFilteredDataListAfterClickPost = fun(artist: ArtistData){
        filteredDataList.remove(artist)
        _filteredDataListForAdapter.value = filteredDataList
    }

    fun nothing() {}
}