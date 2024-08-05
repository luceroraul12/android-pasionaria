package com.example.pasionariastore.navigation

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pasionariastore.R
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.ui.screen.CartListScreen
import com.example.pasionariastore.ui.screen.CartProductScreen
import com.example.pasionariastore.ui.screen.CartScreen
import com.example.pasionariastore.ui.screen.ResumeScreen

enum class MyScreens {
    Resume, CartList, Cart, CartProduct
}

@Preview(showBackground = true)
@Composable
fun NavManager(
    modifier: Modifier = Modifier,
    dataStore: CustomDataStore = CustomDataStore(LocalContext.current)
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MyScreens.Resume.name
    ) {
        composable(route = MyScreens.Resume.name) {
            // TODO: Hay que quitar el boton que accede a un pedido sin ID
            ResumeScreen(
                modifier = modifier,
                onCartButtonClicked = {navController.navigate(MyScreens.CartList.name) },
                dataStore = dataStore,
            )
        }
        composable(
            route = MyScreens.CartList.name,
        ) {
            CartListScreen(
                navController = navController
            )
        }
        composable(
            route = "${MyScreens.Cart.name}/{cartId}",
            arguments = listOf(navArgument("cartId") { type = NavType.LongType }),
        ) {
            val cartId: Long = it.arguments!!.getLong("cartId")
            CartScreen(
                modifier = modifier,
                navController = navController,
                initialCartId = cartId
            )
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
                cartId = cartId,
                productCartId = produccartId,
                navController = navController,
                modifier = modifier,
            )
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