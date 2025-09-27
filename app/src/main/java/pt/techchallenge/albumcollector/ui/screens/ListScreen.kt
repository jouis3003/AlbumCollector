package pt.techchallenge.albumcollector.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pt.techchallenge.albumcollector.R
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.ui.components.TopBar

@Composable
internal fun ListScreen(navController: NavController) {
    ListScreenContent()
}

@Preview
@Composable
private fun ListScreenContent(modifier: Modifier = Modifier) {

    val albums = listOf(
        Album(1, 1, "Title 1", "URL 1", "Thumbnail URL 1"),
        Album(2, 2, "Title 2", "URL 2", "Thumbnail URL 2"),
        Album(3, 3, "Title 3", "URL 3", "Thumbnail URL 3")
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopBar() }
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .padding(paddingValues = innerPadding)
                .padding(all = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            items(items = albums) {
                AlbumItem(album = it)
            }
        }
    }
}

@Composable
private fun AlbumItem(album: Album) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RectangleShape) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.album_image)
            )

            Text(text = album.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}