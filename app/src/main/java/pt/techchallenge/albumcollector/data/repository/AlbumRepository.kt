package pt.techchallenge.albumcollector.data.repository

import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.data.network.AlbumApi
import pt.techchallenge.albumcollector.data.wrapper.DataWrapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(private val albumApi: AlbumApi) {

    suspend fun getAlbums(): DataWrapper<List<Album>, Boolean, Exception> {
        return try {
            val albums = albumApi.getAlbums()
            DataWrapper(data = albums, isLoading = false, error = null)
        } catch (e: Exception) {
            DataWrapper(data = null, isLoading = false, error = e)
        }
    }
}