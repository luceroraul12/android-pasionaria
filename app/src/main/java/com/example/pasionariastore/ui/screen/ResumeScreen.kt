package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pasionariastore.R
import com.example.pasionariastore.components.MainTopBar
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.MenuItem
import com.example.pasionariastore.model.state.CartStatus
import com.example.pasionariastore.ui.preview.ResumeViewModelFake
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import com.example.pasionariastore.viewmodel.ResumeViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
private fun MenuItemPreview() {
    PasionariaStoreTheme {
        Column {
            MenuButton(
                imageVector = Icons.Default.ShoppingCart,
                name = "Pedidos",
                onNavigate = {},
                enable = true
            )
            MenuButton(
                imageVector = Icons.Default.ShoppingCart,
                name = "Pedidos",
                onNavigate = {},
                enable = false
            )
        }
    }
}

@Preview
@Composable
private fun ResumeMonthlyPreview() {
    PasionariaStoreTheme {
        ResumeMonthly(
            "Agosto - 2024",
            emptyList(),
            { cartWithData: List<CartWithData>, optionalString: String? -> Pair(2, 12324) })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResumeScreen() {
    PasionariaStoreTheme {
        ResumeScreen(
            modifier = Modifier,
            dataStore = CustomDataStore(LocalContext.current),
            navController = rememberNavController(),
            resumeViewModel = ResumeViewModelFake()
        )
    }
}

@Preview
@Composable
private fun ResumeActionButtonsPreview() {
    ResumeActionButtons(
        navController = rememberNavController(), menuItems = listOf(
            MenuItem(
                imageVector = Icons.Default.ShoppingCart,
                name = "Pedidos",
                onNavigatePath = ""
            )
        )
    )
}

@Composable
fun ResumeScreen(
    modifier: Modifier = Modifier,
    dataStore: CustomDataStore,
    navController: NavHostController,
    resumeViewModel: ResumeViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        MainTopBar(title = "Pasionaria", onBackClicked = { /*TODO*/ }) {

        }
    }) {
        ResumeScreenBody(
            modifier = modifier.padding(it),
            dataStore = dataStore,
            navController = navController,
            resumeViewModel = resumeViewModel
        )
    }
}

@Composable
fun ResumeScreenBody(
    modifier: Modifier,
    dataStore: CustomDataStore,
    navController: NavHostController,
    resumeViewModel: ResumeViewModel
) {
    val darkMode = dataStore.getDarkMode.collectAsState(initial = false)
    val scope = rememberCoroutineScope();
    val state by resumeViewModel.state.collectAsState()
    val menuItems = resumeViewModel.menuItems

    LaunchedEffect(Unit) {
        resumeViewModel.initScreen()
    }

    Column(
        modifier = modifier.padding(
            horizontal = dimensionResource(id = R.dimen.screen_horizontal),
            vertical = dimensionResource(
                id = R.dimen.screen_vertical
            )
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            ResumeMonthly(
                label = state.label,
                state.cartsWithData,
                calculate = resumeViewModel::calculatePairResume
            )
            ResumeActionButtons(
                menuItems = menuItems,
                navController = navController
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Modo oscuro")
                Switch(checked = darkMode.value, onCheckedChange = {
                    scope.launch {
                        dataStore.saveDarkMode(!darkMode.value)
                    }
                })
            }
        }
    }

}

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    name: String,
    onNavigate: () -> Unit,
    enable: Boolean
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Button(
            onClick = onNavigate,
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded)),
            enabled = enable
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = "",
                    modifier = Modifier.size(100.dp),
                )
                Text(text = name, style = MaterialTheme.typography.headlineMedium)
            }

        }

    }
}

@Composable
fun ResumeMonthly(
    label: String,
    cartsWithData: List<CartWithData>,
    calculate: (List<CartWithData>, status: String?) -> Pair<Int, Int>
) {
    val pairAll: Pair<Int, Int> = calculate(cartsWithData, null)
    val pairPending: Pair<Int, Int> = calculate(cartsWithData, CartStatus.PENDING.name)
    val pairFinalize: Pair<Int, Int> = calculate(cartsWithData, CartStatus.FINALIZED.name)
    Card {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Resumen mensual", style = MaterialTheme.typography.titleLarge)
            Text(
                text = label,
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold
            )
            HorizontalDivider()
            CartHeaderRow(
                firstLabel = "Cantidad de pedidos",
                secondLabel = pairAll.first.toString(),
                modifier = Modifier,
                resultFocus = true
            )
            CartHeaderRow(
                firstLabel = "Precio total",
                secondLabel = "ARS ${pairAll.second}",
                modifier = Modifier,
                resultFocus = true
            )
            CartHeaderRow(
                firstLabel = "Pedidos Pendientes",
                secondLabel = pairPending.first.toString(),
                modifier = Modifier,
                resultFocus = true
            )
            CartHeaderRow(
                firstLabel = "Pedidos Finalizados",
                secondLabel = pairFinalize.first.toString(),
                modifier = Modifier,
                resultFocus = true
            )
        }
    }
}

@Composable
fun ResumeActionButtons(
    modifier: Modifier = Modifier,
    navController: NavController,
    menuItems: List<MenuItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.Center, verticalArrangement = Arrangement.Center
    ) {
        items(menuItems) {
            MenuButton(
                modifier = modifier.padding(5.dp),
                name = it.name,
                imageVector = it.imageVector,
                onNavigate = { navController.navigate(it.onNavigatePath) },
                enable = it.enable
            )
        }

    }
}
