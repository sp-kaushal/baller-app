package com.example.deliveryprojectstructuredemo.ui.features.login

import com.example.deliveryprojectstructuredemo.data.UserInfo


data class LoginUIState(
    var isDataLoading: Boolean = false,
    var errorMessage: String? = null,
    var user: UserInfo? = null,
    val userNameValid: Boolean = true,
    val userNameValidError:String? = null
    )
