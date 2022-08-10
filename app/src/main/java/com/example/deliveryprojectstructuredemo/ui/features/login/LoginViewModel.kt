package com.example.deliveryprojectstructuredemo.ui.features.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delivery_app.core.model.RepositoryResult
import com.delivery_app.core.util.UiEvent
import com.delivery_app.core.util.UiText
import com.example.deliveryprojectstructuredemo.common.ResultWrapper
import com.example.deliveryprojectstructuredemo.data.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var loginRepository: LoginRepository) :
    ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private val _loginUiState = mutableStateOf(LoginUIState())
    val loginUiState: State<LoginUIState> = _loginUiState

    var userInfo: UserInfo? = null
        private set

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.Submit -> {
                    login(event.email, event.password)
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.value = LoginUIState(isDataLoading = true)

            val loginResponse =
                loginRepository.loginWithEmailAndPass(
                    email = email,
                    password = password
                )
            when (loginResponse) {
                is ResultWrapper.Success -> {
                    loginResponse.value.let { response ->

                        if (response.status == 200) {
                            _loginUiState.value =
                                LoginUIState(
                                    user = response.userInfo,
                                    isDataLoading = false,
                                    errorMessage = null
                                )
                            userInfo=response.userInfo
                            _uiEvent.send(UiEvent.Success)
                        } else {
                            _loginUiState.value = LoginUIState(
                                user=null,
                                errorMessage = response.message ?: "Something went wrong",
                                isDataLoading = false
                            )
                        }
                }
            }
                is ResultWrapper.GenericError -> {
                    _loginUiState.value =
                        LoginUIState(
                            user=null,
                            errorMessage = "${loginResponse.code} ${loginResponse.message}",
                            isDataLoading = false
                        )
                    _uiEvent.send(UiEvent.ShowToast(UiText.DynamicString("${loginResponse.code} ${loginResponse.message}")))
                }
                is ResultWrapper.NetworkError -> {
                    _loginUiState.value =
                        LoginUIState(
                            user=null,
                            errorMessage = "${loginResponse.message}",
                            isDataLoading = false
                        )
                    _uiEvent.send(UiEvent.ShowToast(UiText.DynamicString(loginResponse.message)))
                }


        }
    }
}
    }

