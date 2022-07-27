package com.example.deliveryprojectstructuredemo.ui.features.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.deliveryprojectstructuredemo.common.Route.SIGN_UP_SCREEN
import com.example.deliveryprojectstructuredemo.ui.features.components.AppButton
import com.example.deliveryprojectstructuredemo.ui.features.components.AppDivider
import com.example.deliveryprojectstructuredemo.ui.features.components.AppText
import com.example.deliveryprojectstructuredemo.ui.theme.spacing

@Composable
fun LoginScreen(navController: NavController, vm: LoginViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginState = vm.uiState.collectAsState().value

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        when (loginState) {
            is LoginViewModel.UIState.SignedOut -> {
                LoginFields(
                    email,
                    password,
                    onLoginClick = {
                        vm.login(email, password)
                    },
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it }
                )
            }
            is LoginViewModel.UIState.InProgress -> {
                CircularProgressIndicator()
            }

            is LoginViewModel.UIState.SignIn -> {
                Toast.makeText(
                    context,
                    "${loginState.userName} Signed in successfully",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(SIGN_UP_SCREEN)
            }

            is LoginViewModel.UIState.Error -> {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginFields(
    email: String,
    password: String,
    onLoginClick: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        //        verticalArrangement = Arrangement.spacedBy(25.dp),
//        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText("Please login", modifier = Modifier.background(color = MaterialTheme.colors.background))

        OutlinedTextField(
            value = email,
            label = { Text(text = "Mobile Number") },
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = password,
            label = { Text(text = "Password") },
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation()
        )

        AppButton(onClick = {
            onLoginClick(email)
        }, modifier = Modifier.width(300.dp)) {
            Text("Login")
        }
    }
}