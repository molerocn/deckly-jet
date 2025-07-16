package com.molerocn.deckly.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.presentation.screens.home.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeckModal(
    isForEditDeck: Boolean = false,
    deckForEdit: Deck = Deck(name = ""),
    sheetState: SheetState,
    onDismissEvent: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    // Form state
    var title by remember { mutableStateOf(if (isForEditDeck) deckForEdit.name else "") }
    var description by remember { mutableStateOf(if (isForEditDeck) deckForEdit.description else "") }
    val scope = rememberCoroutineScope()

    return ModalBottomSheet(
        onDismissRequest = onDismissEvent,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Crear nuevo mazo", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Nombre del mazo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción del mazo") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        if (isForEditDeck) {
                            viewModel.editDeck(
                                modifiedDeck = deckForEdit.copy(
                                    name = title,
                                    description = description
                                ),
                                originalDeck = deckForEdit
                            )
                        } else {
                            viewModel.addDeck(title.trim(), description.trim())
                        }
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissEvent() // ocultar entre otros xd
                                title = ""
                                description = ""
                            }
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (isForEditDeck) "Editar" else "Guardar")
            }
        }
    }
}