package com.example.pasionariastore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.ui.screen.CartListScreen
import com.example.pasionariastore.ui.screen.CartProductScreen
import com.example.pasionariastore.ui.screen.CartScreen
import com.example.pasionariastore.ui.screen.ResumeScreen
import com.example.pasionariastore.viewmodel.CartListViewModel
import com.example.pasionariastore.viewmodel.CartProductViewModel
import com.example.pasionariastore.viewmodel.CartViewModel

enum class MyScreens {
    Resume, CartList, Cart, CartProduct
}

@Preview(showBackground = true)
@Composable
fun PasionariaStore(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    cartViewModel: CartViewModel = viewModel(),
    cartListViewModel: CartListViewModel = viewModel(),
    cartProductViewModel: CartProductViewModel = viewModel(),
    dataStore: CustomDataStore = CustomDataStore(LocalContext.current)
) {
    val context = LocalContext.current
    val cartProductState by cartProductViewModel.state.collectAsState()
    val cartState by cartViewModel.state.collectAsState()
    val cartListState = cartListViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            PasionariaTopAppBar(
                modifier
            )
        }, modifier = Modifier.fillMaxSize()
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
                    // TODO: Hay que quitar el boton que accede a un pedido sin ID
                    ResumeScreen(
                        modifier = modifier,
                        onCartButtonClicked = { },
                        onCartListButtonClicked = { navController.navigate(MyScreens.CartList.name) },
                        dataStore = dataStore,
                    )
                }
                composable(
                    route = MyScreens.CartList.name,
                ) {
                    CartListScreen(
                        cartListViewModel = cartListViewModel,
                        stateFlow = cartListState,
                        onCreateNewCartClicked = {
                            cartListViewModel.createNewCart()
                        },
                        onDeleteCartClicked = { cartListViewModel.deleteCart(it) },
                        goToCartScreen = {
                            navController.navigate("${MyScreens.Cart.name}/$it")
                        })
                }
                composable(
                    route = "${MyScreens.Cart.name}/{cartId}",
                    arguments = listOf(navArgument("cartId") { type = NavType.LongType }),
                ) {
                    val cartId: Long = it.arguments!!.getLong("cartId")
                    CartScreen(
                        modifier = modifier,
                        onRemoveProductCart = {
                            cartViewModel.removeProductFromCart(
                                data = it, context = context
                            )
                        },
                        onCardProductButtonClicked = {
                            cartViewModel.goToUpdateProductCart(
                                product = it,
                                goToCartProductScreen = { cartId, productCartId ->
                                    navController.navigate("${MyScreens.CartProduct.name}/${cartId}?productCartId=${productCartId}")
                                },
                            )
                        },
                        formatValue = { cartViewModel.formatPriceNumber(it) },
                        state = cartState,
                        goToNewProduct = {
                            cartViewModel.goToAddNewProductCartScreen({ navController.navigate("${MyScreens.CartProduct.name}/$it") })
                        },
                        fetchData = { cartViewModel.initScreenByCart(cartId) })
                }
                composable(
                    route = "${MyScreens.CartProduct.name}/{cartId}?productCartId={productCartId}",
                    arguments = listOf(
                        navArgument("cartId") { type = NavType.LongType },
                        navArgument("productCartId") { type = NavType.LongType; defaultValue = 0L })
                ) {
                    val cartId: Long = it.arguments!!.getLong("cartId")
                    val produccartId: Long =
                        it.arguments?.getLong("productCartId") ?: 0L
                    CartProductScreen(
                        modifier = modifier,
                        onCancelButtonClicked = { navController.popBackStack() },
                        onAddButtonClicked = {
                            cartProductViewModel.createOrUpdateProductCart(context = context,
                                { navController.popBackStack() })
                        },
                        onCancelSearch = { cartProductViewModel.setShowModal(false) },
                        formatPriceNumber = { cartProductViewModel.formatPriceNumber(it) },
                        state = cartProductState,
                        onSearchProducts = { cartProductViewModel.searchProducts(context = context) },
                        updateCurrentSearch = { cartProductViewModel.updateCurrentSearch(it) },
                        onProductSearchClicked = { cartProductViewModel.selectProductSearched(it) },
                        updateQuantity = { cartProductViewModel.updateCurrentQuantity(it) },
                        fetchData = {
                            cartProductViewModel.initScreen(
                                cartId = cartId, productCartId = produccartId
                            )
                        })
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasionariaTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = "Pasionaria", fontWeight = FontWeight.Bold)
        }, colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.secondaryContainer,
            navigationIconContentColor = Color.Gray,
            scrolledContainerColor = Color.Blue,
            actionIconContentColor = Color.Cyan
        )
    )
}