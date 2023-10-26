package com.example.musicchaser.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.data.source.MusicChaserRemoteDataSource
import com.example.musicchaser.data.source.MusicChaserRepository

object ServiceLocator {

    @Volatile
    var musicChaserRepository: MusicChaserRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(): MusicChaserRepository {
        synchronized(this) {
            return musicChaserRepository
                ?: createMusicChaserRepository()
        }
    }

    private fun createMusicChaserRepository(): MusicChaserRepository {
        return DefaultMusicChaserRepository(
            MusicChaserRemoteDataSource
        )
    }


}