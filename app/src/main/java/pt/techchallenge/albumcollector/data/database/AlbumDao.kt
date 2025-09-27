package pt.techchallenge.albumcollector.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pt.techchallenge.albumcollector.data.models.Album

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<Album>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)
}