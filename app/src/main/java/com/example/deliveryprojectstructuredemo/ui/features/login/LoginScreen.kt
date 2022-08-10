package com.example.deliveryprojectstructuredemo.ui.features.login

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.delivery_app.core.util.UiEvent
import com.example.deliveryprojectstructuredemo.R
import com.example.deliveryprojectstructuredemo.common.isValidEmail
import com.example.deliveryprojectstructuredemo.common.isValidPassword
import com.example.deliveryprojectstructuredemo.data.UserInfo
import com.example.deliveryprojectstructuredemo.ui.features.components.AppButton
import com.example.deliveryprojectstructuredemo.ui.features.components.AppOutlineTextField
import com.example.deliveryprojectstructuredemo.ui.features.components.AppText
import com.example.deliveryprojectstructuredemo.ui.features.components.SocialSection
import com.example.deliveryprojectstructuredemo.ui.theme.DeliveryProjectStructureDemoTheme
import com.example.deliveryprojectstructuredemo.ui.theme.spacing
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(
    vm: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (UserInfo) -> Unit,
    onForgetPasswordClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    val loginState = vm.loginUiState.value
    val context = LocalContext.current


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        vm.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> {
                    vm.userInfo?.let {
                        onLoginSuccess(it)
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, uiEvent.message.asString(context), Toast.LENGTH_LONG)
                        .show()
                }
                else -> Unit
            }
        }
    }
    val state=vm.googleUser.observeAsState()
    val user=state.value
    val isError = rememberSaveable { mutableStateOf(false) }

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = GoogleApiContract()) { task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)

                if (gsa != null) {
                    vm.fetchSignInUser(gsa.email, gsa.displayName,gsa.id)
                } else {
                    isError.value = true
                }
            } catch (e: ApiException) {
                Log.i("LoginScreen", "LoginScreen: ${e.toString()}")
            }
        }

    user?.let {
        vm.hideLoading()
        Log.i("gsa", "gsa:$it ")
        Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
    }


/*    loginState.let { state ->
        if (state.isDataLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Center) {
                CircularProgressIndicator()
            }
        }
        state.errorMessage?.let {
            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
            state.errorMessage = null
        }

        state.user?.let {
            Toast.makeText(
                context,
                "Welcome ${it.firstName} ${it.lastName}",
                Toast.LENGTH_SHORT
            ).show()
            state.user = null
        }
    }*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_login),
                contentDescription = "Login Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppText(
                text = stringResource(id = R.string.log_in),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppText(
                text = stringResource(id = R.string.enter_registered_emaila_and_pass),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppOutlineTextField(
                value = email,
                label = { Text(text = stringResource(id = R.string.email)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    email = it
                },
                placeholder = { Text(text = stringResource(id = R.string.enter_your_email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = (!email.isValidEmail() && email.length >= 6),
                errorMessage = stringResource(id = R.string.email_error)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppOutlineTextField(
                value = password,
                label = { Text(text = stringResource(id = R.string.password)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                    password = it
                },
                placeholder = { Text(text = stringResource(id = R.string.your_password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility

                    }) {
                        Icon(
                            imageVector = if (passwordVisibility)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff, ""
                        )
                    }
                },
                isError = (!password.isValidPassword() && password.length >= 4),
                errorMessage = stringResource(id = R.string.password_error)
            )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                AppText(
                    text = stringResource(id = R.string.forgot_password),
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(onClick = onForgetPasswordClick)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                AppButton(
                    enabled = email.isValidEmail() && password.isValidPassword(),
                    onClick = {
                        vm.onEvent(
                            LoginUIEvent.Submit(email, password)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            SocialSection(
                headerText = stringResource(id = R.string.or_login_with),
                footerText1 = stringResource(id = R.string.dont_have_account),
                footerText2 = stringResource(id = R.string.create_now),
                onGoogleClick = { onGoogleClick.invoke()
                    vm.showLoading()
                    authResultLauncher.launch(1)
                                },
                onFacebookClick = onFacebookClick,
                onFooterClick = onCreateAccountClick
            )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
        if (loginState.isDataLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        }
    }


@Preview("default", "rectangle")
@Preview("dark theme", "rectangle", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", "rectangle", fontScale = 2f)
@Composable
private fun RectangleButtonPreview() {
    DeliveryProjectStructureDemoTheme {
        Surface {
//            LoginScreen()
        }
    }
}