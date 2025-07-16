package com.molerocn.deckly.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.molerocn.deckly.data.preferences.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    dataStore: DataStoreManager
) : ViewModel() {
    val isDarkTheme = dataStore.themePreferenceFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}
