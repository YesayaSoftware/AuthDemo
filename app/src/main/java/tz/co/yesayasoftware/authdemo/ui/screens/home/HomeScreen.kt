package tz.co.yesayasoftware.authdemo.ui.screens.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import tz.co.yesayasoftware.authdemo.data.model.Users

@Composable
@Destination
fun HomeScreen(
    navigator: DestinationsNavigator,
    user: Users
) {
    Text(text = user.name)
}