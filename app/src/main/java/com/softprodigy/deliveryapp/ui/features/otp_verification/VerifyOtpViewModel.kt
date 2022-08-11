package com.example.deliveryprojectstructuredemo.ui.features.otp_verification

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.common.isValidEmail
import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordRepository
import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordUIEvent
import com.example.deliveryprojectstructuredemo.ui.features.forgot_password.ForgotPasswordUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private var repository: VerifyOtpRepository
) : ViewModel() {

    private val _otp = mutableStateOf("")
    val otp: State<String> = _otp
    val token = mutableStateOf("")
    private val _verifyOtpUiState = mutableStateOf(VerifyOtpUIState())
    val verifyOtpUiState: State<VerifyOtpUIState> = _verifyOtpUiState

    fun onEvent(event: VerifyOtpUIEvent) {
        when (event) {

            is VerifyOtpUIEvent.OtpChange -> {
                _otp.value = event.otp
            }

            is VerifyOtpUIEvent.Submit -> {
                if (otp.value.isEmpty()) {
                    _verifyOtpUiState.value =
                        verifyOtpUiState.value.copy(errorMessage = "Please enter valid OTP")
                } else {
                    confirm()
                }
            }
        }
    }

    private fun confirm() {

        viewModelScope.launch {

            _verifyOtpUiState.value = verifyOtpUiState.value.copy(isLoading = true)

            val verifyOtpResponse =
                repository.verifyOtp(
                    token = token.value,
                    otp = otp.value,
                    otpType = "email"
                )

            when (verifyOtpResponse) {
                is ResultWrapper.NetworkError -> {
                    _verifyOtpUiState.value = verifyOtpUiState.value.copy(isLoading = false)
                    _verifyOtpUiState.value =
                        verifyOtpUiState.value.copy(errorMessage = verifyOtpResponse.toString())
                }
                is ResultWrapper.GenericError -> {
                    _verifyOtpUiState.value = verifyOtpUiState.value.copy(isLoading = false)
                    _verifyOtpUiState.value =
                        verifyOtpUiState.value.copy(errorMessage = verifyOtpResponse.message)
                }
                is ResultWrapper.Success -> {
                    _verifyOtpUiState.value = verifyOtpUiState.value.copy(isLoading = false)
                    if (verifyOtpResponse.value.status == 200) {
                        _verifyOtpUiState.value =
                            verifyOtpUiState.value.copy(message = verifyOtpResponse.value.message)
                    } else {
                        _verifyOtpUiState.value = verifyOtpUiState.value.copy(
                            errorMessage = verifyOtpResponse.value.message
                                ?: "Something went wrong"
                        )
                    }
                }
            }
        }

    }
}