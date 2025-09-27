@file:OptIn(ExperimentalMaterial3Api::class)

package pt.techchallenge.albumcollector.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import pt.techchallenge.albumcollector.R

@Composable
internal fun TopBar(navController: NavController, isBackButtonVisible: Boolean = true) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.list_screen_title),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            if (isBackButtonVisible) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.arrow_back_icon)
                    )
                }
            }
        })
}