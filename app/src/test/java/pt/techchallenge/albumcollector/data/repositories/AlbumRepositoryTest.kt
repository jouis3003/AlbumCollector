package pt.techchallenge.albumcollector.data.repositories

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import pt.techchallenge.albumcollector.data.database.AlbumDao
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.data.network.AlbumApi
import retrofit2.Response

class AlbumRepositoryTest {

    private lateinit var albumRepository: AlbumRepository
    private val albumApi = mockk<AlbumApi>()
    private val albumDao = mockk<AlbumDao>()

    @Before
    fun setup() {
        albumRepository = AlbumRepository(albumApi, albumDao)
    }

    @Test
    fun `getAlbumsFromDatabase should return a flow of albums`() = runTest {
        val albums = listOf(
            Album(1, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, "title2", "url2", "thumbnailUrl2")
        )

        every { albumDao.getAllAlbums() } returns flowOf(albums)

        val result = albumRepository.getAlbumsFromDatabase().first()

        assertEquals(albums, result)
        verify { albumDao.getAllAlbums() }
    }

    @Test
    fun `getAlbumsFromDatabase should return an empty list when database is empty`() = runTest {
        every { albumDao.getAllAlbums() } returns flowOf(emptyList())

        val result = albumRepository.getAlbumsFromDatabase().first()

        assertEquals(emptyList<Album>(), result)
        verify { albumDao.getAllAlbums() }
    }

    @Test
    fun `refreshAlbumsFromNetwork should return success when network call is successful`() = runTest {
        val albums = listOf(
            Album(1, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, "title2", "url2", "thumbnailUrl2")
        )
        coEvery { albumApi.getAlbums() } returns Response.success(albums)
        coEvery { albumDao.insertAlbums(albums) } returns Unit

        val result = albumRepository.refreshAlbumsFromNetwork()

        assertEquals(Result.success(Unit), result)
        coVerify { albumApi.getAlbums() }
        coVerify { albumDao.insertAlbums(albums) }
    }

    @Test
    fun `refreshAlbumsFromNetwork should return failure when network call fails`() = runTest {
        val exception = Exception("Network error")
        coEvery { albumApi.getAlbums() } throws exception

        val result = albumRepository.refreshAlbumsFromNetwork()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { albumApi.getAlbums() }
        coVerify(exactly = 0) { albumDao.insertAlbums(any()) }
    }

    @Test
    fun `refreshAlbumsFromNetwork should return failure when database call fails`() = runTest {
        val albums = listOf(
            Album(1, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, "title2", "url2", "thumbnailUrl2")
        )
        val exception = Exception("Database error")
        coEvery { albumApi.getAlbums() } returns Response.success(albums)
        coEvery { albumDao.insertAlbums(albums) } throws exception

        val result = albumRepository.refreshAlbumsFromNetwork()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify { albumApi.getAlbums() }
        coVerify { albumDao.insertAlbums(albums) }
    }
}