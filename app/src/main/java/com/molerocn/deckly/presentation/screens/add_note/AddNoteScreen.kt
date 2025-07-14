package com.molerocn.deckly.presentation.screens.add_note

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = hiltViewModel(),
    onBack: Function0<Boolean>
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Nota") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dropdown de Mazo
            Box {
                OutlinedTextField(
                    value = viewModel.selectedDeck?.name ?: "",
                    onValueChange = {},
                    label = { Text("Mazo") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    viewModel.mazos.forEach { deck ->
                        DropdownMenuItem(
                            text = { Text(deck.name) },
                            onClick = {
                                viewModel.changeMazo(deck)
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Front
            OutlinedTextField(
                value = viewModel.frontText,
                onValueChange = { viewModel.frontText = it },
                label = { Text("Front") },
                modifier = Modifier.fillMaxWidth()
            )

            // Back
            OutlinedTextField(
                value = viewModel.backText,
                onValueChange = { viewModel.backText = it },
                label = { Text("Back") },
                modifier = Modifier.fillMaxWidth()
            )

            // Guardar
            Button(
                onClick = { viewModel.onGuardar() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Guardar")
            }
        }

        if (viewModel.showDialog) {
            Toast.makeText(context, "Tarjeta creada con éxito", Toast.LENGTH_SHORT).show()
        }
    }
}

