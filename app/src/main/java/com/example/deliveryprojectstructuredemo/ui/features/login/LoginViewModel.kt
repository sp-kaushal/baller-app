package com.example.deliveryprojectstructuredemo.ui.features.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryprojectstructuredemo.common.ResultWrapper.*
import com.example.deliveryprojectstructuredemo.common.isValidEmail
import com.example.deliveryprojectstructuredemo.common.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var loginRepository: LoginRepository) :
    ViewModel() {
    private val _loginUiState = mutableStateOf(LoginUIState())
    val loginUiState: State<LoginUIState> = _loginUiState

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility: State<Boolean> = _passwordVisibility


    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChange -> {
                _email.value = event.email
            }
            is LoginUIEvent.PasswordChange -> {
                _password.value = event.password

            }
            is LoginUIEvent.PasswordToggleChange -> {
                _passwordVisibility.value = event.showPassword
            }
            is LoginUIEvent.Submit -> {
                if (!email.value.isValidEmail()) {
                    _loginUiState.value =
                        loginUiState.value.copy(errorMessage = "Please enter valid email")
                } else if (!password.value.isValidPassword()) {
                    _loginUiState.value =
                        loginUiState.value.copy(errorMessage = "Password must have at least eight characters with a lowercase letter, an uppercase letter and a number")
                } else {

                    login()
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            Log.i("LoginViewModel", "login: ")
            _loginUiState.value = loginUiState.value.copy(isLoading = true)

            val loginResponse =
                loginRepository.loginWithEmailAndPass(
                    email = email.value,
                    password = password.value
                )
            when (loginResponse) {
                is NetworkError -> {
                    _loginUiState.value = loginUiState.value.copy(isLoading = false)
                    _loginUiState.value =
                        loginUiState.value.copy(errorMessage = loginResponse.toString())

                }
                is GenericError -> {
                    _loginUiState.value = loginUiState.value.copy(isLoading = false)
                    _loginUiState.value =
                        loginUiState.value.copy(errorMessage = "${loginResponse.code} ${loginResponse.message}")

                }
                is Success -> {
                    _loginUiState.value = loginUiState.value.copy(isLoading = false)
                    if (loginResponse.value.status == 200) {
                        _loginUiState.value =
                            loginUiState.value.copy(user = loginResponse.value.userInfo)

                    } else {
                        _loginUiState.value = loginUiState.value.copy(
                            errorMessage = loginResponse.value.message ?: "Something went wrong"
                        )

                    }
                }
            }
        }
    }
}

