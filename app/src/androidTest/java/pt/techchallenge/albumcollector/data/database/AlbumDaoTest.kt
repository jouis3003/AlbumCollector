package pt.techchallenge.albumcollector.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pt.techchallenge.albumcollector.data.models.Album

@RunWith(AndroidJUnit4::class)
class AlbumDaoTest {

    private lateinit var albumDao: AlbumDao
    private lateinit var albumDatabase: AlbumDatabase

    @Before
    fun setup() {
        albumDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumDatabase::class.java
        ).build()
        albumDao = albumDatabase.albumDao()
    }

    @After
    fun tearDown() {
        albumDatabase.close()
    }

    @Test
    fun insertAlbums_should_insert_albums_in_database() = runTest {
        val albums = listOf(
            Album(1, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, "title2", "url2", "thumbnailUrl2")
        )

        albumDao.insertAlbums(albums)

        val result = albumDao.getAllAlbums().first()

        assertEquals(2, result.size)
        assertEquals(albums[0], result[0])
        assertEquals(albums[1], result[1])
    }

    @Test
    fun insertAlbums_with_empty_list_should_work() = runTest {
        albumDao.insertAlbums(emptyList())

        val result = albumDao.getAllAlbums().first()

        assertEquals(0, result.size)
    }

    @Test
    fun insertAlbums_with_same_id_should_replace_album() = runTest {
        val album1 = Album(1, 1, "title", "url", "thumbnailUrl")
        val album2 = Album(1, 1, "title2", "url2", "thumbnailUrl2")

        albumDao.insertAlbums(listOf(album1))
        albumDao.insertAlbums(listOf(album2))

        val result = albumDao.getAllAlbums().first()

        assertEquals(1, result.size)
        assertEquals(album2, result[0])
    }

    @Test
    fun insertAlbums_with_null_values_should_work() = runTest {
        val albums = listOf(
            Album(null, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, null, "url", "thumbnailUrl"),
            Album(3, 3, "title", null, "thumbnailUrl"),
            Album(4, 4, "title", "url", null)
        )

        albumDao.insertAlbums(albums)

        val result = albumDao.getAllAlbums().first()

        assertEquals(4, result.size)
        assertEquals(albums[0], result[0])
        assertEquals(albums[1], result[1])
        assertEquals(albums[2], result[2])
        assertEquals(albums[3], result[3])
    }
}