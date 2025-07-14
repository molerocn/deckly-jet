package com.molerocn.deckly.presentation.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.molerocn.deckly.presentation.components.FabMenu
import com.molerocn.deckly.presentation.components.Spinner
import com.molerocn.deckly.R
import com.molerocn.deckly.presentation.navigation.Routes

@Composable
fun DeckItem(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // de verdad que yo no te entiendo
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val deckItems by viewModel.deckItems.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            sheetState.show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deckly") },
                actions = {
                    IconButton(onClick = { onNavigate(Routes.PROFILE) }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FabMenu(
                onAddNote = {
                    if (!deckItems.isEmpty()) {
                        onNavigate(Routes.ADD_NOTE)
                    } else {
                        Toast.makeText(
                            context, "No se puede agregar una tarjeta si no hay al menos un mazo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
                onAddDeck = {
                    showBottomSheet = true
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
                isLoading -> Spinner()
                deckItems.isEmpty() -> {
                    //icon
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty_box),
                            contentDescription = "No hay decks",
                            modifier = Modifier
                                .size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No hay mazos aún",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }

                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(deckItems) { deck ->
                        DeckItem(
                            title = deck.name,
                            onClick = { onNavigate("deck/${deck.id}") }
                        )
                    }
                }
            }
        }

        if (showBottomSheet) {
            AddDeckModal(sheetState, onDismissEvent = { showBottomSheet = false })
        }
        if (viewModel.showDeckError) {
            Toast.makeText(
                context,
                "No se pudo crear el mazo, asegurate de que un mazo con ese nombre ya exista",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.showDeckError = false
        }
    }
}
