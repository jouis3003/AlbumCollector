package pt.techchallenge.albumcollector.data.network

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
        val response = albumApi.getAlbums()
        val albums = if (response.isSuccessful) { response.body() } else { null }

        assertTrue(response.isSuccessful)

        assertNotNull(albums)
        assertEquals(8, albums?.size)

        //Check first 3 albums - with all fields present
        assertEquals(1, albums?.get(0)?.albumId)
        assertEquals(1, albums?.get(0)?.id)
        assertEquals("accusamus beatae ad facilis cum similique qui sunt", albums?.get(0)?.title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums?.get(0)?.url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums?.get(0)?.thumbnailUrl)

        assertEquals(1, albums?.get(1)?.albumId)
        assertEquals(2, albums?.get(1)?.id)
        assertEquals("reprehenderit est deserunt velit ipsam", albums?.get(1)?.title)
        assertEquals("https://placehold.co/600x600/771796/white/png", albums?.get(1)?.url)
        assertEquals("https://placehold.co/150x150/771796/white/png", albums?.get(1)?.thumbnailUrl)

        assertEquals(1, albums?.get(2)?.albumId)
        assertEquals(3, albums?.get(2)?.id)
        assertEquals("officia porro iure quia iusto qui ipsa ut modi", albums?.get(2)?.title)
        assertEquals("https://placehold.co/600x600/24f355/white/png", albums?.get(2)?.url)
        assertEquals("https://placehold.co/150x150/24f355/white/png", albums?.get(2)?.thumbnailUrl)

        //Check next 4 albums - with some fields missing
        assertEquals(2, albums?.get(3)?.albumId)
        assertEquals(4, albums?.get(3)?.id)
        assertNull(albums?.get(3)?.title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums?.get(3)?.url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums?.get(3)?.thumbnailUrl)

        assertEquals(2, albums?.get(4)?.albumId)
        assertEquals(5, albums?.get(4)?.id)
        assertEquals("album with missing thumbnail", albums?.get(4)?.title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums?.get(4)?.url)
        assertNull(albums?.get(4)?.thumbnailUrl)

        assertNull(albums?.get(5)?.albumId)
        assertEquals(6, albums?.get(5)?.id)
        assertEquals("album with null albumId", albums?.get(5)?.title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums?.get(5)?.url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums?.get(5)?.thumbnailUrl)

        assertEquals(3, albums?.get(6)?.albumId)
        assertNull(albums?.get(6)?.id)
        assertEquals("album with null id - should be filtered out", albums?.get(6)?.title)
        assertEquals("https://placehold.co/600x600/92c952/white/png", albums?.get(6)?.url)
        assertEquals("https://placehold.co/150x150/92c952/white/png", albums?.get(6)?.thumbnailUrl)

        assertNull(albums?.get(7))
    }

    @Test
    fun `getAlbums should handle empty response`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200).setBody("[]")
            }
        }

        val response = albumApi.getAlbums()
        val albums = if (response.isSuccessful) { response.body() } else { null }

        assertTrue(response.isSuccessful)

        assertNotNull(albums)
        assertTrue(albums?.isEmpty() ?: false)
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 400`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(400)
            }
        }

        val response = albumApi.getAlbums()
        assertFalse(response.isSuccessful)
        assertEquals(400, response.code())
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 401`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(401)
            }
        }

        val response = albumApi.getAlbums()
        assertFalse(response.isSuccessful)
        assertEquals(401, response.code())
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 404`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(404)
            }
        }

        val response = albumApi.getAlbums()
        assertFalse(response.isSuccessful)
        assertEquals(404, response.code())
    }

    @Test
    fun `getAlbums should throw HttpException when response code is 500`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(500)
            }
        }

        val response = albumApi.getAlbums()
        assertFalse(response.isSuccessful)
        assertEquals(500, response.code())
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

    @Test
    fun `getAlbums should handle null response`() = runTest {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200).setBody("null")
            }
        }

        val response = albumApi.getAlbums()
        val albums = if (response.isSuccessful) { response.body() } else { null }

        assertTrue(response.isSuccessful)
        assertNull(albums)
    }
}