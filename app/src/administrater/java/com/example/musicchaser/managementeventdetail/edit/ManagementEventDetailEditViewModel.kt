package com.example.musicchaser.managementeventdetail.edit

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventDetailEditViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var event: EventData? = null



}