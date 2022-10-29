package tz.co.yesayasoftware.authdemo.ui.screens.login

sealed class LoginUiEvent {
    data class EmailChanged(val value: String): LoginUiEvent()
    data class EmailError(val value: String): LoginUiEvent()
    data class PasswordChanged(val value: String): LoginUiEvent()
    data class PasswordError(val value: String): LoginUiEvent()

    object Login: LoginUiEvent()
}