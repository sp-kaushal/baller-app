package com.example.deliveryprojectstructuredemo.ui.features.login

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.deliveryprojectstructuredemo.common.ResultWrapper.*
import com.example.deliveryprojectstructuredemo.common.isValidEmail
import com.example.deliveryprojectstructuredemo.common.isValidPassword
import com.example.deliveryprojectstructuredemo.data.GoogleUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var loginRepository: LoginRepository,application: Application) :
    AndroidViewModel(application) {
    private var _userState=MutableLiveData<GoogleUserModel>()
    val googleUser:LiveData<GoogleUserModel> =_userState

    private var _loadingState=MutableLiveData(false)
    val loading:LiveData<Boolean> =_loadingState

    fun fetchSignInUser(email:String?,name: String?,idToken:String?){
        _loadingState.value=true
        viewModelScope.launch {
            _userState.value= GoogleUserModel(
                email=email,
                name = name,
                idToken =idToken,
            )
        }
        _loadingState.value=false
    }
    fun hideLoading(){
        _loadingState.value=false
    }
    fun showLoading(){
        _loadingState.value=true
    }

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

