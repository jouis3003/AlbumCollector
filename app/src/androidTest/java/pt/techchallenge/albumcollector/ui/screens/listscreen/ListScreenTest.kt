package pt.techchallenge.albumcollector.ui.screens.listscreen

import android.content.Context
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pt.techchallenge.albumcollector.R
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.data.wrapper.DataWrapper
import pt.techchallenge.albumcollector.ui.navigation.Screens
import pt.techchallenge.albumcollector.ui.screens.listScreen.ListScreen
import pt.techchallenge.albumcollector.ui.screens.listScreen.ListScreenViewModel
import java.util.concurrent.CountDownLatch

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ListScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var navController: TestNavHostController
    private lateinit var mockViewModel: ListScreenViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        // Set up a TestNavHostController with a smaller navigation graph using only screens needed for testing
        //The CountDownLatch is used to ensure that the NavController setup completes on the main thread before the test continues.
        val latch = CountDownLatch(1)
        composeTestRule.runOnUiThread {
            navController = TestNavHostController(ApplicationProvider.getApplicationContext())
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            navController.graph =
                navController.createGraph(startDestination = Screens.ListScreen.name) {
                    composable(Screens.ListScreen.name) { }
                }
            latch.countDown()
        }
        latch.await()

        mockViewModel = mockk(relaxed = true)
    }

    @Test
    fun list_screen_should_display_top_bar_without_back_button() {
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper())

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        //Test that navigation bar is visible without the back button
        composeTestRule.onNodeWithText(context.getString(R.string.list_screen_title))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.arrow_back_icon))
            .assertDoesNotExist()
    }

    @Test
    fun list_screen_should_display_loading_indicator_when_loading() {
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper(isLoading = true))

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        //Test that only the loading indicator is visible
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.loading_indicator))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.error_loading_collection))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.empty_collection))
            .assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.album_image))
            .assertDoesNotExist()
    }

    @Test
    fun list_screen_should_display_error_message_when_error() {
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper(error = Exception("error")))

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        //Test that only the error message is visible
        composeTestRule.onNodeWithText(context.getString(R.string.error_loading_collection))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.loading_indicator))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.empty_collection))
            .assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.album_image))
            .assertDoesNotExist()
    }

    @Test
    fun list_screen_should_display_empty_message_when_empty_result() {
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper(data = emptyList()))

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        //Test that only the empty message is visible
        composeTestRule.onNodeWithText(context.getString(R.string.empty_collection))
            .assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.loading_indicator))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.error_loading_collection))
            .assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.album_image))
            .assertDoesNotExist()
    }

    @Test
    fun list_screen_should_display_albums_when_data_is_available_and_valid() {
        val albums = listOf(
            Album(1, 1, "title", "url", "thumbnailUrl"),
            Album(2, 2, "title2", "url2", "thumbnailUrl2")
        )
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper(data = albums))

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        //Test that only the albums are visible
        composeTestRule.onNodeWithText(albums[0].title!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(albums[1].title!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.album_unknown_title))
            .assertDoesNotExist()
        composeTestRule.onAllNodesWithContentDescription(context.getString(R.string.album_image))
            .assertCountEquals(2)
        composeTestRule.onNodeWithText(context.getString(R.string.album_image_placeholder))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.error_loading_collection))
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(context.getString(R.string.empty_collection))
            .assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.loading_indicator))
            .assertDoesNotExist()
    }

    @Test
    fun list_screen_should_display_album_with_unknown_title_when_title_is_null() {
        val albums = listOf(
            Album(1, 1, null, "url", "thumbnailUrl")
        )
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper(data = albums))

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText(context.getString(R.string.album_unknown_title))
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription(context.getString(R.string.album_image))
            .assertCountEquals(1)
        composeTestRule.onNodeWithText(context.getString(R.string.album_image_placeholder))
            .assertDoesNotExist()
    }

    @Test
    fun list_screen_should_display_album_with_placeholder_when_thumbnail_is_null() {
        val albums = listOf(
            Album(1, 1, "title", "url", null)
        )
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper(data = albums))

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText(albums[0].title!!).assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription(context.getString(R.string.album_image_placeholder))
            .assertCountEquals(1)
        composeTestRule.onNodeWithText(context.getString(R.string.album_image))
            .assertDoesNotExist()
    }

    @Test
    fun list_screen_should_display_album_with_placeholder_and_unknown_title_when_both_thumbnail_and_title_are_null() {
        val albums = listOf(
            Album(1, 1, null, "url", null)
        )
        every { mockViewModel.albums } returns MutableStateFlow(DataWrapper(data = albums))

        composeTestRule.setContent {
            ListScreen(navController = navController, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText(context.getString(R.string.album_unknown_title))
            .assertIsDisplayed()
        composeTestRule.onAllNodesWithContentDescription(context.getString(R.string.album_image_placeholder))
            .assertCountEquals(1)
        composeTestRule.onNodeWithText(context.getString(R.string.album_image))
            .assertDoesNotExist()
    }
}