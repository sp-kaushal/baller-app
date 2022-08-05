package com.example.deliveryprojectstructuredemo.ui.features.login

import android.content.res.Configuration
import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
import androidx.navigation.NavController
import com.example.deliveryprojectstructuredemo.R
import com.example.deliveryprojectstructuredemo.common.Route
import com.example.deliveryprojectstructuredemo.ui.features.components.AppButton
import com.example.deliveryprojectstructuredemo.ui.features.components.AppOutlineTextField
import com.example.deliveryprojectstructuredemo.ui.features.components.AppText
import com.example.deliveryprojectstructuredemo.ui.features.components.SocialSection
import com.example.deliveryprojectstructuredemo.ui.theme.DeliveryProjectStructureDemoTheme
import com.example.deliveryprojectstructuredemo.ui.theme.spacing

@Composable
fun LoginScreen(navController: NavController, vm: LoginViewModel = hiltViewModel()) {
    val loginState = vm.loginUiState.value
    val context = LocalContext.current

    loginState.let { state ->
        if (state.isLoading) {
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
                    value = vm.email.value,
                    label = { Text(text = stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        vm.onEvent(LoginUIEvent.EmailChange(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.enter_your_email)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                AppOutlineTextField(
                    value = vm.password.value,
                    label = { Text(text = stringResource(id = R.string.password)) },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        vm.onEvent(LoginUIEvent.PasswordChange(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.your_password)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (vm.passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            vm.onEvent(LoginUIEvent.PasswordToggleChange(!vm.passwordVisibility.value))

                        }) {
                            Icon(
                                imageVector = if (vm.passwordVisibility.value)
                                    Icons.Filled.Visibility
                                else
                                    Icons.Filled.VisibilityOff, ""
                            )
                        }
                    },
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                AppText(
                    text = stringResource(id = R.string.forgot_password),
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            navController.navigate(Route.FORGOT_PASSWORD_SCREEN)
                        }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                AppButton(
                    onClick = {
                        vm.onEvent(
                            LoginUIEvent.Submit
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
                    onGoogleClick = { /*TODO*/ },
                    onFacebookClick = { /*TODO*/ },
                    onFooterClick = { navController.navigate(Route.SIGN_UP_SCREEN) })

            }

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