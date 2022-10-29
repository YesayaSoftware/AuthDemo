package tz.co.yesayasoftware.authdemo.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import tz.co.yesayasoftware.authdemo.data.utils.Result
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import tz.co.yesayasoftware.authdemo.R
import tz.co.yesayasoftware.authdemo.data.model.ErrorResponse
import tz.co.yesayasoftware.authdemo.data.model.Users
import tz.co.yesayasoftware.authdemo.ui.screens.destinations.HomeScreenDestination
import tz.co.yesayasoftware.authdemo.ui.screens.destinations.LoginScreenDestination
import tz.co.yesayasoftware.authdemo.ui.screens.destinations.RegisterScreenDestination
import tz.co.yesayasoftware.authdemo.ui.theme.*

@Composable
@Destination
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is Result.Authorized -> {
                    navigator.navigate(
                        HomeScreenDestination(
                            user = result.data as Users
                        )
                    ) {
                        popUpTo(LoginScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }

                is Result.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "You're not authorized",
                        Toast.LENGTH_LONG
                    ).show()
                }

                is Result.ApiError -> {
                    val apiResponse: ErrorResponse? = result.data as ErrorResponse?

                    viewModel.validate(apiResponse)

                    Toast.makeText(
                        context,
                        apiResponse?.message ?: "An error occurred.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    var isPasswordOpen by remember { mutableStateOf(false) }
    var isConfirmPasswordOpen by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.TopCenter) {
        Image(
            painter = painterResource(id = R.drawable.login_illustration),
            contentDescription = "Branding Image - Yesaya Software",
            modifier = Modifier.fillMaxWidth()
        )
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "",
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontFamily = ReemKufi
            )

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(26.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "Continue with Google",
                        color = PrimaryColor,
                        fontSize = 16.sp
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                backgroundColor = Color.White,
                elevation = 0.dp,
                shape = BottomBoxShape.medium
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Log In with Email",
                        color = LightTextColor,
                        fontFamily = Poppins,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    OutlinedTextField(
                        value = state.name,
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.NameChanged(it))
                        },

                        label = {
                            Text(
                                text = "Name",
                                color = PrimaryColor
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 10.dp),

                        colors = TextFieldDefaults
                            .outlinedTextFieldColors(
                                unfocusedBorderColor = PrimaryColor,
                                textColor = PrimaryColor
                            ),

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),

                        singleLine = true,

                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_user),
                                contentDescription = "User icon",
                                tint = PrimaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                    if (state.nameError.isNotBlank()) {
                        Text(
                            text = state.nameError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(horizontal = 20.dp)
                                .padding(top = 10.dp)
                        )
                    }

                    OutlinedTextField(
                        value = state.email,
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.EmailChanged(it))
                        },

                        label = {
                            Text(
                                text = "Email Address",
                                color = PrimaryColor
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 10.dp),

                        colors = TextFieldDefaults
                            .outlinedTextFieldColors(
                                unfocusedBorderColor = PrimaryColor,
                                textColor = PrimaryColor
                            ),

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),

                        singleLine = true,

                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_email),
                                contentDescription = "Email icon",
                                tint = PrimaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                    if (state.emailError.isNotBlank()) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(horizontal = 20.dp)
                                .padding(top = 10.dp)
                        )
                    }

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.PasswordChanged(it))
                        },

                        label = {
                            Text(
                                text = "Password",
                                color = PrimaryColor
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 10.dp),

                        colors = TextFieldDefaults
                            .outlinedTextFieldColors(
                                unfocusedBorderColor = PrimaryColor,
                                textColor = PrimaryColor
                            ),

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),

                        singleLine = true,

                        visualTransformation = if (!isPasswordOpen)
                            PasswordVisualTransformation()
                        else
                            VisualTransformation.None,

                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_password),
                                contentDescription = "Password icon",
                                tint = PrimaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                        },

                        trailingIcon = {
                            IconButton(onClick = {
                                isPasswordOpen = !isPasswordOpen
                            }) {
                                if (!isPasswordOpen) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_eye_open),
                                        contentDescription = "Eye open icon ",
                                        tint = PrimaryColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_eye_close),
                                        contentDescription = "Eye close icon ",
                                        tint = PrimaryColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    )

                    if (state.passwordError.isNotBlank()) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(horizontal = 20.dp)
                                .padding(top = 10.dp)
                        )
                    }

                    OutlinedTextField(
                        value = state.passwordConfirmation,
                        onValueChange = {
                            viewModel.onEvent(RegisterUiEvent.ConfirmPasswordChanged(it))
                        },

                        label = {
                            Text(
                                text = "Confirm Password",
                                color = PrimaryColor
                            )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 10.dp),

                        colors = TextFieldDefaults
                            .outlinedTextFieldColors(
                                unfocusedBorderColor = PrimaryColor,
                                textColor = PrimaryColor
                            ),

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),

                        singleLine = true,

                        visualTransformation = if (!isConfirmPasswordOpen)
                            PasswordVisualTransformation()
                        else
                            VisualTransformation.None,

                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_password),
                                contentDescription = "Password icon",
                                tint = PrimaryColor,
                                modifier = Modifier.size(24.dp)
                            )
                        },

                        trailingIcon = {
                            IconButton(onClick = {
                                isConfirmPasswordOpen = !isConfirmPasswordOpen
                            }) {
                                if (!isConfirmPasswordOpen) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_eye_open),
                                        contentDescription = "Eye open icon ",
                                        tint = PrimaryColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_eye_close),
                                        contentDescription = "Eye close icon ",
                                        tint = PrimaryColor,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    )

                    if (state.confirmPasswordError.isNotBlank()) {
                        Text(
                            text = state.confirmPasswordError,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(horizontal = 20.dp)
                                .padding(top = 10.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.onEvent(RegisterUiEvent.Register)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = PrimaryColor,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(vertical = 14.dp)
                    ) {
                        Text(
                            text = "Register",
                            fontFamily = Poppins
                        )
                    }

                    Spacer(modifier = Modifier.padding(top = 26.dp))

                    TextButton(
                        onClick = {},
                        contentPadding = PaddingValues(vertical = 0.dp)
                    ) {
                        Text(
                            text = "Forgot Password ?",
                            color = LightTextColor,
                            fontFamily = Poppins,
                            fontSize = 12.sp
                        )
                    }

                    TextButton(
                        onClick = {
                            navigator.navigate(LoginScreenDestination) {
                                popUpTo(RegisterScreenDestination.route) {
                                    inclusive = true
                                }
                            }
                        },
                        contentPadding = PaddingValues(vertical = 0.dp)
                    ) {
                        Text(
                            text = "Have an Account ? Login",
                            color = LightTextColor,
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}