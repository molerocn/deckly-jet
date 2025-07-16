package com.molerocn.deckly.presentation.screens.startup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.molerocn.deckly.R
import com.molerocn.deckly.presentation.navigation.Routes
import com.molerocn.deckly.presentation.theme.myFontFamily

@Composable
fun WelcomeScreen(onNavigate: (String) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo de imagen
        Image(
            painter = painterResource(id = R.drawable.welcome_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Logo en el centro
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_sin_fondo),
                contentDescription = "Deckly Logo",
                modifier = Modifier
                    .size(480.dp) // tamaño más grande
                    .clip(CircleShape)
            )
        }

        // Contenido principal en parte inferior
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp, start = 24.dp, end = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Deckly",
                    fontFamily = myFontFamily,
                    fontSize = 60.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Aprende más rápido, olvida menos.",
                    fontSize = 14.sp,
                    color = Color.White
                )

                Button(
                    onClick = { onNavigate(Routes.LOGIN) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(text = "Continuar", color = Color.Black)
                }
            }
        }
    }
}

