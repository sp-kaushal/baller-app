package com.softprodigy.deliveryapp.ui.features.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delivery_app.core.util.UiEvent
import com.delivery_app.core.util.UiText
import com.softprodigy.deliveryapp.common.*
import com.softprodigy.deliveryapp.data.response.SignUpResponse
import com.softprodigy.deliveryapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private var signUpRepository: SignUpRepository) :
    ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _signUpUiState = mutableStateOf(SignUpUIState())
    val signUpUIState: State<SignUpUIState> = _signUpUiState

    var signupResponse: SignUpResponse? = null
        private set

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

                viewModelScope.launch {
                    if (!name.value.isValidFullName()) {
                        _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.enter_valid_full_name)))

                    } else if (!email.value.isValidEmail()) {
                        _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.enter_valid_email)))

                    } else if (!password.value.isValidPassword()) {
                        _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.password_error)))

                    } else if (!termsAccepted.value) {
                        _uiEvent.send(UiEvent.ShowToast(UiText.StringResource(R.string.please_accept_tems)))

                    } else {
                        signUp()
                    }
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _signUpUiState.value = SignUpUIState(isLoading = true)

            val signUpResponse =
                signUpRepository.signUpWithDetails(
                    firstName = name.value.split(" ").component1(),
                    lastName = name.value.split(" ").component2(),
                    email = email.value,
                    password = password.value
                )
            when (signUpResponse) {
                is ResultWrapper.NetworkError -> {
                    _signUpUiState.value =
                        SignUpUIState(
                            user = null,
                            errorMessage = signUpResponse.message,
                            isLoading = false
                        )
                    _uiEvent.send(UiEvent.ShowToast(UiText.DynamicString(signUpResponse.message)))
                }
                is ResultWrapper.GenericError -> {
                    _signUpUiState.value =
                        SignUpUIState(
                            user = null,
                            errorMessage = "${signUpResponse.code} ${signUpResponse.message}",
                            isLoading = false
                        )
                    _uiEvent.send(UiEvent.ShowToast(UiText.DynamicString("${signUpResponse.code} ${signUpResponse.message}")))
                }
                is ResultWrapper.Success -> {

                    signUpResponse.value.let { response ->
                        if (response.status == 200) {
                            _signUpUiState.value =
                                SignUpUIState(user = response.userInfo)

                            signupResponse = response

                            _uiEvent.send(UiEvent.Success)
                        } else {
                            _uiEvent.send(
                                UiEvent.ShowToast(
                                    UiText.DynamicString(
                                        response.message ?: AppConstants.DEFAULT_ERROR_MESSAGE
                                    )
                                )
                            )

                        }
                    }
                }
            }
        }
    }
}

