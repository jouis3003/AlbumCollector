package pt.techchallenge.albumcollector.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pt.techchallenge.albumcollector.data.database.AlbumDao
import pt.techchallenge.albumcollector.data.database.AlbumDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAlbumDatabase(@ApplicationContext context: Context): AlbumDatabase {
        return Room.databaseBuilder(context, AlbumDatabase::class.java, "album_database")
            .build()
    }

    @Provides
    fun provideAlbumDao(albumDatabase: AlbumDatabase): AlbumDao {
        return albumDatabase.albumDao()
    }
}