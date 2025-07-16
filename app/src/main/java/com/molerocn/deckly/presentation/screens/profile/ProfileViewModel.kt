package com.molerocn.deckly.presentation.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.data.preferences.DataStoreManager
import com.molerocn.deckly.data.repository.UserRepository
import com.molerocn.deckly.domain.usecase.AddCardUseCase
import com.molerocn.deckly.domain.usecase.GetDecksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _imageUrl = MutableStateFlow("")
    val imageUrl: StateFlow<String> = _imageUrl.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _imageUrl.value = dataStoreManager.getImageUrl()
            _name.value = dataStoreManager.getUserName()
        }
    }

    fun logOut() {
        viewModelScope.launch {
            userRepository.clearUserSession()
        }
    }

    val isDarkTheme = dataStoreManager.themePreferenceFlow
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun toggleTheme(enabled: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setThemePreference(enabled)
        }
    }


};