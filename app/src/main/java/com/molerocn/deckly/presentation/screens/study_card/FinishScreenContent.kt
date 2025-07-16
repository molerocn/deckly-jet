package com.molerocn.deckly.presentation.screens.study_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molerocn.deckly.R

@Composable
fun FinishScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_box),
            contentDescription = "No hay tarjetas",
            modifier = Modifier
                .size(50.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¡Felicidades! Estudiaste todas las tarjetas de hoy, sigue así",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}