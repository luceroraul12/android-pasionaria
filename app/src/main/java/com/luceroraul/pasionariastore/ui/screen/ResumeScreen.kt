package com.luceroraul.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.luceroraul.pasionariastore.R
import com.luceroraul.pasionariastore.components.CustomScaffold
import com.luceroraul.pasionariastore.components.MainTopBar
import com.luceroraul.pasionariastore.model.CartWithData
import com.luceroraul.pasionariastore.model.Product
import com.luceroraul.pasionariastore.model.state.CartStatus
import com.luceroraul.pasionariastore.ui.preview.ResumeViewModelFake
import com.luceroraul.pasionariastore.ui.preview.SharedViewModelFake
import com.luceroraul.pasionariastore.ui.theme.PasionariaStoreTheme
import com.luceroraul.pasionariastore.viewmodel.ResumeViewModel
import com.luceroraul.pasionariastore.viewmodel.SharedViewModel

@Preview
@Composable
private fun ResumeMonthlyPreview() {
    PasionariaStoreTheme {
        ResumeMonthly(
            cartsWithData = emptyList(),
            calculate = { cartWithData: List<CartWithData>, optionalString: String? ->
                Pair(
                    2,
                    12324
                )
            })
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_XL)
@Composable
fun PreviewResumeScreen() {
    PasionariaStoreTheme {
        ResumeScreen(
            modifier = Modifier,
            navController = rememberNavController(),
            resumeViewModel = ResumeViewModelFake(),
            sharedViewModel = SharedViewModelFake(LocalContext.current)
        )
    }
}

@Composable
fun ResumeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    resumeViewModel: ResumeViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {

    CustomScaffold(
        navController = navController,
        sharedViewModel = sharedViewModel,
        content = {
            ResumeScreenBody(
                modifier = modifier.padding(it),
                resumeViewModel = resumeViewModel
            )
        },
        topBar = {
            MainTopBar(
                title = stringResource(id = R.string.title_resume_screen),
                onBackClicked = { /*TODO*/ }) {
            }
        },
        floatingActionButton = {

        }
    )
}

@Composable
fun ResumeScreenBody(
    modifier: Modifier,
    resumeViewModel: ResumeViewModel
) {
    val state by resumeViewModel.state.collectAsState()

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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.label, style = MaterialTheme.typography.displayMedium)
        ResumeMonthly(
            state.cartsWithData,
            calculate = resumeViewModel::calculatePairResume
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)))
        ResumeTopProducts(
            productNames = state.topProducts
        )
    }
}

@Composable
fun ResumeTopProducts(productNames: MutableList<Product>) {
    Card {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Productos más vendidos", style = MaterialTheme.typography.headlineSmall)
            HorizontalDivider()
            LazyColumn {
                itemsIndexed(productNames) { index, item ->
                    TopProductItem(item, index)
                }
            }
        }
    }

}

@Composable
fun TopProductItem(product: Product, index: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)).fillMaxWidth()
    ) {
        Text(text = "${index + 1}", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(
            dimensionResource(id = R.dimen.default_value)))
        Column {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.tertiary)
            Text(text = product.description, style = MaterialTheme.typography.labelMedium)
        }
    }

}

@Composable
fun ResumeMonthly(
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
            Text(text = "Pedidos por estado", style = MaterialTheme.typography.headlineSmall)
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(dimensionResource(id = R.dimen.default_value))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = status,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(
                    dimensionResource(id = R.dimen.default_value)
                )
            )
            Text(
                text = "(${count})",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(
                    dimensionResource(id = R.dimen.default_value)
                )
            )
        }
        Text(
            text = "ARS $price",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}