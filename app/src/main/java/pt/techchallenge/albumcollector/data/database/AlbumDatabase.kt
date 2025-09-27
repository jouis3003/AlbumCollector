package pt.techchallenge.albumcollector.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pt.techchallenge.albumcollector.data.models.Album

@Database(entities = [Album::class], version = 1, exportSchema = false)
abstract class AlbumDatabase: RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}