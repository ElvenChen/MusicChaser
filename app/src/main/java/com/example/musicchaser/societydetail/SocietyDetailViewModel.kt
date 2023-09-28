package com.example.musicchaser.societydetail

import androidx.lifecycle.ViewModel
import com.example.musicchaser.data.EventData
import com.example.musicchaser.data.ThreadData
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SocietyDetailViewModel @Inject constructor(val repository: DefaultMusicChaserRepository) :
    ViewModel() {
    var thread: ThreadData? = null



}