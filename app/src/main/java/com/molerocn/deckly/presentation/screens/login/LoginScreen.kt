package com.molerocn.deckly.presentation.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.molerocn.deckly.presentation.navigation.Routes

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit,
) {
    Log.i("login", "this is login screen")
    val isForRegister by viewModel.isForRegister.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            onNavigate(Routes.HOME)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { viewModel.toggleSignUp() }) {
            Text(if (isForRegister) "No tienes cuenta? Registrate" else "Ya tienes cuenta? Inicia sesión")
        }
        Button(onClick = { viewModel.signinWithGoogle() }) {
            Text("Iniciar sesión con Google")
        }
        if (isForRegister) {
            Text("ingresa tus datos para registrarte")
        }
    }

}