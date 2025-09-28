package pt.techchallenge.albumcollector.data.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class AlbumTest {

    @Test
    fun `album creation with valid data should succeed`() {
        val album = Album(1, 1, "title", "url", "thumbnailUrl")

        assertEquals(1, album.albumId)
        assertEquals(1, album.id)
        assertEquals("title", album.title)
        assertEquals("url", album.url)
        assertEquals("thumbnailUrl", album.thumbnailUrl)
    }

    @Test
    fun `albums with same data should be equal`() {
        val album1 = Album(1, 1, "title", "url", "thumbnailUrl")
        val album2 = Album(1, 1, "title", "url", "thumbnailUrl")

        assertEquals(album1, album2)
    }

    @Test
    fun `albums with different data should not be equal`() {
        val album1 = Album(1, 1, "title", "url", "thumbnailUrl")
        val album2 = Album(2, 2, "title2", "url2", "thumbnailUrl2")

        assertNotEquals(album1, album2)
    }

    @Test
    fun `album toString should contain all properties`() {
        val album = Album(1, 1, "title", "url", "thumbnailUrl")

        assertEquals("Album(albumId=1, id=1, title=title, url=url, thumbnailUrl=thumbnailUrl)", album.toString())
    }
}