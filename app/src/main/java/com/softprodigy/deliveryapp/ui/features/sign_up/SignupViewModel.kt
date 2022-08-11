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
import com.softprodigy.deliveryapp.ui.features.login.LoginUIEvent
import com.softprodigy.deliveryapp.ui.features.login.LoginUIState
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

    fun onEvent(event: SignUpUIEvent) {
        when (event) {

            is SignUpUIEvent.Submit -> {
                signUp(event.name, event.email, event.password)
            }

        }
    }

    private fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpUiState.value = SignUpUIState(isLoading = true)

            val signUpResponse =
                signUpRepository.signUpWithDetails(
                    firstName = name.split(" ").component1(),
                    lastName = name.split(" ").component2(),
                    email = email,
                    password = password
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

