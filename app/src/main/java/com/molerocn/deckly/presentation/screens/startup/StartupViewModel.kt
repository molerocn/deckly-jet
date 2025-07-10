package com.molerocn.deckly.presentation.screens.startup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface StartupUiState {
    object Loading : StartupUiState
    object LoggedIn : StartupUiState
    object NotLoggedIn : StartupUiState
}

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<StartupUiState>(StartupUiState.Loading)
    val uiState: StateFlow<StartupUiState> = _uiState.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            // set delay of 2 seconds
            delay(2000)
            val token = userRepository.getUserToken()
            if (token.isNotEmpty()) {
                _uiState.value = StartupUiState.LoggedIn
            } else {
                _uiState.value = StartupUiState.NotLoggedIn
            }
        }
    }
}
