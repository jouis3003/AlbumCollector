package pt.techchallenge.albumcollector.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.techchallenge.albumcollector.data.database.AlbumDao
import pt.techchallenge.albumcollector.data.network.AlbumApi
import pt.techchallenge.albumcollector.data.repositories.AlbumRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAlbumRepository(albumApi: AlbumApi, albumDao: AlbumDao): AlbumRepository {
        return AlbumRepository(albumApi, albumDao)
    }
}