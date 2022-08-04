package com.example.deliveryprojectstructuredemo.ui.features.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryprojectstructuredemo.common.ResultWrapper.*
import com.example.deliveryprojectstructuredemo.common.isValidEmail
import com.example.deliveryprojectstructuredemo.common.isValidFullName
import com.example.deliveryprojectstructuredemo.common.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private var signUpRepository: SignUpRepository) :
    ViewModel() {
    private val _signUpUiState = mutableStateOf(SignUpUIState())
    val signUpUIState: State<SignUpUIState> = _signUpUiState

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _passwordVisibility = mutableStateOf(false)
    val passwordVisibility: State<Boolean> = _passwordVisibility

    private val _termsAccepted = mutableStateOf(false)
    val termsAccepted: State<Boolean> = _termsAccepted


    fun onEvent(event: SignUpUIEvent) {
        when (event) {

            is SignUpUIEvent.NameChange -> {
                _name.value = event.name
            }

            is SignUpUIEvent.EmailChange -> {
                _email.value = event.email
            }
            is SignUpUIEvent.PasswordChange -> {
                _password.value = event.password

            }
            is SignUpUIEvent.PasswordToggleChange -> {
                _passwordVisibility.value = event.showPassword
            }
            is SignUpUIEvent.ConfirmTermsChange -> {
                _termsAccepted.value = event.acceptTerms
            }
            is SignUpUIEvent.Submit -> {

                if (!name.value.isValidFullName()) {
                    _signUpUiState.value =
                        signUpUIState.value.copy(errorMessage = "Please enter valid full name")
                } else if (!email.value.isValidEmail()) {
                    _signUpUiState.value =
                        signUpUIState.value.copy(errorMessage = "Please enter valid email")
                } else if (!password.value.isValidPassword()) {
                    _signUpUiState.value =
                        signUpUIState.value.copy(errorMessage = "Password must have at least eight characters with a lowercase letter, an uppercase letter and a number")
                } else if (!termsAccepted.value) {
                    _signUpUiState.value =
                        signUpUIState.value.copy(errorMessage = "Please accept our terms of service and Privacy Policy ")
                } else {
                    signUp()
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _signUpUiState.value = signUpUIState.value.copy(isLoading = true)

            val signUpResponse =
                signUpRepository.signUpWithDetails(
                    firstName = name.value.split(" ").component1(),
                    lastName = name.value.split(" ").component2(),
                    email = email.value,
                    password = password.value
                )
            when (signUpResponse) {
                is NetworkError -> {
                    _signUpUiState.value = signUpUIState.value.copy(isLoading = false)
                    _signUpUiState.value =
                        signUpUIState.value.copy(errorMessage = signUpResponse.toString())
                }
                is GenericError -> {
                    _signUpUiState.value = signUpUIState.value.copy(isLoading = false)
                    _signUpUiState.value =
                        signUpUIState.value.copy(errorMessage = "${signUpResponse.code} ${signUpResponse.message}")

                }
                is Success -> {
                    _signUpUiState.value = signUpUIState.value.copy(isLoading = false)
                    if (signUpResponse.value.status == 200) {
                        _signUpUiState.value =
                            signUpUIState.value.copy(user = signUpResponse.value.userInfo)

                    } else {
                        _signUpUiState.value = signUpUIState.value.copy(
                            errorMessage = signUpResponse.value.message ?: "Something went wrong"
                        )

                    }
                }
            }
        }
    }
}

