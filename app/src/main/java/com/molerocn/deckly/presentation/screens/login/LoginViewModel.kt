package com.molerocn.deckly.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.core.AuthHelper
import com.molerocn.deckly.domain.usecase.SignInWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val authHelper: AuthHelper
) : ViewModel() {

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _isForRegister = MutableStateFlow(false)
    val isForRegister: StateFlow<Boolean> = _isForRegister.asStateFlow()

    fun signinWithGoogle() {
        viewModelScope.launch {
            val token = authHelper.getTokenFromGoogle()
            val response = signInWithGoogleUseCase(token)
            if (response != null) {
                _loginSuccess.value = true
            }
        }
    }

    fun toggleSignUp() {
        _isForRegister.value = !_isForRegister.value
    }
}