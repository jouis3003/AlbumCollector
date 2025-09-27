package pt.techchallenge.albumcollector.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import pt.techchallenge.albumcollector.ui.navigation.Navigation
import pt.techchallenge.albumcollector.ui.theme.AlbumCollectorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlbumCollectorTheme(dynamicColor = false) {
                Navigation()
            }
        }
    }
}