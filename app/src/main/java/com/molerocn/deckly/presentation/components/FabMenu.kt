package com.molerocn.deckly.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import com.molerocn.deckly.R
import android.util.Log
import androidx.compose.ui.graphics.Color

object Options {
    const val DECK = "Mazo"
    const val CARD = "Tarjeta"
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FabMenu(
    onAddNote: () -> Unit,
    onAddDeck: () -> Unit
) {
    var fabMenuExpanded by rememberSaveable { mutableStateOf(false) }

    BackHandler(fabMenuExpanded) { fabMenuExpanded = false }

    // Corregimos los textos para que coincidan con el when()
    val menuItems = listOf(
        painterResource(id = R.drawable.ic_new_folder) to Options.DECK,
        painterResource(id = R.drawable.ic_add_notes) to Options.CARD,
    )

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButtonMenu(
            modifier = Modifier.align(Alignment.BottomEnd),
            expanded = fabMenuExpanded,
            button = {
                ToggleFloatingActionButton(
                    modifier = Modifier
                        .semantics {
                            stateDescription = if (fabMenuExpanded) "Expandido" else "Colapsado"
                            contentDescription = "Botón de menú flotante"
                        }
                        .animateFloatingActionButton(
                            visible = true,
                            alignment = Alignment.BottomEnd
                        ),
                    checked = fabMenuExpanded,
                    onCheckedChange = { fabMenuExpanded = !fabMenuExpanded },
                ) {
                    val icon by remember {
                        derivedStateOf {
                            if (fabMenuExpanded) Icons.Filled.Close else Icons.Filled.Add
                        }
                    }
                    Icon(
                        tint = if (fabMenuExpanded) Color.White else Color.Black,
                        painter = rememberVectorPainter(icon),
                        contentDescription = null
                    )
                }
            }
        ) {
            menuItems.forEach { (icon, label) ->
                FloatingActionButtonMenuItem(
                    onClick = {
                        fabMenuExpanded = false
                        Log.i("FabMenu", "Item seleccionado: $label") // Para debug
                        when (label) {
                            Options.DECK -> onAddDeck()
                            Options.CARD -> onAddNote()
                        }
                    },
                    icon = { Icon(painter = icon, contentDescription = null) },
                    text = { Text(label) }
                )
            }
        }
    }
}
