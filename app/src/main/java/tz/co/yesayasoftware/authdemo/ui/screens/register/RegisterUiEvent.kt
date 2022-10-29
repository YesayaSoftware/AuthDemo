package tz.co.yesayasoftware.authdemo.ui.screens.register

sealed class RegisterUiEvent {
    data class NameChanged(val value: String): RegisterUiEvent()
    data class EmailChanged(val value: String): RegisterUiEvent()
    data class PasswordChanged(val value: String): RegisterUiEvent()
    data class ConfirmPasswordChanged(val value: String): RegisterUiEvent()
    data class NameError(val value: String): RegisterUiEvent()
    data class EmailError(val value: String): RegisterUiEvent()
    data class PasswordError(val value: String): RegisterUiEvent()
    data class ConfirmPasswordError(val value: String): RegisterUiEvent()

    object Register: RegisterUiEvent()
}
