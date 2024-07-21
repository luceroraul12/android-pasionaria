package com.example.pasionariastore

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pasionariastore.ui.screen.CartScreen
import com.example.pasionariastore.ui.screen.ResumeScreen

enum class MyScreens(@StringRes val title: Int) {
    Resume(title = R.string.resume),
    Cart(title = R.string.cart),
    CartProduct(title = R.string.cart_product),
    CartResume(title = R.string.cart_resume),
    CartResumes(title = R.string.cart_resumes),

}

@Preview(showBackground = true)
@Composable
fun PasionariaStore(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                    onCartButtonClicked = { navController.navigate(MyScreens.Cart.name) })
            }
            composable(route = MyScreens.Cart.name) {
                CartScreen(modifier = modifier)
            }
        }
    }
}