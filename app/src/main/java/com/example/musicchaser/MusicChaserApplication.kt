package com.example.musicchaser

import android.app.Application
import com.example.musicchaser.data.source.MusicChaserRepository
import com.example.musicchaser.util.ServiceLocator
import kotlin.properties.Delegates

class MusicChaserApplication : Application()  {
    val musicChaserRepository: MusicChaserRepository
        get() = ServiceLocator.provideTasksRepository()

    companion object {
        var instance: MusicChaserApplication by Delegates.notNull()
    }
}