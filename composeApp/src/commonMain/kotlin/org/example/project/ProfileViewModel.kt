package org.example.project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ProfileUiState(
    val name: String = "Miftahul Khoiriyah",
    val bio: String = "Hi, I’m Miftahul Khoiriyah, an Informatics Engineering student at Institut Teknologi Sumatera (ITERA) with a strong interest in programming, computer systems, networks, and cybersecurity fundamentals. I continuously develop my technical skills through coursework and projects.",
    val email: String = "miftahul.123140064@student.itera.ac.id",
    val phone: String = "+62xxxxx",
    val location: String = "Indonesia",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false
)

class ProfileViewModel(
    private val settingsDataSource: SettingsDataSource
) : ViewModel() {
    private val _isEditing = MutableStateFlow(false)
    
    val uiState: StateFlow<ProfileUiState> = combine(
        settingsDataSource.isDarkMode,
        _isEditing
    ) { isDarkMode, isEditing ->
        ProfileUiState(
            isDarkMode = isDarkMode,
            isEditing = isEditing
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState()
    )

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataSource.setDarkMode(enabled)
        }
    }

    fun setEditing(editing: Boolean) {
        _isEditing.value = editing
    }

    fun updateProfile(newName: String, newBio: String) {
        // In a real app, you'd persist this too. 
        // For now, we'll just stop editing.
        _isEditing.value = false
    }
}
