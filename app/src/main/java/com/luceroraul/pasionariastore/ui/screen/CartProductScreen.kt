package com.luceroraul.pasionariastore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.luceroraul.pasionariastore.R
import com.luceroraul.pasionariastore.model.ProductCart
import com.luceroraul.pasionariastore.model.ProductWithUnit
import com.luceroraul.pasionariastore.model.format
import com.luceroraul.pasionariastore.ui.preview.CartProductViewModelFake
import com.luceroraul.pasionariastore.ui.preview.CartRepositoryFake
import com.luceroraul.pasionariastore.ui.preview.ProductRepositoryFake
import com.luceroraul.pasionariastore.ui.theme.PasionariaStoreTheme
import com.luceroraul.pasionariastore.viewmodel.CartProductViewModel
import kotlinx.coroutines.launch


@Preview
@Composable
fun ProductScreenPreview() {
    PasionariaStoreTheme {
        CartProductScreen(
            navController = rememberNavController(),
            cartProductViewModel = CartProductViewModelFake(),
            cartId = 2L,
            productCartId = 5L
        )
    }
}

@Composable
fun CartProductScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    cartProductViewModel: CartProductViewModel = hiltViewModel(),
    cartId: Long,
    productCartId: Long
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val state by cartProductViewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        launch {
            cartProductViewModel.initScreen(cartId, productCartId)
        }
    }

    LaunchedEffect(key1 = cartProductViewModel.operationCompleted) {
        cartProductViewModel.operationCompleted.collect { completed ->
            if (completed) {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(Unit) {
        launch {
            cartProductViewModel.state.value.lastSearch.collect {
                if (it) {
                    focusRequester.requestFocus()
                    focusManager.moveFocus(FocusDirection.Down)
                } else {
                    focusManager.clearFocus(true)
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { cartProductViewModel.cleanState() }
    }

    Scaffold(
        topBar = {
            ProductSearcher(
                onSearchProducts = { cartProductViewModel.searchProducts(context) },
                currentSearch = state.currentSearch,
                updateCurrentSearch = { cartProductViewModel.updateCurrentSearch(it) },
                focusRequester = focusRequester,
                data = state.productsFound,
                onProductSearchClicked = { cartProductViewModel.selectProductSearched(it) },
                onCancelSearchClicked = cartProductViewModel::cancelCurrentSearch,
                enabled = state.canSearchProducts,
                modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_horizontal))
            )
        }
    ) {
        CartProductBody(
            cartProductViewModel = cartProductViewModel,
            navController = navController,
            focusRequester = focusRequester,
            modifier = modifier.padding(it)
        )
    }


}

@Composable
fun CartProductBody(
    cartProductViewModel: CartProductViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    focusRequester: FocusRequester
) {

    val state by cartProductViewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(id = R.dimen.screen_horizontal),
                vertical = dimensionResource(
                    id = R.dimen.screen_vertical
                )
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)))
        Card(modifier = Modifier.weight(1f)) {
            ProductDescription(
                Modifier.fillMaxWidth(),
                state.currentProductWithUnit,
                formatValue = cartProductViewModel::formatPriceNumber
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)))
        Card {
            ProductFormCalculator(
                quantity = state.currentProductCart.quantity,
                modifier = Modifier,
                priceCalculated = state.currentProductCart.totalPrice.toString(),
                updateQuantity = { cartProductViewModel.updateCurrentQuantity(it) },
                focusRequester = focusRequester,
                canEditQuantity = state.canUpdateQuantity
            )
            CartProductActionButtons(
                modifier = Modifier,
                onCancelButtonClicked = navController::popBackStack,
                onAddButtonClicked = {
                    cartProductViewModel.createOrUpdateProductCart(
                        context = context,
                    )
                },
                productCart = state.currentProductCart,
                isNew = state.isNew
            )
        }
    }
}

@Composable
fun ProductDescription(
    modifier: Modifier = Modifier,
    relation: ProductWithUnit,
    formatValue: (Double) -> String
) {
    var name: String
    var description: String
    var price: String
    var unit: String
    var lastUpdate: String
    relation.let {
        it.product.let { product ->
            name = product.name
            description = product.description
            price = formatValue(product.priceList * it.unit.value)
            lastUpdate = product.lastUpdate?.format() ?: "NUNCA"
        }
        unit = it.unit.name
    }
    Card(modifier = modifier) {
        Box {
            Text(
                text = "Actualizado por ultima vez $lastUpdate", modifier = Modifier
                    .align(
                        Alignment.TopCenter
                    )
                    .padding(vertical = dimensionResource(id = R.dimen.default_value))
                    .fillMaxWidth(),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
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
                    description = formatValue(relation.product.priceList),
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
                            description = price,
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
}

@Composable
fun DescriptionItem(
    title: String,
    description: String,
    modifier: Modifier,
    focusDescription: Boolean = false
) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = description.ifEmpty { "SIN DESCRIPCIÓN" },
            textAlign = TextAlign.Center,
            color = if (focusDescription) MaterialTheme.colorScheme.tertiary else Color.Unspecified,
            modifier = modifier.fillMaxWidth(),
            style = MaterialTheme.typography.displaySmall
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSearcher(
    updateCurrentSearch: (String) -> Unit,
    onSearchProducts: () -> Unit,
    currentSearch: String,
    focusRequester: FocusRequester,
    data: List<ProductWithUnit>,
    onProductSearchClicked: (ProductWithUnit) -> Unit,
    onCancelSearchClicked: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    SearchBar(
        enabled = enabled,
        placeholder = {
            Text(
                text = "Buscador de productos...",
                style = MaterialTheme.typography.labelLarge
            )
        },
        query = currentSearch,
        onQueryChange = { updateCurrentSearch(it) },
        onSearch = { onSearchProducts() },
        active = data.isNotEmpty(),
        onActiveChange = {},
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = "search")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "cancelSearch",
                modifier = Modifier.clickable { onCancelSearchClicked() })
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded))
    ) {
        LazyColumn {
            items(data) {
                ModalSearchProductItem(it, onProductSearchClicked, modifier)
            }
        }
    }
}

@Composable
fun CartProductActionButtons(
    modifier: Modifier,
    onCancelButtonClicked: () -> Unit,
    onAddButtonClicked: () -> Unit,
    productCart: ProductCart,
    isNew: Boolean
) {
    Row {
        Button(
            onClick = onCancelButtonClicked,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.delete_active)),
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
            enabled = (productCart.quantity.toIntOrNull() ?: 0) > 0,
            onClick = onAddButtonClicked,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.update_active)),
            shape = RoundedCornerShape(
                0.dp
            ),
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Max),
        ) {
            Text(text = if (isNew) "Agregar" else "Actualizar")
        }
    }
}

@Composable
fun ProductFormCalculator(
    quantity: String,
    modifier: Modifier,
    priceCalculated: String,
    updateQuantity: (String) -> Unit,
    focusRequester: FocusRequester,
    canEditQuantity: Boolean
) {
    Card(modifier = modifier.padding(10.dp)) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            DescriptionItem(
                title = "Calculadora",
                description = priceCalculated,
                modifier = modifier,
                focusDescription = true
            )
            TextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = quantity,
                onValueChange = {
                    updateQuantity(it)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                singleLine = true,
                enabled = canEditQuantity,
                label = {
                    Text(
                        text = "Cantidad del producto",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
            )
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
        )
        Text(
            text = productWithUnit.product.description,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
        )
        HorizontalDivider(modifier = modifier.padding(5.dp))
    }
}

