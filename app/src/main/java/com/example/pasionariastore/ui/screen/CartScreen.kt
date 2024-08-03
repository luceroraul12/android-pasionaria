package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pasionariastore.R
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.ProductCartWithData
import com.example.pasionariastore.model.calculateTotalPriceLabel
import com.example.pasionariastore.model.format
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import com.example.pasionariastore.viewmodel.CartViewModel

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    PasionariaStoreTheme(
        darkTheme = false
    ) {
        CartProductItem(
            onCartProductClicked = { /*TODO*/ },
            onDeleteProductClicked = { /*TODO*/ },
            modifier = Modifier,
            data = Datasource.cartProducts.get(1),
            formatValue = { "ARS1,500" }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartListPreview() {
    PasionariaStoreTheme(
        darkTheme = true
    ) {
        CartListProducts(
            productCartList = Datasource.cartProducts,
            modifier = Modifier,
            onProductCartDelete = {},
            formatValue = { "2.3" },
            onProductCartEditClicked = { cartId, productCartId -> }
        )
    }
}

//@Preview
//@Composable
//fun CartPreview() {
//    PasionariaStoreTheme(
//        darkTheme = true
//    ) {
//        Scaffold(
//            modifier = Modifier.fillMaxSize()
//        ) { innerPadding ->
//            CartScreen(
//                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
//                onCardProductButtonClicked = {},
//                goToNewProduct = {},
//                cartViewModel = viewModel(),
//                navController = rememberNavController(),
//                initialCartId = 1
//            )
//        }
//    }
//}

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = hiltViewModel(),
    navController: NavHostController,
    initialCartId: Long
) {
    val state by cartViewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = initialCartId) {
        cartViewModel.initScreenByCart(initialCartId)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.padding(horizontal = 10.dp)) {
            CartHeader(modifier, state.cartWithData.value)
            CartListProducts(
                productCartList = state.cartWithData.value.productCartWithData,
                modifier = modifier,
                onProductCartEditClicked = { cartId, productCartId ->
                    cartViewModel.goToProductCartScreen(
                        navController = navController,
                        cartId = cartId,
                        productCartId = productCartId
                    )
                },
                onProductCartDelete = {
                    cartViewModel.removeProductFromCart(
                        data = it, context = context
                    )
                },
                formatValue = cartViewModel::formatPriceNumber
            )
        }
        FloatingActionButton(
            onClick = {
                cartViewModel.goToProductCartScreen(
                    navController = navController,
                    cartId = initialCartId,
                    productCartId = 0
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(30.dp)
        ) {
            Icon(Icons.Filled.Add, "New Product")
        }
    }
}

@Composable
fun CartHeader(modifier: Modifier, cartWithData: CartWithData) {
    Card(
        shape = RoundedCornerShape(
            topStart = 0.dp, topEnd = 0.dp, bottomStart = 25.dp, bottomEnd = 25.dp
        ),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Gray,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(5.dp),
        modifier = modifier.padding(top = 0.dp, end = 5.dp, start = 5.dp, bottom = 5.dp),
    ) {
        Column(modifier = modifier.padding(10.dp)) {
            CartHeaderRow("Identificador de producto", cartWithData.cart.id.toString(), modifier)
            CartHeaderRow("Fecha de creación", cartWithData.cart.dateCreated.format(), modifier)
            CartHeaderRow("Precio total", cartWithData.calculateTotalPriceLabel(), modifier)
        }
    }
}

@Composable
fun CartHeaderRow(firstLabel: String, secondLabel: String, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()
    ) {
        Text(text = firstLabel, fontStyle = FontStyle.Italic)
        Text(text = secondLabel, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun CartListProducts(
    productCartList: List<ProductCartWithData>,
    modifier: Modifier,
    onProductCartEditClicked: (cartId: Long, productCartId: Long) -> Unit,
    onProductCartDelete: (ProductCartWithData) -> Unit,
    formatValue: (Double) -> String
) {
    if (productCartList.isEmpty()) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay productos agregados",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            items(productCartList) {
                CartProductItem(
                    onCartProductClicked = {
                        onProductCartEditClicked(
                            it.productCart.cartId,
                            it.productCart.productCartId
                        )
                    },
                    onDeleteProductClicked = { onProductCartDelete(it) },
                    modifier = modifier,
                    data = it,
                    formatValue = formatValue
                )
            }
        }
    }
}

@Composable
fun CartProductItem(
    onCartProductClicked: () -> Unit,
    onDeleteProductClicked: () -> Unit,
    modifier: Modifier,
    data: ProductCartWithData,
    formatValue: (Double) -> String
) {
    Card(
        modifier = modifier
            .padding(5.dp)
            .alpha(0.9f),
        elevation = CardDefaults.cardElevation(3.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary)
    ) {
        Column(modifier = modifier) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = data.productWithUnit.product.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Row(modifier = modifier.padding(horizontal = 15.dp)) {
                    Text(text = "${data.productCart.quantity} ${data.productWithUnit.unit.nameType}")
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = formatValue(data.productCart.totalPrice),
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = data.productWithUnit.product.description,
                    modifier = modifier.padding(vertical = 5.dp)
                )
            }
            CardActionButtons(
                onCartProductClicked = onCartProductClicked,
                onDeleteProductClicked = onDeleteProductClicked,
                modifier = modifier,
                labelEdit = "Editar",
                labelDelete = "Eliminar"
            )
        }
    }
}

@Composable
fun CardActionButtons(
    onCartProductClicked: () -> Unit,
    onDeleteProductClicked: () -> Unit,
    modifier: Modifier = Modifier,
    labelEdit: String,
    labelDelete: String
) {
    Row() {
        Button(
            onClick = onDeleteProductClicked, colors = ButtonColors(
                containerColor = colorResource(id = R.color.delete_active),
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(
                0.dp
            ),
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Max)
        ) {
            Text(text = labelDelete, modifier = modifier)
        }
        Button(
            onClick = onCartProductClicked,
            colors = ButtonColors(
                containerColor = colorResource(id = R.color.update_active),
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(
                0.dp
            ),
            modifier = modifier
                .weight(1f)
                .height(IntrinsicSize.Max),
        ) {
            Text(text = labelEdit)
        }
    }
}
