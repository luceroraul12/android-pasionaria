package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasionariastore.R
import com.example.pasionariastore.model.ProductCart
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import com.example.pasionariastore.viewmodel.CartViewModel

@Preview
@Composable
fun CartPreview() {
    PasionariaStoreTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            CartScreen(cartViewModel = CartViewModel(),
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                onCardProductButtonClicked = { })
        }
    }
}

@Composable
fun CartScreen(
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier,
    onCardProductButtonClicked: () -> Unit
) {
    Column(modifier = modifier.padding(horizontal = 10.dp)) {
        CartHeader(modifier)
        CartListProducts(
            productCartList = cartViewModel.state.productCartList,
            modifier = modifier,
            onCardProductButtonClicked = onCardProductButtonClicked
        )
    }
}

@Composable
fun CartHeader(modifier: Modifier) {
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
        Column(modifier = modifier.padding(5.dp)) {
            CartHeaderRow("Identificador de producto", "1234", modifier)
            CartHeaderRow("Fecha de creación", "20/07/2024 17:35", modifier)
            CartHeaderRow("Precio total", "ARS 13500", modifier)
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
    productCartList: List<ProductCart>, modifier: Modifier, onCardProductButtonClicked: () -> Unit
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
                    onCartProductClicked = onCardProductButtonClicked,
                    onDeleteProductClicked = { },
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
    data: ProductCart
) {
    Card(modifier = modifier.padding(5.dp), elevation = CardDefaults.cardElevation(3.dp)) {
        Column(modifier = modifier.padding(5.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Producto ${data.product.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Row {
                    Text(text = data.product.unit.name)
                    Text(
                        text = (data.product.unit.value * data.product.priceList).toString(),
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Text(text = data.product.description)
            ActionButtons(
                onCartProductClicked = onCartProductClicked,
                onDeleteProductClicked = onDeleteProductClicked,
                modifier.padding(horizontal = 15.dp)
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
            onClick = { /*TODO*/ }, modifier = modifier.weight(1f), colors = ButtonColors(
                containerColor = colorResource(id = R.color.delete),
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Quitar")
        }
        Button(
            onClick = onCartProductClicked, modifier = modifier.weight(1f), colors = ButtonColors(
                containerColor = colorResource(id = R.color.update),
                contentColor = Color.White,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(text = "Editar")
        }
    }
}
