package com.example.pasionariastore.ui.screen

import android.app.Dialog
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
@Preview
fun CartFormPreview(modifier: Modifier = Modifier) {
    CartForm(modifier)
}


@Preview(showBackground = true)
@Composable
fun CartListScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        CartForm(modifier)
        CartList(modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartForm(modifier: Modifier) {
    Card {
        Column(
            modifier = modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            CartFormDate(modifier)
            CartFormFilterStates(modifier.padding(5.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartFormDate(modifier: Modifier) {
    Text(text = "Rango de fechas")
    OutlinedButton(onClick = { /*TODO*/ }) {
        Text(text = "Seleccionar fechas")
    }
    ModalCartFormDate(modifier = modifier.padding(5.dp))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalCartFormDate(modifier: Modifier) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card {
            val dateState = rememberDateRangePickerState()
            DateRangePicker(state = dateState)
        }
    }
}

@Composable
fun CartFormFilterStates(modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Estados visibles")
        Row {
            FilterChip(
                modifier = modifier.weight(1f),
                selected = false,
                onClick = { /*TODO*/ },
                label = {
                    Text(
                        text = "Cancelados",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                )
            FilterChip(
                modifier = modifier.weight(1f),
                selected = false,
                onClick = { /*TODO*/ },
                label = {
                    Text(
                        text = "Pendientes",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                })
            FilterChip(
                modifier = modifier.weight(1f),
                selected = false,
                onClick = { /*TODO*/ },
                label = {
                    Text(
                        text = "Finalizados",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                })
        }
    }
}

@Composable
fun CartList(modifier: Modifier) {

}


