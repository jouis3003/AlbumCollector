package pt.techchallenge.albumcollector.ui.screens.detailsScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
internal fun DetailsScreen(navController: NavController) {
    DetailsScreenContent()
}

@Preview
@Composable
private fun DetailsScreenContent(modifier: Modifier = Modifier) {
    Text(text = "Details Screen")
}