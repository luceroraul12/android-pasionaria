package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pasionariastore.R
import com.example.pasionariastore.components.CustomScaffold
import com.example.pasionariastore.components.MainTopBar
import com.example.pasionariastore.model.CartWithData
import com.example.pasionariastore.model.MenuItem
import com.example.pasionariastore.model.state.CartStatus
import com.example.pasionariastore.ui.preview.ResumeViewModelFake
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import com.example.pasionariastore.viewmodel.ResumeViewModel

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

@Preview(showBackground = true, device = Devices.PIXEL_XL)
@Composable
fun PreviewResumeScreen() {
    PasionariaStoreTheme {
        ResumeScreen(
            modifier = Modifier,
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
            ),
        )
    )
}

@Composable
fun ResumeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    resumeViewModel: ResumeViewModel = hiltViewModel()
) {

    CustomScaffold(
        navController = navController,
        showBackIcon = false,
        actions = {},
        topBar = {
            MainTopBar(
                title = stringResource(id = R.string.title_resume_screen),
                onBackClicked = { /*TODO*/ }) {
            }
        },
        content = {
            ResumeScreenBody(
                modifier = modifier.padding(it),
                navController = navController,
                resumeViewModel = resumeViewModel
            )
        },
    )
}

@Composable
fun ResumeScreenBody(
    modifier: Modifier,
    navController: NavHostController,
    resumeViewModel: ResumeViewModel
) {
    val state by resumeViewModel.state.collectAsState()
    val menuItems = resumeViewModel.menuItems

    LaunchedEffect(Unit) {
        resumeViewModel.initScreen()
    }

    Column(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.screen_horizontal),
                vertical = dimensionResource(id = R.dimen.screen_vertical),
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
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
    val pairSynchronized: Pair<Int, Int> = calculate(cartsWithData, CartStatus.SYNCHRONIZED.name)
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
            ResumeCartStatusItem(
                status = "Pendiente",
                count = pairPending.first,
                price = pairPending.second
            )
            ResumeCartStatusItem(
                status = "Finalizado",
                count = pairFinalize.first,
                price = pairFinalize.second
            )
            ResumeCartStatusItem(
                status = "Sincronizado",
                count = pairSynchronized.first,
                price = pairSynchronized.second
            )
            ResumeCartStatusItem(status = "Total", count = pairAll.first, price = pairAll.second)
        }
    }
}

@Preview
@Composable
private fun ResumeCartStatusItemPreview() {
    ResumeCartStatusItem(status = "FINLIZADO", count = 2, price = 2324)
}

@Composable
fun ResumeCartStatusItem(modifier: Modifier = Modifier, status: String, count: Int, price: Int) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)))
        Text(text = "$status: $count", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "ARS $price",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
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
