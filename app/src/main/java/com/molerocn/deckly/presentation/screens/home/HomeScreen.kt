package com.molerocn.deckly.presentation.screens.home

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.molerocn.deckly.data.preferences.DataStoreManager
import com.molerocn.deckly.presentation.components.FabMenu
import com.molerocn.deckly.domain.model.Deck
import com.molerocn.deckly.presentation.navigation.Routes
import com.molerocn.deckly.presentation.screens.home.components.AddDeckModal
import com.molerocn.deckly.presentation.screens.home.components.DeckItem
import com.molerocn.deckly.presentation.screens.home.components.DeleteDialog
import com.molerocn.deckly.presentation.theme.myFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    // de verdad que yo no te entiendo
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()
    var editDeck by remember { mutableStateOf(false) }
    var openAlertDialog by remember { mutableStateOf(false) }
    var currentDeck by remember { mutableStateOf(Deck(name = "")) }
    val deckItems by viewModel.deckItems.collectAsState()
    val mountCards by viewModel.mountCards.collectAsState()

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
                title = {
                    Text(
                        text = "Deckly",
                        fontFamily = myFontFamily,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    // IconButton(onClick = { viewModel.sync() }) {
                    //     Icon(
                    //         imageVector = Icons.Filled.Refresh,
                    //         contentDescription = "Actualizar"
                    //     )
                    // }

                    IconButton(onClick = { onNavigate(Routes.PROFILE) }) {
                        if (!imageUrl.isEmpty()) {
                            Log.i("", "image url is $imageUrl")
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Perfil"
                            )
                        }
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
                isLoading -> {}
                deckItems.isEmpty() -> ThereIsNoDecksContent()
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(deckItems) { deck ->
                            val name = Uri.encode(deck.name)
                            val description = Uri.encode(deck.description)
                            val mount = deck.amountOfCardsToBeStudy
                            DeckItem(
                                deck = deck,
                                onDelete = {
                                    openAlertDialog = true
                                    currentDeck = deck
                                },
                                onEdit = {
                                    editDeck = true
                                    currentDeck = deck
                                    showBottomSheet = true
                                },
                                onClick = { onNavigate("${Routes.DECK_DETAIL}/${deck.id}/$name/$description/$mount") }
                            )
                        }
                    }
                    if (openAlertDialog) {
                        DeleteDialog(
                            title = "¿Seguro que desea eliminar este mazo?",
                            description = "Eliminar el mazo eliminará las tarjetas registradas también.",
                            onConfirmation = { viewModel.deleteDeck(currentDeck) },
                            onDismissRequest = { openAlertDialog = false }
                        )
                    }
                    // TODO: agregar componente aqui que muestre amountCards al final de todo el view
                }
            }
        }

        if (showBottomSheet) {
            AddDeckModal(
                isForEditDeck = editDeck,
                deckForEdit = currentDeck,
                sheetState,
                onDismissEvent = {
                    showBottomSheet = false
                    editDeck = false
                })
        }
        if (viewModel.showDeckError) {
            Toast.makeText(
                context,
                "No se pudo crear el mazo, asegurate de que un mazo con ese nombre no exista",
                Toast.LENGTH_SHORT
            ).show()
            viewModel.showDeckError = false
        }
    }
}
