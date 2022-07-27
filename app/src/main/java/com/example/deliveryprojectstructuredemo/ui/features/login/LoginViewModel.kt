package com.example.deliveryprojectstructuredemo.ui.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.SignedOut)

    val uiState: StateFlow<UIState> = _uiState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UIState.InProgress
            delay(2000)
            _uiState.value = UIState.SignIn("User008")
        }
    }

    sealed class UIState {
        object SignedOut : UIState()
        object InProgress : UIState()
        object Error : UIState()
        class SignIn(val userName: String) : UIState()
    }
}