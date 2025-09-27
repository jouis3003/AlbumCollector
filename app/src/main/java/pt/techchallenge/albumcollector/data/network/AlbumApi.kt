package pt.techchallenge.albumcollector.data.network

import pt.techchallenge.albumcollector.data.models.Album
import retrofit2.http.GET

interface AlbumApi {
    @GET("technical-test.json")
    suspend fun getAlbums(): List<Album>
}