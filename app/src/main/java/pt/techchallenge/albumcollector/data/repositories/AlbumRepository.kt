package pt.techchallenge.albumcollector.data.repositories

import kotlinx.coroutines.flow.Flow
import pt.techchallenge.albumcollector.data.database.AlbumDao
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.data.network.AlbumApi
import pt.techchallenge.albumcollector.data.wrapper.DataWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(
    private val albumApi: AlbumApi,
    private val albumDao: AlbumDao
) {

    suspend fun getAlbumsFromDatabase(): Flow<List<Album>> {
        return albumDao.getAllAlbums()
    }

    suspend fun refreshAlbumsFromNetwork() {
        val albums = albumApi.getAlbums()
        albumDao.insertAlbums(albums)
    }
}