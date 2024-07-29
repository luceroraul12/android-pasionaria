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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.ui.screen.CartListScreen
import com.example.pasionariastore.ui.screen.CartProductScreen
import com.example.pasionariastore.ui.screen.CartScreen
import com.example.pasionariastore.ui.screen.ResumeScreen
import com.example.pasionariastore.viewmodel.CartListViewModel
import com.example.pasionariastore.viewmodel.CartViewModel

enum class MyScreens(@StringRes val title: Int, val route: String) {
    Resume(title = R.string.resume, route = "resume"),
    CartList(title = R.string.cart_list, route = "cart"),
    Cart(title = R.string.cart, route = "cart/{cart_id}"),
    CartProduct(title = R.string.cart_product, route = "cart_product/{product_id}"),
    CartResume(title = R.string.cart_resume, route = "cart_resume"),
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
            val lastRoute =
                backStackEntry?.destination?.route ?: MyScreens.Resume.route
            PasionariaTopAppBar(
                screen = MyScreens.entries.first { lastRoute.equals(it.route) }, modifier
            )
        },
        floatingActionButton = {
            // El floating button solo se ve en la vista de pedidos
            if (backStackEntry?.destination?.route.equals(MyScreens.Cart.route))
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
                startDestination = MyScreens.Resume.route,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = MyScreens.Resume.route) {
                    // TODO: Hay que quitar el boton que accede a un pedido sin ID
                    ResumeScreen(
                        modifier = modifier,
                        onCartButtonClicked = { navController.navigate("${MyScreens.Cart.route}/1") },
                        onCartListButtonClicked = { navController.navigate(MyScreens.CartList.route) },
                        dataStore = dataStore,
                    )
                }
                composable(
                    route = MyScreens.CartList.route,
                ) {
                    CartListScreen(
                        cartListViewModel = cartListViewModel,
                        stateFlow = cartListViewModel.state,
                        onCreateNewCartClicked = { cartListViewModel.createNewCart() },
                        onDeleteCartClicked = { cartListViewModel.deleteCart(it) },
                        onNavigteToCart = { navController.navigate("${MyScreens.CartList.route}/$it") }
                    )
                }
                composable(
                    route = MyScreens.Cart.route,
                    arguments = listOf(navArgument("cart_id") { type = NavType.LongType }),
                ) {
                    val cartId: Long = it.arguments!!.getLong("cart_id")
                    LaunchedEffect(key1 = "initCart") {
                        cartViewModel.initScreenByCart(cartId)
                    }
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
                        stateFlow = cartViewModel.state,
                        cleanState = {
                            cartViewModel.cleanState()
                            navController.popBackStack()
                        }
                    )
                }
                composable(route = MyScreens.CartProduct.route) {
                    CartProductScreen(
                        modifier = modifier,
                        onCancelButtonClicked = { navController.navigate(MyScreens.Cart.route) },
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

