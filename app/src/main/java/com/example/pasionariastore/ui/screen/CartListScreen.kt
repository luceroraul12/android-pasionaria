package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pasionariastore.data.Datasource
import com.example.pasionariastore.model.Cart
import com.example.pasionariastore.model.state.CartListUIState
import com.example.pasionariastore.model.state.CartStatus
import com.example.pasionariastore.viewmodel.CartListViewModel

@Composable
@Preview
fun CartFormPreview(modifier: Modifier = Modifier) {
    val state = CartListUIState()
    CartForm(
        modifier = modifier,
        stateButtons = state.stateFilters,
        onUpdateChip = {})
}

@Composable
@Preview
fun CartItemPreview(modifier: Modifier = Modifier) {
    Column {
        CartItem(modifier = modifier, cart = Datasource.carts.get(0))
        CartItem(modifier = modifier, cart = Datasource.carts.get(1))
        CartItem(modifier = modifier, cart = Datasource.carts.get(2))
    }
}

//@Composable
//@Preview
//fun ListPreview(modifier: Modifier = Modifier) {
//    CartList(modifier = modifier, carts = Datasource.carts)
//}


@Composable
fun CartListScreen(
    modifier: Modifier = Modifier,
    cartListViewModel: CartListViewModel,
    state: CartListUIState
) {
    Column(modifier = modifier.fillMaxSize()) {
        CartForm(
            modifier = modifier,
            stateButtons = state.stateFilters,
            onUpdateChip = { cartListViewModel.onUpdateChip(it) }
        )
        CartList(modifier, state.carts)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartForm(
    modifier: Modifier,
    stateButtons: List<CartStatus>,
    onUpdateChip: (CartStatus) -> Unit
) {
    Card {
        Column(
            modifier = modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CartFormFilterStates(
                modifier.padding(5.dp),
                status = stateButtons,
                onUpdateChip = { onUpdateChip(it) })
        }
    }
}

@Composable
fun CartFormFilterStates(
    modifier: Modifier,
    status: List<CartStatus>,
    onUpdateChip: (CartStatus) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            LazyRow {
                items(status) {
                    FilterChip(
                        modifier = modifier.weight(1f),
                        selected = it.enabled,
                        onClick = { onUpdateChip(it) },
                        label = {
                            Text(
                                text = it.label,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(id = it.backgroundColor),
                            selectedLabelColor = Color.White
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun CartList(modifier: Modifier, carts: List<Cart>) {
    LazyColumn {
        items(carts) {
            CartItem(modifier, it)
        }
    }
}

@Composable
fun CartItem(modifier: Modifier, cart: Cart) {
    Card(
        modifier = modifier
            .padding(5.dp)
            .alpha(0.9f),
        elevation = CardDefaults.cardElevation(3.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary)
    ) {
        Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
            CartItemStatusLabel(modifier = modifier, cart = CartStatus.valueOf(cart.status))
            Column(modifier = modifier.padding(10.dp)) {
                Text(text = "Pedido ${cart.id}")
                Text(text = cart.usernameSeller)
                Text(text = cart.dateCreated.toString())
                Text(text = cart.status)
            }
            if (cart.status.equals(CartStatus.PENDING.name))
                CardActionButtons(
                    onCartProductClicked = { /*TODO*/ },
                    onDeleteProductClicked = { /*TODO*/ })
        }
    }
}

@Composable
fun CartItemStatusLabel(modifier: Modifier, cart: CartStatus) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(colorResource(id = cart.backgroundColor))
    ) {
        Text(
            text = cart.label,
            textAlign = TextAlign.Center,
            modifier = modifier,
            color = Color.White
        )
    }

}


