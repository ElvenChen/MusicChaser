package com.example.musicchaser.managementeventdetail.addperformer.delete

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.ArtistData
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManagementEventPerformerDetailDeleteViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    var event: EventData? = null
    var artist: ArtistData? = null


     fun deleteEventPerformer() {
        repository.deleteEventPerformer(
            event!!.eventId,
            artist!!.artistId
        )
    }

    fun deleteArtistAttendEvent() {
        repository.deleteArtistAttendEvent(
            artist!!.artistId,
            event!!.eventId
        )
    }

    fun nothing() {}
}