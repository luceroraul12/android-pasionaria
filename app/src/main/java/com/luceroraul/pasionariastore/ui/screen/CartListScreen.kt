package com.luceroraul.pasionariastore.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.luceroraul.pasionariastore.R
import com.luceroraul.pasionariastore.components.CustomIconButton
import com.luceroraul.pasionariastore.components.CustomScaffold
import com.luceroraul.pasionariastore.components.MainTopBar
import com.luceroraul.pasionariastore.data.Datasource
import com.luceroraul.pasionariastore.model.Cart
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.calculateTotalPriceLabel
import com.luceroraul.pasionariastore.model.format
import com.luceroraul.pasionariastore.model.state.CartListUIState
import com.luceroraul.pasionariastore.model.state.CartStatus
import com.luceroraul.pasionariastore.navigation.MyScreens
import com.luceroraul.pasionariastore.ui.preview.CartListViewModelFake
import com.luceroraul.pasionariastore.ui.preview.SharedViewModelFake
import com.luceroraul.pasionariastore.ui.theme.PasionariaStoreTheme
import com.luceroraul.pasionariastore.viewmodel.CartListViewModel
import com.luceroraul.pasionariastore.viewmodel.SharedViewModel

@Composable
@Preview
fun CartFormPreview(modifier: Modifier = Modifier) {
    val state = CartListUIState()
    PasionariaStoreTheme(darkTheme = true) {
        CartForm(
            modifier = modifier,
            stateButtons = state.stateFilters,
            onUpdateChip = {})

    }
}

@Composable
@Preview
fun CartItemPreview(modifier: Modifier = Modifier) {
    PasionariaStoreTheme(darkTheme = true) {
        Column {
            CartItem(
                modifier = modifier,
                cartWithData = Datasource.cartWithData.get(0),
                onDeleteCartClicked = {},
                onCartClicked = {})
            CartItem(
                modifier = modifier,
                cartWithData = Datasource.cartWithData.get(1),
                onDeleteCartClicked = {},
                onCartClicked = {})
            CartItem(
                modifier = modifier,
                cartWithData = Datasource.cartWithData.get(2),
                onDeleteCartClicked = {},
                onCartClicked = {})
            CartItem(
                modifier = modifier,
                cartWithData = Datasource.cartWithData.get(3),
                onDeleteCartClicked = {},
                onCartClicked = {})
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CartListScreenPreview(modifier: Modifier = Modifier) {
    PasionariaStoreTheme(darkTheme = false) {
        CartListScreen(
            navController = rememberNavController(),
            cartListViewModel = CartListViewModelFake(),
            sharedViewModel = SharedViewModelFake(LocalContext.current)
        )
    }
}


@Composable
fun CartListScreen(
    modifier: Modifier = Modifier,
    cartListViewModel: CartListViewModel = hiltViewModel(),
    navController: NavHostController,
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val state by cartListViewModel.state.collectAsState()
    CustomScaffold(
        sharedViewModel = sharedViewModel,
        navController = navController,
        content = {
            CartListBody(
                state = state,
                modifier = modifier.padding(it),
                onUpdateChip = { cartListViewModel.updateChipStatus(it) },
                onDeleteCart = { cartListViewModel.deleteCart(it) },
                onGoToCartClicked = {
                    navController.navigate("${MyScreens.Cart.name}/${it.id}")
                },
            )
        },
        topBar = {
            MainTopBar(
                title = stringResource(id = R.string.title_cart_list_screen),
                showBackIcon = true,
                onBackClicked = { navController.popBackStack() },
                actions = {
                    CustomIconButton(
                        onClick = cartListViewModel::trySynchronize,
                        iconId = R.drawable.sync,
                        enabled = state.hasCartsToSynchronized.value
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = cartListViewModel::createNewCart,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.floating_button))
            ) {
                Icon(painter = painterResource(id = R.drawable.cart_new), "New Cart")
            }
        }
    )
}

@Composable
fun CartListBody(
    state: CartListUIState,
    modifier: Modifier = Modifier,
    onUpdateChip: (CartStatus) -> Unit,
    onDeleteCart: (Cart) -> Unit,
    onGoToCartClicked: (Cart) -> Unit,
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            CartForm(
                modifier = Modifier,
                stateButtons = state.stateFilters,
                onUpdateChip = onUpdateChip
            )
            CartList(
                modifier = Modifier,
                carts = state.cartsWithData,
                onDeleteCartClicked = onDeleteCart,
                onCartClicked = onGoToCartClicked
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartForm(
    modifier: Modifier,
    stateButtons: MutableList<CartStatus>,
    onUpdateChip: (CartStatus) -> Unit
) {
    Card(
        shape = RoundedCornerShape(0.dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CartFormFilterStates(
                modifier = modifier,
                status = stateButtons,
                onUpdateChip = { onUpdateChip(it) })
        }
    }
}

@Composable
fun CartFormFilterStates(
    modifier: Modifier,
    status: MutableList<CartStatus>,
    onUpdateChip: (CartStatus) -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        status.forEach {
            FilterChip(
                modifier = modifier
                    .height(IntrinsicSize.Max)
                    .weight(1f),
                selected = it.enabled,
                onClick = { onUpdateChip(it) },
                label = {
                    Text(
                        text = it.label,
                        modifier = modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                shape = RoundedCornerShape(0.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = colorResource(id = it.backgroundColorActive),
                    selectedLabelColor = Color.White,
                    labelColor = Color.White,
                    containerColor = colorResource(id = it.backgroundColorInactive)
                ),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartList(
    modifier: Modifier,
    carts: MutableList<CartWithData>,
    onDeleteCartClicked: (Cart) -> Unit,
    onCartClicked: (Cart) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_horizontal))
    ) {
        item(key = 0) { Spacer(modifier = Modifier.padding(1.dp)) }
        items(carts, key = { it.cart.id }) {
            CartItem(
                modifier.animateItemPlacement(),
                it,
                onDeleteCartClicked = onDeleteCartClicked,
                onCartClicked = onCartClicked
            )
        }
    }
}

@Composable
fun CartItem(
    modifier: Modifier,
    cartWithData: CartWithData,
    onDeleteCartClicked: (Cart) -> Unit,
    onCartClicked: (Cart) -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.card_vertical))
            .alpha(0.9f),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondary)
    ) {
        Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
            CartItemStatusLabel(
                modifier = modifier,
                cart = CartStatus.valueOf(cartWithData.cart.status)
            )
            Column(modifier = modifier.padding(10.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "#${cartWithData.cart.id}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = cartWithData.calculateTotalPriceLabel(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = cartWithData.cart.dateCreated.format(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${cartWithData.productCartWithData.size} Productos",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            val cartStatus = CartStatus.valueOf(cartWithData.cart.status)
            CardActionButtons(
                onCartProductClicked = { onCartClicked(cartWithData.cart) },
                onDeleteProductClicked = { onDeleteCartClicked(cartWithData.cart) },
                labelDelete = "Eliminar",
                labelEdit = if (cartStatus.canEditProducts) "Editar" else "Ver",
                canDelete = cartStatus.canDeleteCart,
                canNext = true
            )
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
            .background(colorResource(id = cart.backgroundColorActive))
    ) {
        Text(
            text = cart.label,
            textAlign = TextAlign.Center,
            modifier = modifier,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
    }

}


