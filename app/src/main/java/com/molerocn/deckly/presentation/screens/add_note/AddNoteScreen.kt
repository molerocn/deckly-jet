package com.molerocn.deckly.presentation.screens.add_note

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.molerocn.deckly.presentation.components.MyToast
import com.molerocn.deckly.presentation.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = hiltViewModel(),
    onBack: () -> Boolean,
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva Nota") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
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
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = viewModel.selectedDeck?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Mazo") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                        searchText = ""
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Buscar mazo...") },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )

                    // 🔽 Lista filtrada de mazos
                    val filteredDecks = viewModel.mazos.filter {
                        it.name.contains(searchText, ignoreCase = true)
                    }

                    if (filteredDecks.isEmpty()) {
                        DropdownMenuItem(
                            text = { Text("No hay resultados") },
                            onClick = {},
                            enabled = false
                        )
                    } else {
                        filteredDecks.forEach { deck ->
                            DropdownMenuItem(
                                text = { Text(deck.name) },
                                onClick = {
                                    viewModel.changeMazo(deck)
                                    expanded = false
                                    searchText = ""
                                }
                            )
                        }
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
                onClick = {
                    viewModel.onSave()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Guardar")
            }
        }

        if (viewModel.showDialog) {
            Toast.makeText(context, viewModel.dialogMessage, Toast.LENGTH_SHORT).show()
        }
    }
}


