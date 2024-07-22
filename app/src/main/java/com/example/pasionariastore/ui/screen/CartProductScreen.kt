package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pasionariastore.R
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.CartUIState
import com.example.pasionariastore.model.Product
import com.example.pasionariastore.model.ProductCart
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
    val state = cartViewModel.state
    if (state.showModalProductSearch) {
        // Antes de abrir el modal tengo que ver si existen coincidencias
        ModalSearchProduct(
            productList = state.currentProductSearcheds,
            onProductSearchClicked = { cartViewModel.selectProductSearched(it) },
            search = state.currentSearch,
            modifier = modifier,
            onCancelSearch = { cartViewModel.cancelProductSearch() }
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Card {
            ProductSearcher(modifier.fillMaxWidth(), state, cartViewModel)
        }
        Spacer(modifier = modifier.padding(10.dp))
        Card(modifier = modifier.weight(1f)) {
            ProductDescription(modifier.fillMaxWidth(), state.currentProductCart)
        }
        Spacer(modifier = modifier.padding(10.dp))
        Card {
            ProductFormCalculator(
                modifier = modifier,
                viewModel = cartViewModel,
                uiState = state
            )
            CartProductActionButtons(
                modifier = modifier.padding(15.dp),
                onAddButtonClicked = onAddButtonClicked,
                onCancelButtonClicked = onCancelButtonClicked,
            )
        }
    }

}

@Composable
fun ProductDescription(modifier: Modifier = Modifier, productCart: ProductCart?) {
    var name: String = "Nombre del producto"
    var description: String = "Descripcion del producto"
    var price: String = "0.0"
    var unit: String = "SIN UNIDAD"
    productCart?.product?.let {
        name = it.name
        description = it.description
        price = String.format("%.2f", (it.priceList * it.unit.value))
        unit = it.unit.name
    }
    Card(modifier = modifier) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
            DescriptionItem(
                title = "Producto",
                description = name,
                modifier = modifier
            )
            DescriptionItem(
                title = "Descripcion", description = description, modifier = modifier
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.weight(1f)
                ) {
                    DescriptionItem(
                        title = "Precio",
                        description = "ARS $price",
                        modifier = modifier
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.weight(1f)
                ) {
                    DescriptionItem(title = "Unidad", description = unit, modifier = modifier)
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
fun ProductSearcher(modifier: Modifier, uiState: CartUIState, viewModel: CartViewModel) {
    TextField(
        enabled = uiState.canSearchProducts,
        value = uiState.currentSearch,
        onValueChange = {
            viewModel.updateCurrentSearch(it)
        },
        singleLine = true,
        label = { Text(text = "Buscador de productos") },
        keyboardActions = KeyboardActions(onSearch = {
            viewModel.searchProducts()
        }),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = "search")
        },

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
fun ProductFormCalculator(viewModel: CartViewModel, uiState: CartUIState, modifier: Modifier) {
    Card(modifier = modifier.padding(10.dp)) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            DescriptionItem(
                title = "Precio Estimado",
                description = "ARS ${viewModel.calculatePrice()}",
                modifier = modifier
            )
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                value = uiState.currentAmount.quantity.toString(),
                onValueChange = { viewModel.updateCurrentQuantity(it) },
                modifier = modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text(text = "Cantidad del producto") }
            )
        }
    }
}

@Preview
@Composable
fun ModalSearchProductPreview(modifier: Modifier = Modifier) {
    ModalSearchProduct(
        productList = Datasource.apiProducts,
        "prueba",
        onProductSearchClicked = { },
        onCancelSearch = {})
}

@Composable
fun ModalSearchProduct(
    productList: List<Product>,
    search: String,
    onProductSearchClicked: (Product) -> Unit,
    onCancelSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(onDismissRequest = onCancelSearch) {
        Card {
            Column {
                Text(text = "Buscando ... $search ...")
                LazyColumn(modifier = modifier.height(400.dp)) {
                    items(productList) {
                        ModalSearchProductItem(it, onProductSearchClicked, modifier)
                    }
                }
                Button(
                    onClick = onCancelSearch, modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.delete))
                ) {
                    Text(text = "Cancelar")
                }
            }
        }
    }
}

@Composable
fun ModalSearchProductItem(
    product: Product,
    onProductSearchClicked: (Product) -> Unit,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clickable { onProductSearchClicked(product) }
    ) {
        Text(
            text = product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = product.description,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            modifier = modifier.fillMaxWidth()
        )
        HorizontalDivider(modifier = modifier.padding(5.dp))
    }
}

