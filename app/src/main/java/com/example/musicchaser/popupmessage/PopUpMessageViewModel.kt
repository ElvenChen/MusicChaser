package com.example.musicchaser.popupmessage

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PopUpMessageViewModel @Inject constructor(private val repository: DefaultMusicChaserRepository) :
    ViewModel() {

    fun nothing() {}
}