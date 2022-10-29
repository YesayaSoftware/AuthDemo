package tz.co.yesayasoftware.authdemo.ui.screens.register

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import tz.co.yesayasoftware.authdemo.data.model.ErrorResponse
import tz.co.yesayasoftware.authdemo.data.repository.AuthRepository
import tz.co.yesayasoftware.authdemo.data.utils.Result
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    var state by mutableStateOf(RegisterState())

    private val resultChannel = Channel<Result<out Any?>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticatedUser()
    }

    fun onEvent(event: RegisterUiEvent) {
        when(event) {
            is RegisterUiEvent.NameChanged -> {
                state = state.copy(name = event.value)
            }

            is RegisterUiEvent.EmailChanged -> {
                state = state.copy(email = event.value)
            }

            is RegisterUiEvent.PasswordChanged -> {
                state = state.copy(password = event.value)
            }

            is RegisterUiEvent.ConfirmPasswordChanged -> {
                state = state.copy(passwordConfirmation = event.value)
            }

            is RegisterUiEvent.NameError -> {
                state = state.copy(nameError = event.value)
            }

            is RegisterUiEvent.EmailError -> {
                state = state.copy(emailError = event.value)
            }

            is RegisterUiEvent.PasswordError -> {
                state = state.copy(passwordError = event.value)
            }

            is RegisterUiEvent.ConfirmPasswordError -> {
                state = state.copy(confirmPasswordError = event.value)
            }

            is RegisterUiEvent.Register -> {
                register()
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val result = repository.register(
                name = state.name,
                email = state.email,
                password = state.password,
                password_confirmation = state.passwordConfirmation
            )

            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticatedUser() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val result = repository.getCurrentAuthUser()

            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    fun validate(apiResponse: ErrorResponse?) {
        apiResponse?.errors?.forEach { error ->
            when (error.key) {
                "name" -> {
                    state = state.copy(nameError = error.value[0])
                }
                "email" -> {
                    state = state.copy(emailError = error.value[0])
                }
                "password" -> {
                    state = state.copy(passwordError = error.value[0])
                }
                "password_confirmation" -> {
                    state = state.copy(confirmPasswordError = error.value[0])
                }
            }
        }
    }
}