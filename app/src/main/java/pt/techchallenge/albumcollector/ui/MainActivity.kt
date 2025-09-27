package pt.techchallenge.albumcollector.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pt.techchallenge.albumcollector.ui.navigation.Navigation
import pt.techchallenge.albumcollector.ui.theme.AlbumCollectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlbumCollectorTheme {
                Navigation()
            }
        }
    }
}