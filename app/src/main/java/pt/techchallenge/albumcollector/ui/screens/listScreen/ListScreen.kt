package pt.techchallenge.albumcollector.ui.screens.listScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import pt.techchallenge.albumcollector.R
import pt.techchallenge.albumcollector.data.models.Album
import pt.techchallenge.albumcollector.ui.components.TopBar

@Composable
internal fun ListScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    ListScreenContent(navController = navController, viewModel = viewModel)
}

@Composable
private fun ListScreenContent(navController: NavController, viewModel: ListScreenViewModel) {

    val albums by viewModel.albums.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController, isBackButtonVisible = false)
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(all = 16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (albums.isLoading == true) {
                CircularProgressIndicator()

            } else if (albums.error != null) {
                Text(
                    text = stringResource(id = R.string.error_loading_collection),
                    style = MaterialTheme.typography.labelLarge
                )

            } else if (albums.data.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.empty_collection),
                    style = MaterialTheme.typography.labelLarge
                )

            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                ) {
                    items(items = albums.data ?: emptyList()) {
                        AlbumItem(album = it)
                    }
                }
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