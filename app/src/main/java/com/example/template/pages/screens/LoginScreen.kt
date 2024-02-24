package com.example.template.pages.screens

import android.content.Context
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.template.R
import com.example.template.pages.elements.DimmedLoadingIndicator
import com.example.template.pages.elements.showToast
import com.example.template.viewmodels.LoginState
import com.example.template.viewmodels.LoginViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccessful: () -> Unit = {},
    goToRegistrationScreen: () -> Unit = {}
) {
    // TODO check if current user is logged in already


    val loginState by loginViewModel.loginState.collectAsState()
    when (loginState) {
        is LoginState.Success -> {
            LaunchedEffect(Unit) { // This ensures that `onLoginSuccessful` is called only once
                onLoginSuccessful()
            }
        }
        is LoginState.Error -> {
            val errorMessage = (loginState as LoginState.Error).message
            Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
        }
        is LoginState.Loading -> {
            DimmedLoadingIndicator()
        }
        is LoginState.Idle -> {

        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logocopy),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = showError && email.isEmpty(),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = showError && password.isEmpty(),
            singleLine = true
        )

        val context = LocalContext.current
        Text(
            text = "Forgot your password?",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clickable {
                    // Handle forgot password click
                    showToast(context,"Forgot Password Clicked")
                },
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.End
        )

        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.login(email, password)
                } else {
                    loginViewModel.login("test", "test") // TODO remove
                    showToast(context, "Fill In Password And Email")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text("Log in")
        }

        Text(
            text = "Create Account",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    goToRegistrationScreen()
                },
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}


