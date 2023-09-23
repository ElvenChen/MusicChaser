package com.example.musicchaser.managementeventdetail.delete

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventDetailDeleteViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var event: EventData? = null



    fun deleteSelectedEvent(){
        repository.deleteSelectedEvent(event!!.eventId)
    }



    fun nothing() {}
}