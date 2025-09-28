package pt.techchallenge.albumcollector.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test

class AlbumDatabaseTest {

    private lateinit var albumDatabase: AlbumDatabase

    @Before
    fun setup() {
        albumDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumDatabase::class.java
        ).build()
    }

    @After
    fun tearDown() {
        albumDatabase.close()
    }

    @Test
    fun getAlbumDao_should_return_a_valid_dao() {
        val albumDao = albumDatabase.albumDao()
        assertNotNull(albumDao)
    }

    @Test
    fun getAlbumDao_should_always_return_the_same_dao() {
        val albumDao1 = albumDatabase.albumDao()
        val albumDao2 = albumDatabase.albumDao()
        assert(albumDao1 === albumDao2)
    }
}