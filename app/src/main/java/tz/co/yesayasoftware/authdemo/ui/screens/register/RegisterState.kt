package tz.co.yesayasoftware.authdemo.ui.screens.register

data class RegisterState(
    val isLoading: Boolean = false,
    val name: String = "",
    val nameError: String = "",
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val passwordConfirmation: String = "",
    val confirmPasswordError: String = ""
)