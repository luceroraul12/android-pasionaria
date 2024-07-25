package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.model.ProductWithUnit
import com.example.pasionariastore.viewmodel.CartViewModel


//@Preview
//@Composable
//fun ProductScreenPreview() {
//    PasionariaStoreTheme {
//        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//            CartProductScreen(
//                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
//                onAddButtonClicked = {},
//                onCancelButtonClicked = {},
//                cartViewModel = CartViewModel()
//            )
//        }
//    }
//}

@Composable
fun CartProductScreen(
    modifier: Modifier = Modifier,
    onAddButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    cartViewModel: CartViewModel
) {
    val state = cartViewModel.state.collectAsState()
    if (state.value.showModalProductSearch) {
        // Antes de abrir el modal tengo que ver si existen coincidencias
        ModalSearchProduct(
            productList = state.value.currentProductSearcheds,
            onProductSearchClicked = { cartViewModel.selectProductSearched(it) },
            search = state.value.currentSearch,
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
            ProductSearcher(modifier.fillMaxWidth(), state.value, cartViewModel)
        }
        Spacer(modifier = modifier.padding(10.dp))
        Card(modifier = modifier.weight(1f)) {
            ProductDescription(modifier.fillMaxWidth(), state.value.currentProductCart)
        }
        Spacer(modifier = modifier.padding(10.dp))
        Card {
            ProductFormCalculator(
                modifier = modifier,
                viewModel = cartViewModel,
                state = state.value
            )
            CartProductActionButtons(
                modifier = modifier,
                onCancelButtonClicked = onCancelButtonClicked,
                onAddButtonClicked = onAddButtonClicked,
                enabled = cartViewModel.canAddProductToCart()
            )
        }
    }

}

@Composable
fun ProductDescription(modifier: Modifier = Modifier, relation: ProductCartWithData?) {
    var name: String = "Nombre del producto"
    var description: String = "Descripcion del producto"
    var price: String = "0.0"
    var unit: String = "SIN UNIDAD"
    relation?.productWithUnit?.let {
        it.product.let { product ->
            name = product.name
            description = product.description
            price = String.format("%.2f", (product.priceList * it.unit.value))
        }
        unit = it.unit.nameType
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
            DescriptionItem(
                title = "Precio Lista",
                description = "ARS ${relation?.productWithUnit?.product?.priceList.toString()}",
                modifier = modifier
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
    val context = LocalContext.current
    TextField(
        enabled = uiState.canSearchProducts,
        value = uiState.currentSearch,
        onValueChange = {
            viewModel.updateCurrentSearch(it)
        },
        singleLine = true,
        label = { Text(text = "Buscador de productos") },
        keyboardActions = KeyboardActions(onSearch = {
            viewModel.searchProducts(context = context)
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
    onAddButtonClicked: () -> Unit,
    enabled: Boolean
) {
    Row {
        Button(
            onClick = onCancelButtonClicked,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.delete)),
            shape = RoundedCornerShape(
                0.dp
            ),
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Max),
        ) {
            Text(text = "Cancelar")
        }
        Button(
            enabled = enabled,
            onClick = onAddButtonClicked,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.update)),
            shape = RoundedCornerShape(
                0.dp
            ),
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Max),
        ) {
            Text(text = "Agregar")
        }
    }
}

@Composable
fun ProductFormCalculator(viewModel: CartViewModel, state: CartUIState, modifier: Modifier) {
    Card(modifier = modifier.padding(10.dp)) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            DescriptionItem(
                title = "Calculadora",
                description = viewModel.calculatePrice(),
                modifier = modifier
            )
            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                value = state.currentProductCart?.productCart?.quantity ?: "",
                onValueChange = { viewModel.updateCurrentQuantity(it) },
                modifier = modifier.fillMaxWidth(),
                singleLine = true,
                enabled = state.currentProductCart != null,
                label = { Text(text = "Cantidad del producto") }
            )
        }
    }
}

@Preview
@Composable
fun ModalSearchProductPreview(modifier: Modifier = Modifier) {
    ModalSearchProduct(
        productList = Datasource.apiProductsWithUnit,
        "prueba",
        onProductSearchClicked = { },
        onCancelSearch = {})
}

@Composable
fun ModalSearchProduct(
    productList: List<ProductWithUnit>,
    search: String,
    onProductSearchClicked: (ProductWithUnit) -> Unit,
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
    productWithUnit: ProductWithUnit,
    onProductSearchClicked: (ProductWithUnit) -> Unit,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .clickable { onProductSearchClicked(productWithUnit) }
    ) {
        Text(
            text = productWithUnit.product.name,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = productWithUnit.product.description,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            modifier = modifier.fillMaxWidth()
        )
        HorizontalDivider(modifier = modifier.padding(5.dp))
    }
}

