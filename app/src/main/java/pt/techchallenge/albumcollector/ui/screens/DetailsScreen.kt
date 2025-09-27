package pt.techchallenge.albumcollector.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun DetailsScreen(navController: NavController) {
    DetailsScreen()
}

@Preview
@Composable
private fun DetailsScreen(modifier: Modifier = Modifier) {
    Text(text = "Details Screen")
}