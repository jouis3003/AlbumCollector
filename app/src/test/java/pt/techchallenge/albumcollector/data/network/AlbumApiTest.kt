package pt.techchallenge.albumcollector.data.network

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
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
        assertEquals(7, albums.size)

        //Check first 3 albums - with all fields present
        assertEquals(1, albums[0].albumId)
        assertEquals(1, albums[0].id)
        assertEquals("accusamus beatae ad facilis cum similique qui sunt", albums[0].title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums[0].url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums[0].thumbnailUrl)

        assertEquals(1, albums[1].albumId)
        assertEquals(2, albums[1].id)
        assertEquals("reprehenderit est deserunt velit ipsam", albums[1].title)
        assertEquals("https://placehold.co/600x600/771796/white/png", albums[1].url)
        assertEquals("https://placehold.co/150x150/771796/white/png", albums[1].thumbnailUrl)

        assertEquals(1, albums[2].albumId)
        assertEquals(3, albums[2].id)
        assertEquals("officia porro iure quia iusto qui ipsa ut modi", albums[2].title)
        assertEquals("https://placehold.co/600x600/24f355/white/png", albums[2].url)
        assertEquals("https://placehold.co/150x150/24f355/white/png", albums[2].thumbnailUrl)

        //Check next 4 albums - with some fields missing
        assertEquals(2, albums[3].albumId)
        assertEquals(4, albums[3].id)
        assertNull(albums[3].title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums[3].url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums[3].thumbnailUrl)

        assertEquals(2, albums[4].albumId)
        assertEquals(5, albums[4].id)
        assertEquals("album with missing thumbnail", albums[4].title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums[4].url)
        assertNull(albums[4].thumbnailUrl)

        assertNull(albums[5].albumId)
        assertEquals(6, albums[5].id)
        assertEquals("album with null albumId", albums[5].title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums[5].url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums[5].thumbnailUrl)

        assertEquals(3, albums[6].albumId)
        assertNull(albums[6].id)
        assertEquals("album with null id - should be filtered out", albums[6].title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums[6].url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums[6].thumbnailUrl)
    }

    @Test
    fun `getAlbums should handle empty response`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200).setBody("[]")
            }
        }

        val albums = albumApi.getAlbums()

        assertNotNull(albums)
        assertTrue(albums.isEmpty())
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 400`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(400)
            }
        }

        try {
            albumApi.getAlbums()
            fail("Expected HttpException")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("HTTP 400") == true)
        }
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 401`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(401)
            }
        }

        try {
            albumApi.getAlbums()
            fail("Expected HttpException")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("HTTP 401") == true)
        }
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 404`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(404)
            }
        }

        try {
            albumApi.getAlbums()
            fail("Expected HttpException")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("HTTP 404") == true)
        }
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 500`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(500)
            }
        }

        try {
            albumApi.getAlbums()
            fail("Expected HttpException")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("HTTP 500") == true)
        }
    }

    @Test
    fun `getAlbums should throw JsonSyntaxException when response is not valid json`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200).setBody("invalid json")
            }
        }

        try {
            albumApi.getAlbums()
            fail("Expected JsonSyntaxException")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("malformed JSON") == true)
        }
    }
}