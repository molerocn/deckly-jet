package com.molerocn.deckly.presentation.screens.study_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.molerocn.deckly.R
import com.molerocn.deckly.presentation.navigation.Routes
import com.molerocn.deckly.presentation.screens.home.components.DeleteDialog

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ReviewCardScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: ReviewCardViewModel = hiltViewModel()
) {

    val options = listOf("De nuevo", "Difícil", "Bien", "Facil")

    val isLoading by viewModel.isLoading.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val hasHistoryStack by viewModel.hasHistoryStack.collectAsState()
    val isThereNoCards by viewModel.isThereNoCards.collectAsState()
    val isAnswerRevealed by viewModel.isAnswerRevealed.collectAsState()
    val currentCard by viewModel.currentCard.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Cerrar")
                    }
                },
                actions = {
                    if (hasHistoryStack) {
                        IconButton(onClick = { viewModel.goBackInHistory() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_undo),
                                contentDescription = "Volver a la última tarjeta"

                            )
                        }
                    }
                    if (!isThereNoCards) {
                        IconButton(onClick = {
                            showDeleteDialog = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Eliminar tarjeta"
                            )
                        }
                        IconButton(onClick = {
                            onNavigate("${Routes.ADD_NOTE}?cardId=${currentCard!!.id}&deckId=${currentCard!!.deckId}&front=${currentCard!!.front}&back=${currentCard!!.back}")
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "Editar tarjeta"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {}
                isThereNoCards -> FinishScreenContent()
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = currentCard!!.front,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(thickness = 2.dp)
                        if (isAnswerRevealed) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = currentCard!!.back,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp)
                    ) {
                        if (isAnswerRevealed) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                            ) {

                                options.forEachIndexed { index, label ->
                                    val difficult = when (index) {
                                        0 -> Difficult.AGAIN
                                        1 -> Difficult.HARD
                                        2 -> Difficult.GOOD
                                        3 -> Difficult.EASY
                                        else -> Difficult.EASY
                                    }
                                    ElevatedButton(
                                        onClick = { viewModel.reviewCard(difficult) },
                                    ) {
                                        Text(label)
                                    }
                                }
                            }
                        } else {
                            Button(
                                onClick = { viewModel.revealAnswer() },
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                            ) {
                                Text(text = "Ver respuesta")
                            }
                        }
                    }

                    if (showDeleteDialog) {
                        DeleteDialog(
                            title = "¿Estás seguro que deseas eliminar la tarjeta actual?",
                            description = "Este proceso es irreversible",
                            onDismissRequest = { showDeleteDialog = false },
                            onConfirmation = {
                                viewModel.deleteCard()
                                showDeleteDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}