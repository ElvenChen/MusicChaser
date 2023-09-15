package com.example.musicchaser

import android.util.Log
import com.example.musicchaser.data.source.DefaultMusicChaserRepository
import com.example.musicchaser.data.source.MusicChaserDataSource
import com.example.musicchaser.data.source.MusicChaserRemoteDataSource
import com.example.musicchaser.data.source.MusicChaserRepository
import com.google.firebase.firestore.auth.User
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMusicChaserRemoteDataSource(): MusicChaserDataSource {
        return MusicChaserRemoteDataSource
    }

    @Singleton
    @Provides
    fun provideMusicChaserRepository(musicChaserRemoteDataSource :MusicChaserDataSource):MusicChaserRepository{
        return DefaultMusicChaserRepository(musicChaserRemoteDataSource)
    }

}