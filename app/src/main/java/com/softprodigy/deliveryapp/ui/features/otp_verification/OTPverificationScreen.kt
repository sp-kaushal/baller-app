package com.softprodigy.deliveryapp.ui.features.otp_verification

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import com.softprodigy.deliveryapp.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.softprodigy.deliveryapp.common.Route
import com.softprodigy.deliveryapp.ui.features.components.AppButton
import com.softprodigy.deliveryapp.ui.features.components.AppOutlineTextField
import com.softprodigy.deliveryapp.ui.features.components.AppText
import com.softprodigy.deliveryapp.ui.theme.spacing
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPVerificationScreen(
    navController: NavController,
    token: String,
    vm: VerifyOtpViewModel = hiltViewModel()
) {

    val (editValue, setEditValue) = remember { mutableStateOf("") }
    val otpLength = remember { 6 }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val verifyOtpState = vm.verifyOtpUiState.value
    val context = LocalContext.current

    verifyOtpState.let { state ->
        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            state.errorMessage = null
        }

        state.message?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_SHORT
            ).show()
            state.message = null
            navController.navigate(Route.NEW_PASSWORD_SCREEN + "/$token")
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraXLarge))

                Image(
                    painter = painterResource(id = R.drawable.ic_otp_verification),
                    contentDescription = "Welcome Icon",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                AppText(
                    text = stringResource(id = R.string.otp_verifcation),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                AppText(
                    text = stringResource(id = R.string.please_enter_4_digit_code, "your email"),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

                AppOutlineTextField(
                    value = editValue,
                    onValueChange = {
                        if (it.length <= otpLength) {
                            Log.d("harsh", "OTPVerificationScreen: $it")
                            setEditValue(it)
                            vm.onEvent(VerifyOtpUIEvent.OtpChange(it))
                        }
                    },
                    modifier = Modifier
                        .size(0.dp)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    (0 until otpLength).map { index ->
                        OtpCell(
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    focusRequester.requestFocus()
                                    keyboard?.show()
                                }
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    RoundedCornerShape(4.dp)
                                )
                                .graphicsLayer {
                                    shadowElevation = 0.dp.toPx()
                                    shape = CutCornerShape(5.dp)
                                    clip = true
                                }
                                .background(color = MaterialTheme.colors.background),
                            value = editValue.getOrNull(index)?.toString() ?: "",
                            isCursorVisible = editValue.length == index
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                AppButton(
                    onClick = {
                        vm.token.value = token
                        vm.onEvent(
                            VerifyOtpUIEvent.Submit
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.verify))
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
                AppText(
                    text = stringResource(id = R.string.resend_code),
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable {
                        }
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
    }
}


@Composable
fun OtpCell(
    modifier: Modifier,
    value: String,
    isCursorVisible: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val (cursorSymbol, setCursorSymbol) = remember { mutableStateOf("") }

    LaunchedEffect(key1 = cursorSymbol, isCursorVisible) {
        if (isCursorVisible) {
            scope.launch {
                delay(350)
                setCursorSymbol(if (cursorSymbol.isEmpty()) "|" else "")
            }
        }
    }

    Box(
        modifier = modifier
    ) {

        AppText(
            text = if (isCursorVisible) cursorSymbol else value,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
