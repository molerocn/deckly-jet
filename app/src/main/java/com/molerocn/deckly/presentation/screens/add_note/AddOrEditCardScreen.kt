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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.molerocn.deckly.presentation.screens.study_card.ReviewCardViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.molerocn.deckly.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditCardScreen(
    deckId: Int,
    viewModel: AddOrEditCardViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onBackAfterInsert: () -> Unit,
    navController: NavController
) {
    // reviewCardViewodel: ReviewCardViewModel = hiltViewModel(),

    val cardAdded by viewModel.cardAdded.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (viewModel.isForEdit) "Editar tarjeta" else  "Crear tarjeta") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (cardAdded) {
                            onBackAfterInsert()
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!viewModel.isForEdit) {
                ExposedDropdownMenuBox(
                    expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    OutlinedTextField(
                        value = viewModel.selectedDeck?.name ?: "",
                        onValueChange = {},
                        enabled = !viewModel.isForEdit,
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
                        expanded = expanded, onDismissRequest = {
                            expanded = false
                            searchText = ""
                        }, modifier = Modifier.fillMaxWidth()
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
                        val filteredDecks = viewModel.decks.filter {
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
                                DropdownMenuItem(text = { Text(deck.name) }, onClick = {
                                    viewModel.changeMazo(deck)
                                    expanded = false
                                    searchText = ""
                                })
                            }
                        }
                    }
                }
            }

            // front
            OutlinedTextField(
                value = viewModel.frontText,
                onValueChange = { viewModel.frontText = it },
                label = { Text("Front") },
                modifier = Modifier.fillMaxWidth()
            )

            // back
            OutlinedTextField(
                value = viewModel.backText,
                onValueChange = { viewModel.backText = it },
                label = { Text("Back") },
                modifier = Modifier.fillMaxWidth()
            )

            // actionnn
            Button(
                onClick = {
                    viewModel.onSubmit()
                }, modifier = Modifier.align(Alignment.End)
            ) {
                if (viewModel.isForEdit) {
                    Text("Editar")
                } else {
                    Text("Guardar")
                }
            }
        }

        if (viewModel.successAction) {
            if (viewModel.isForEdit) {
                if (viewModel.dialogMessage.isEmpty()) {
                    // TODO: actualizar current card del view model
                    val reviewCardBackStackEntry =
                        navController.getBackStackEntry("${Routes.REVIEW_CARD}/$deckId")
                    val reviewCardViewModel: ReviewCardViewModel =
                        viewModel(reviewCardBackStackEntry)
                    reviewCardViewModel.changeCurrentCardAfterEdit(
                        viewModel.frontText, viewModel.backText
                    )
                    onBack()
                } else { // cuando la actualizacion de el card dio error xdd
                    Toast.makeText(context, viewModel.dialogMessage, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, viewModel.dialogMessage, Toast.LENGTH_SHORT).show()
            }
            viewModel.successAction = false
        }
    }
}


