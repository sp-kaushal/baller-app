package com.softprodigy.deliveryapp.ui.features.login

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.delivery_app.core.util.UiEvent
import com.delivery_app.core.util.UiText
import com.softprodigy.deliveryapp.common.ResultWrapper
import com.softprodigy.deliveryapp.data.UserInfo
import androidx.lifecycle.*
import com.softprodigy.deliveryapp.common.ResultWrapper.*
import com.softprodigy.deliveryapp.data.GoogleUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var loginRepository: LoginRepository,application: Application) :
    AndroidViewModel(application) {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _loginUiState = mutableStateOf(LoginUIState())
    val loginUiState: State<LoginUIState> = _loginUiState

    private var _userState=MutableLiveData<GoogleUserModel>()
    val googleUser:LiveData<GoogleUserModel> =_userState

/*    private var _loadingState=MutableLiveData(false)
    val loading:LiveData<Boolean> =_loadingState*/

    fun fetchSignInUser(email:String?,name: String?,idToken:String?){
//        _loadingState.value=true
        _loginUiState.value = LoginUIState(isDataLoading = true)

        viewModelScope.launch {
            _userState.value= GoogleUserModel(
                email=email,
                name = name,
                idToken =idToken,
            )
        }
//        _loadingState.value=false
        _loginUiState.value = LoginUIState(isDataLoading = false)

    }
    fun hideLoading(){
//        _loadingState.value=false
        _loginUiState.value = LoginUIState(isDataLoading = false)
    }
    fun showLoading(){
//        _loadingState.value=true
        _loginUiState.value = LoginUIState(isDataLoading = false)

    }



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
                is ResultWrapper.Success ->{
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
                is GenericError -> {
                    _loginUiState.value = loginUiState.value.copy(isDataLoading = false)
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



