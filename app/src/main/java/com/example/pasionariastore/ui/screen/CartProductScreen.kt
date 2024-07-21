package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasionariastore.R
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import com.example.pasionariastore.viewmodel.CartViewModel


@Preview
@Composable
fun ProductScreenPreview() {
    PasionariaStoreTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CartProductScreen(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                onAddButtonClicked = {},
                onCancelButtonClicked = {},
                cartViewModel = CartViewModel()
            )
        }
    }
}

@Composable
fun CartProductScreen(
    modifier: Modifier = Modifier,
    onAddButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    cartViewModel: CartViewModel
) {
    val cartUiState = cartViewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card {
            ProductSearcher(modifier.fillMaxWidth(), cartUiState.value.canSearchProducts)
        }
        Spacer(modifier = modifier.padding(10.dp))
        Card(modifier = modifier.weight(1f)) {
            ProductDescription(modifier.fillMaxWidth())
        }
        Spacer(modifier = modifier.padding(10.dp))
        Card {
            ProductFormCalculator(modifier)
            CartProductActionButtons(
                modifier = modifier.padding(15.dp),
                onAddButtonClicked = onAddButtonClicked,
                onCancelButtonClicked = onCancelButtonClicked,
            )
        }
    }
}

@Composable
fun ProductDescription(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            DescriptionItem(
                title = "Producto", description = "Nombre del producto", modifier = modifier
            )
            DescriptionItem(
                title = "Descripcion", description = "Descripcion del producto", modifier = modifier
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.weight(1f)
                ) {
                    DescriptionItem(
                        title = "Precio",
                        description = "ARS 14500",
                        modifier = modifier
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.weight(1f)
                ) {
                    DescriptionItem(title = "Unidad", description = "x100gr", modifier = modifier)
                }
            }
        }

    }
}

@Composable
fun DescriptionItem(title: String, description: String, modifier: Modifier) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = description,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )

    }
}

@Composable
fun ProductSearcher(modifier: Modifier, canSearch: Boolean) {
    TextField(
        readOnly = !canSearch,
        value = "EXAMPLE",
        onValueChange = {},
        singleLine = true,
        label = { Text(text = "Buscador de productos") },
        modifier = modifier
    )
}

@Composable
fun CartProductActionButtons(
    modifier: Modifier,
    onCancelButtonClicked: () -> Unit,
    onAddButtonClicked: () -> Unit
) {
    Row {
        Button(
            onClick = onAddButtonClicked,
            modifier = modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.update))
        ) {
            Text(text = "Agregar")
        }
        Button(
            onClick = onCancelButtonClicked, modifier = modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.delete))
        ) {
            Text(text = "Cancelar")
        }
    }
}

@Composable
fun ProductFormCalculator(modifier: Modifier) {
    Card(modifier = modifier.padding(10.dp)) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            DescriptionItem(
                title = "Precio Estimado",
                description = "ARS 1550",
                modifier = modifier
            )
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                value = "99.66",
                onValueChange = {},
                modifier = modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(text = "Cantidad del producto") },
            )
        }
    }
}
