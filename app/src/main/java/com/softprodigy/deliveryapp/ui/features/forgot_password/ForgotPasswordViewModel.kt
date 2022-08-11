package com.softprodigy.deliveryapp.ui.features.forgot_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.softprodigy.deliveryapp.common.ResultWrapper
import com.softprodigy.deliveryapp.common.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private var forgotPasswordRepository: ForgotPasswordRepository,
) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _forgotPassUiState = mutableStateOf(ForgotPasswordUIState())
    val forgotPassUiState: State<ForgotPasswordUIState> = _forgotPassUiState
    val verifyToken = mutableStateOf(String())
    fun onEvent(event: ForgotPasswordUIEvent) {
        when (event) {

            is ForgotPasswordUIEvent.EmailChange -> {
                _email.value = event.email
            }

            is ForgotPasswordUIEvent.Submit -> {
                if (!email.value.isValidEmail()) {
                    _forgotPassUiState.value =
                        forgotPassUiState.value.copy(errorMessage = "Please enter valid email")
                } else {
                    submit()
                }
            }
        }
    }

    private fun submit() {
        viewModelScope.launch {

            _forgotPassUiState.value = forgotPassUiState.value.copy(isLoading = true)

            val forgotPassResponse =
                forgotPasswordRepository.forgotPassword(
                    email = email.value,
                )
            when (forgotPassResponse) {
                is ResultWrapper.NetworkError -> {
                    _forgotPassUiState.value = forgotPassUiState.value.copy(isLoading = false)
                    _forgotPassUiState.value =
                        forgotPassUiState.value.copy(errorMessage = forgotPassResponse.toString())

                }
                is ResultWrapper.GenericError -> {
                    _forgotPassUiState.value = forgotPassUiState.value.copy(isLoading = false)

                    _forgotPassUiState.value =
                        forgotPassUiState.value.copy(errorMessage = "${forgotPassResponse.code} ${forgotPassResponse.message}")

                }
                is ResultWrapper.Success -> {
                    _forgotPassUiState.value = forgotPassUiState.value.copy(isLoading = false)
                    if (forgotPassResponse.value.status == 200) {
                        verifyToken.value = forgotPassResponse.value.verifyToken
                        _forgotPassUiState.value =
                            forgotPassUiState.value.copy(message = forgotPassResponse.value.message)
                    } else {
                        _forgotPassUiState.value = forgotPassUiState.value.copy(
                            errorMessage = forgotPassResponse.value.message
                                ?: "Something went wrong"
                        )
                    }
                }
            }
        }
    }
}