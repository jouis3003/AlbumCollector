package pt.techchallenge.albumcollector.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun ListScreen(navController: NavController) {
    ListScreen()
}

@Preview
@Composable
private fun ListScreen(modifier: Modifier = Modifier) {
    Text(text = "List Screen")
}