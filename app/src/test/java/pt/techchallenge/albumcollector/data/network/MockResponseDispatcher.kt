package pt.techchallenge.albumcollector.data.network

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class MockResponseDispatcher: Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/technical-test.json" -> MockResponse().setResponseCode(200).setBody(getMockAlbums())
            else -> MockResponse().setResponseCode(404)
        }
    }

    private fun getMockAlbums(): String {
        return """
        [
          {
            "albumId": 1,
            "id": 1,
            "title": "accusamus beatae ad facilis cum similique qui sunt",
            "url": "https://placehold.co/600x600/92c952/white/png",
            "thumbnailUrl": "https://placehold.co/150x150/92c952/white/png"
          },
          {
            "albumId": 1,
            "id": 2,
            "title": "reprehenderit est deserunt velit ipsam",
            "url": "https://placehold.co/600x600/771796/white/png",
            "thumbnailUrl": "https://placehold.co/150x150/771796/white/png"
          },
          {
            "albumId": 1,
            "id": 3,
            "title": "officia porro iure quia iusto qui ipsa ut modi",
            "url": "https://placehold.co/600x600/24f355/white/png",
            "thumbnailUrl": "https://placehold.co/150x150/24f355/white/png"
          },
          {
            "albumId": 2,
            "id": 4,
            "title": null,
            "url": "https://placehold.co/600x600/92c952/white/png",
            "thumbnailUrl": "https://placehold.co/150x150/92c952/white/png"
          },
          {
            "albumId": 2,
            "id": 5,
            "title": "album with missing thumbnail",
            "url": "https://placehold.co/600x600/92c952/white/png",
            "thumbnailUrl": null
          },
          {
            "albumId": null,
            "id": 6,
            "title": "album with null albumId",
            "url": "https://placehold.co/600x600/92c952/white/png",
            "thumbnailUrl": "https://placehold.co/150x150/92c952/white/png"
          },
          {
            "albumId": 3,
            "id": null,
            "title": "album with null id - should be filtered out",
            "url": "https://placehold.co/600x600/92c952/white/png",
            "thumbnailUrl": "https://placehold.co/150x150/92c952/white/png"
          },
          null
        ]
        """.trimIndent()
    }
}