package pt.techchallenge.albumcollector.data.network

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var albumApi: AlbumApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockResponseDispatcher()
        mockWebServer.start()
        albumApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getAlbums should return a list of albums`() = runTest {
        val albums = albumApi.getAlbums()

        assertNotNull(albums)
        assertEquals(3, albums.size)

        assertEquals(1, albums[0].albumId)
        assertEquals(1, albums[0].id)
        assertEquals("accusamus beatae ad facilis cum similique qui sunt", albums[0].title)
        assertEquals("https://via.placeholder.com/600/92c952", albums[0].url)
        assertEquals("https://via.placeholder.com/150/92c952", albums[0].thumbnailUrl)

        assertEquals(1, albums[1].albumId)
        assertEquals(2, albums[1].id)
        assertEquals("reprehenderit est deserunt velit ipsam", albums[1].title)
        assertEquals("https://via.placeholder.com/600/771796", albums[1].url)
        assertEquals("https://via.placeholder.com/150/771796", albums[1].thumbnailUrl)

        assertEquals(1, albums[2].albumId)
        assertEquals(3, albums[2].id)
        assertEquals("officia porro iure quia iusto qui ipsa ut modi", albums[2].title)
        assertEquals("https://via.placeholder.com/600/24f355", albums[2].url)
        assertEquals("https://via.placeholder.com/150/24f355", albums[2].thumbnailUrl)
    }
}