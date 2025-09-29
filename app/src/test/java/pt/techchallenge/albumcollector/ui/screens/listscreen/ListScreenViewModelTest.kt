package pt.techchallenge.albumcollector.ui.screens.listscreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.data.repositories.AlbumRepository
import pt.techchallenge.albumcollector.ui.screens.listScreen.ListScreenViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class ListScreenViewModelTest {

    private lateinit var listScreenViewModel: ListScreenViewModel
    private val albumRepository = mockk<AlbumRepository>(relaxed = true)
    private val context = mockk<Context>()
    private val connectivityManager = mockk<ConnectivityManager>()
    private val network = mockk<Network>()
    private val networkCapabilities = mockk<NetworkCapabilities>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should start with loading state`() = runTest {
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns null

        listScreenViewModel = ListScreenViewModel(context, albumRepository)

        assertTrue(listScreenViewModel.albums.value.isLoading == true)
        assertNull(listScreenViewModel.albums.value.data)
        assertNull(listScreenViewModel.albums.value.error)
    }

    @Test
    fun `loadAlbums should set error state when network call fails`() = runTest {
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns false

        coEvery { albumRepository.refreshAlbumsFromNetwork() } returns Result.failure(Exception("Network error"))
        every { albumRepository.getAlbumsFromDatabase() } returns flowOf(emptyList())

        listScreenViewModel = ListScreenViewModel(context, albumRepository)

        // Wait a bit for the coroutine to complete
        advanceUntilIdle()

        assertTrue(listScreenViewModel.albums.value.isLoading == false)
        assertNull(listScreenViewModel.albums.value.data)
        assertNotNull(listScreenViewModel.albums.value.error)
    }

    @Test
    fun `loadAlbums should load data from database when network is unavailable`() = runTest {
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns null

        val albums = listOf(
            Album(1, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, "title2", "url2", "thumbnailUrl2")
        )
        every { albumRepository.getAlbumsFromDatabase() } returns flowOf(albums)

        listScreenViewModel = ListScreenViewModel(context, albumRepository)

        // Wait a bit for the coroutine to complete
        advanceUntilIdle()

        assertTrue(listScreenViewModel.albums.value.isLoading == false)
        assertNotNull(listScreenViewModel.albums.value.data)
        assertTrue(listScreenViewModel.albums.value.data?.size == 2)
        assertNull(listScreenViewModel.albums.value.error)
    }

    @Test
    fun `loadAlbums should update data from database when network call is successful`() = runTest {
        every { context.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns network
        every { connectivityManager.getNetworkCapabilities(network) } returns networkCapabilities
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) } returns true
        every { networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) } returns true

        val albums = listOf(
            Album(1, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, "title2", "url2", "thumbnailUrl2")
        )
        coEvery { albumRepository.refreshAlbumsFromNetwork() } returns Result.success(Unit)
        every { albumRepository.getAlbumsFromDatabase() } returns flowOf(albums)

        listScreenViewModel = ListScreenViewModel(context, albumRepository)

        // Wait a bit for the coroutine to complete
        advanceUntilIdle()

        assertTrue(listScreenViewModel.albums.value.isLoading == false)
        assertNotNull(listScreenViewModel.albums.value.data)
        assertTrue(listScreenViewModel.albums.value.data?.size == 2)
        assertNull(listScreenViewModel.albums.value.error)
    }
}