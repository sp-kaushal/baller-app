package com.example.deliveryprojectstructuredemo.ui.features.sign_up

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.delivery_app.core.util.UiEvent
import com.example.deliveryprojectstructuredemo.R
import com.example.deliveryprojectstructuredemo.data.response.SignUpResponse
import com.example.deliveryprojectstructuredemo.ui.features.components.AppButton
import com.example.deliveryprojectstructuredemo.ui.features.components.AppOutlineTextField
import com.example.deliveryprojectstructuredemo.ui.features.components.AppText
import com.example.deliveryprojectstructuredemo.ui.features.components.SocialSection
import com.example.deliveryprojectstructuredemo.ui.theme.DeliveryProjectStructureDemoTheme
import com.example.deliveryprojectstructuredemo.ui.theme.spacing

@Composable
fun SignUpScreen(
    vm: SignupViewModel = hiltViewModel(),
    onSuccessfulSignUp: (SignUpResponse) -> Unit,
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val signUpState = vm.signUpUIState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        vm.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> {
                    vm.signupResponse?.let {
                        onSuccessfulSignUp(it)
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
                painter = painterResource(id = R.drawable.ic_sign_up),
                contentDescription = "Sign up Icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppText(
                text = stringResource(id = R.string.sign_up),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            AppText(
                text = stringResource(id = R.string.create_free_account_and_join_us),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AppOutlineTextField(
                value = vm.name.value,
                label = { Text(text = stringResource(id = R.string.your_name)) },
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {
                        vm.onEvent(SignUpUIEvent.NameChange(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.enter_your_name)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                AppOutlineTextField(
                    value = vm.email.value,
                    label = { Text(text = stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = {
                        vm.onEvent(SignUpUIEvent.EmailChange(it))
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
                        vm.onEvent(SignUpUIEvent.PasswordChange(it))
                    },
                    placeholder = { Text(text = stringResource(id = R.string.create_password)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (vm.passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            vm.onEvent(SignUpUIEvent.PasswordToggleChange(!vm.passwordVisibility.value))

                        }) {
                            Icon(
                                imageVector = if (vm.passwordVisibility.value)
                                    Icons.Filled.Visibility
                                else
                                    Icons.Filled.VisibilityOff, ""
                            )
                        }
                    })
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = vm.termsAccepted.value,
                        onCheckedChange = {
                            vm.onEvent(SignUpUIEvent.ConfirmTermsChange(it))
                        }
                    )

                    val annotatedString = buildAnnotatedString {
                        append(stringResource(id = R.string.by_creating_an_accound_you_agree))

                        pushStringAnnotation(tag = "terms", annotation = "https://google.com/terms")
                        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                            append(stringResource(id = R.string.terms_of_service))
                        }
                        pop()

                        append(" and ")

                        pushStringAnnotation(
                            tag = "policy",
                            annotation = "https://google.com/privacy"
                        )

                        withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                            append(stringResource(id = R.string.privacy_policy))
                        }
                        pop()
                    }

                    ClickableText(
                        text = annotatedString,
                        style = MaterialTheme.typography.h2,
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(
                                tag = "policy",
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let {
                                Log.d("policy URL", it.item)
                                uriHandler.openUri(it.item)

                            }

                            annotatedString.getStringAnnotations(
                                tag = "terms",
                                start = offset,
                                end = offset
                            ).firstOrNull()?.let {
                                Log.d("terms URL", it.item)
                                uriHandler.openUri(it.item)

                            }
                        })
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            AppButton(
                onClick = {
                    vm.onEvent(SignUpUIEvent.Submit)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.create_account))
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            SocialSection(
                onGoogleClick = { onGoogleClick },
                onFacebookClick = { onFacebookClick },
                onFooterClick = { onLoginClick })

        }
        if (signUpState.isLoading) {
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
//            SignUpScreen()
        }
    }
}