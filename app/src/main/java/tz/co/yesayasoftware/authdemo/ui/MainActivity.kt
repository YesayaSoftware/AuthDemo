package tz.co.yesayasoftware.authdemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import tz.co.yesayasoftware.authdemo.ui.screens.NavGraphs
import tz.co.yesayasoftware.authdemo.ui.theme.AuthProjectTheme
import tz.co.yesayasoftware.authdemo.ui.theme.BackgroundColor

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuthProjectTheme {
                Surface(
                    color = BackgroundColor,
                    modifier = Modifier.fillMaxSize()
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}