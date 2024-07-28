package com.example.pasionariastore

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.ui.screen.CartListScreen
import com.example.pasionariastore.ui.screen.CartProductScreen
import com.example.pasionariastore.ui.screen.CartScreen
import com.example.pasionariastore.ui.screen.ResumeScreen
import com.example.pasionariastore.viewmodel.CartListViewModel
import com.example.pasionariastore.viewmodel.CartViewModel

enum class MyScreens(@StringRes val title: Int) {
    Resume(title = R.string.resume),
    CartList(title = R.string.cart_list),
    Cart(title = R.string.cart),
    CartProduct(title = R.string.cart_product),
    CartResume(title = R.string.cart_resume),
    CartResumes(title = R.string.cart_resumes),

}

@Preview(showBackground = true)
@Composable
fun PasionariaStore(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    cartViewModel: CartViewModel = viewModel(),
    cartListViewModel: CartListViewModel = viewModel(),
    dataStore: CustomDataStore = CustomDataStore(LocalContext.current)
) {
    // Intento recuperar ultimo valor de navegacion
    val backStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current
    val state = cartViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            PasionariaTopAppBar(
                MyScreens.valueOf(
                    backStackEntry?.destination?.route ?: MyScreens.Resume.name
                ), modifier
            )
        },
        floatingActionButton = {
            // El floating button solo se ve en la vista de pedidos
            if (backStackEntry?.destination?.route.equals(MyScreens.Cart.name))
                FloatingActionButton(
                    onClick = {
                        cartViewModel.goToAddNewProductCartScreen(
                            navController = navController
                        )
                    },
                ) {
                    Icon(Icons.Filled.Add, "Floating action button.")
                }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box {
            Image(
                painter = painterResource(id = R.drawable.pasionaria_logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = modifier.fillMaxSize(),
                alpha = 0.3f,
            )
            NavHost(
                navController = navController,
                startDestination = MyScreens.Resume.name,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = MyScreens.Resume.name) {
                    ResumeScreen(
                        modifier = modifier,
                        onCartButtonClicked = { navController.navigate(MyScreens.Cart.name) },
                        onCartListButtonClicked = { navController.navigate(MyScreens.CartList.name) },
                        dataStore = dataStore,
                    )
                }
                composable(route = MyScreens.CartList.name) {
                    CartListScreen(
                        cartListViewModel = cartListViewModel,
                        state = cartListViewModel.state.collectAsState()
                    )
                }
                composable(route = MyScreens.Cart.name) {
                    CartScreen(
                        modifier = modifier,
                        cartPrice = cartViewModel.calculateCartPrice(),
                        onRemoveProductCart = {
                            cartViewModel.removeProductFromCart(
                                data = it,
                                context = context
                            )
                        },
                        onCardProductButtonClicked = {
                            cartViewModel.goToUpdateProductCart(
                                product = it,
                                navController = navController
                            )
                        },
                        formatValue = { cartViewModel.formatPriceNumber(it) },
                        productCartList = state.value.productCartList
                    )
                }
                composable(route = MyScreens.CartProduct.name) {
                    CartProductScreen(
                        modifier = modifier,
                        onCancelButtonClicked = { navController.navigate(MyScreens.Cart.name) },
                        onAddButtonClicked = {
                            cartViewModel.addProductToCart(
                                navController = navController,
                                context = context
                            )
                        },
                        onCancelSearch = { cartViewModel.cancelProductSearch() },
                        priceCalculated = cartViewModel.calculatePrice(),
                        formatPriceNumber = { cartViewModel.formatPriceNumber(it) },
                        state = state.value,
                        onSearchProducts = { cartViewModel.searchProducts(context = context) },
                        updateCurrentSearch = { cartViewModel.updateCurrentSearch(it) },
                        onProductSearchClicked = { cartViewModel.selectProductSearched(it) },
                        updateQuantity = { cartViewModel.updateCurrentQuantity(it) },

                        )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasionariaTopAppBar(screen: MyScreens, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = screen.title), fontWeight = FontWeight.Bold)
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.secondaryContainer,
            navigationIconContentColor = Color.Gray,
            scrolledContainerColor = Color.Blue,
            actionIconContentColor = Color.Cyan
        )
    )
}

