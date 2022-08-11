package com.softprodigy.deliveryapp.ui.features.create_new_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softprodigy.deliveryapp.common.ResultWrapper
import com.softprodigy.deliveryapp.common.isValidPassword

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private var resetPasswordRepository: ResetPasswordRepository) :
    ViewModel() {

    val token = mutableStateOf("")
    private val _resetPassUiState = mutableStateOf(ResetPasswordUIState())
    val resetPassUiState: State<ResetPasswordUIState> = _resetPassUiState

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility: State<Boolean> = _passwordVisibility

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> = _confirmPassword

    private val _confirmPasswordVisibility = mutableStateOf(false)
    val confirmPasswordVisibility: State<Boolean> = _confirmPasswordVisibility

    fun onEvent(event: ResetPasswordUIEvent) {
        when (event) {

            is ResetPasswordUIEvent.PasswordChange -> {
                _password.value = event.password
            }
            is ResetPasswordUIEvent.PasswordToggleChange -> {
                _passwordVisibility.value = event.showPassword
            }
            is ResetPasswordUIEvent.ConfirmPasswordChange -> {
                _confirmPassword.value = event.password
            }
            is ResetPasswordUIEvent.ConfirmPasswordToggleChange -> {
                _confirmPasswordVisibility.value = event.showPassword
            }
            is ResetPasswordUIEvent.Submit -> {
                if (!password.value.isValidPassword()) {
                    _resetPassUiState.value =
                        resetPassUiState.value.copy(errorMessage = "Password must have at least eight characters with a lowercase letter, an uppercase letter and a number")
                } else if (password.value.isEmpty() || confirmPassword.value.isEmpty()) {
                    _resetPassUiState.value =
                        resetPassUiState.value.copy(errorMessage = "Enter valid password")

                } else if (password.value != confirmPassword.value) {
                    _resetPassUiState.value =
                        resetPassUiState.value.copy(errorMessage = "Confirm password should be same as password")
                } else {
                    submit()
                }
            }
        }
    }

    private fun submit() {
        viewModelScope.launch {

            _resetPassUiState.value = resetPassUiState.value.copy(isLoading = true)

            val resetPassResponse =
                resetPasswordRepository.resetPassword(
                    token = token.value, password = confirmPassword.value
                )
            when (resetPassResponse) {
                is ResultWrapper.NetworkError -> {
                    _resetPassUiState.value = resetPassUiState.value.copy(isLoading = false)
                    _resetPassUiState.value =
                        resetPassUiState.value.copy(errorMessage = resetPassResponse.toString())

                }
                is ResultWrapper.GenericError -> {
                    _resetPassUiState.value = resetPassUiState.value.copy(isLoading = false)

                    _resetPassUiState.value =
                        resetPassUiState.value.copy(errorMessage = "${resetPassResponse.code} ${resetPassResponse.message}")

                }
                is ResultWrapper.Success -> {
                    _resetPassUiState.value = resetPassUiState.value.copy(isLoading = false)
                    if (resetPassResponse.value.status == 200) {
                        _resetPassUiState.value =
                            resetPassUiState.value.copy(message = resetPassResponse.value.message)
                    } else {
                        _resetPassUiState.value = resetPassUiState.value.copy(
                            errorMessage = resetPassResponse.value.message
                                ?: "Something went wrong"
                        )
                    }
                }
            }
        }
    }
}