package com.example.pasionariastore.ui.screen

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasionariastore.R
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.model.ProductCartWithProductAndUnit
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import com.example.pasionariastore.viewmodel.CartViewModel

//@Preview
//@Composable
//fun CartPreview() {
//    PasionariaStoreTheme {
//        Scaffold(
//            modifier = Modifier.fillMaxSize()
//        ) { innerPadding ->
//            CartScreen(
//                cartViewModel = CartViewModel(),
//                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
//                navController = rememberNavController()
//            )
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    CartListProducts(
        productCartList = Datasource.apiCartProducts,
        modifier = Modifier,
        onCardProductButtonClicked = {}) {
    }
}

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val state = cartViewModel.state.collectAsState()
    Column(modifier = modifier.padding(horizontal = 10.dp)) {
        CartHeader(modifier, cartViewModel.calculateCartPrice())
        CartListProducts(
            productCartList = state.value.productCartList,
            modifier = modifier,
            onCardProductButtonClicked = {
                cartViewModel.updateProductCart(
                    product = it,
                    context = context,
                    navController = navController
                )
            },
            onProductCartDete = {
                cartViewModel.removeProductFromCart(
                    product = it,
                    context = context
                )
            }
        )
    }
}

@Composable
fun CartHeader(modifier: Modifier, cartPrice: String) {
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
            CartHeaderRow("Identificador de producto", "1234", modifier)
            CartHeaderRow("Fecha de creación", "20/07/2024 17:35", modifier)
            CartHeaderRow("Precio total", "ARS $cartPrice", modifier)
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
    productCartList: List<ProductCartWithProductAndUnit>,
    modifier: Modifier,
    onCardProductButtonClicked: (ProductCartWithProductAndUnit) -> Unit,
    onProductCartDete: (ProductCartWithProductAndUnit) -> Unit
) {
    if (productCartList.isNullOrEmpty()) {
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
                    onCartProductClicked = { onCardProductButtonClicked(it) },
                    onDeleteProductClicked = { onProductCartDete(it) },
                    modifier = modifier,
                    data = it
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
    data: ProductCartWithProductAndUnit
) {
    Card(
        modifier = modifier.padding(5.dp),
        elevation = CardDefaults.cardElevation(3.dp),
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
                        text = "Producto ${data.productWithUnit.product.name}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Row(modifier = modifier.padding(horizontal = 15.dp)) {
                    Text(text = data.productCart.quantity)
                    Spacer(modifier = modifier.weight(1f))
                    Text(
                        text = "ARS ${(data.productCart.totalPrice)}",
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(text = data.productWithUnit.product.description, modifier = modifier.padding(vertical = 5.dp))
            }
            ActionButtons(
                onCartProductClicked = onCartProductClicked,
                onDeleteProductClicked = onDeleteProductClicked,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ActionButtons(
    onCartProductClicked: () -> Unit,
    onDeleteProductClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row() {
        Button(
            onClick = onDeleteProductClicked, colors = ButtonColors(
                containerColor = colorResource(id = R.color.delete),
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
            Text(text = "Quitar", modifier = modifier)
        }
        Button(
            onClick = onCartProductClicked,
            colors = ButtonColors(
                containerColor = colorResource(id = R.color.update),
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
            Text(text = "Editar")
        }
    }
}
