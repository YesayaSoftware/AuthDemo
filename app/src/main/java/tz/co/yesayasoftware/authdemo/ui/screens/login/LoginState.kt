package tz.co.yesayasoftware.authdemo.ui.screens.login

data class LoginState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = ""
)