package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pasionariastore.R
import com.example.pasionariastore.components.MainTopBar
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import kotlinx.coroutines.launch

@Preview
@Composable
private fun MenuItemPreview() {
    PasionariaStoreTheme {
        MenuButton()
    }
}

@Preview
@Composable
private fun ResumeMonthlyPreview() {
    PasionariaStoreTheme {
        ResumeMonthly()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewResumeScreen() {
    PasionariaStoreTheme {
        ResumeScreen(
            modifier = Modifier,
            onCartButtonClicked = { },
            dataStore = CustomDataStore(LocalContext.current),

            )
    }
}

@Preview
@Composable
private fun ResumeActionButtonsPreview() {
    ResumeActionButtons()
}

@Composable
fun ResumeScreen(
    modifier: Modifier = Modifier,
    onCartButtonClicked: () -> Unit,
    dataStore: CustomDataStore,
) {
    Scaffold(topBar = {
        MainTopBar(title = "Pasionaria", onBackClicked = { /*TODO*/ }) {

        }
    }) {
        ResumeScreenBody(
            modifier = modifier.padding(it),
            dataStore = dataStore,
            onCartButtonClicked
        )
    }
}

@Composable
fun ResumeScreenBody(
    modifier: Modifier,
    dataStore: CustomDataStore,
    onCartListButtonClicked: () -> Unit
) {
    val darkMode = dataStore.getDarkMode.collectAsState(initial = false)

    val scope = rememberCoroutineScope();
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
        ResumeMonthly()
        Text(
            text = "Component: Ultimos pedidos realizados",
            modifier = modifier,
            textAlign = TextAlign.Center
        )
        ResumeActionButtons()
        Text(
            text = "Component: Acciones disponibles",
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Button(onClick = onCartListButtonClicked) {
                Text(text = "Historial de pedidos")
            }
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
fun MenuButton(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "",
                    modifier = Modifier.size(100.dp),
                )
                Text(text = "Pedidos", style = MaterialTheme.typography.headlineMedium)
            }

        }

    }
}

@Composable
fun ResumeMonthly() {
    Card {
        Column(
            modifier = Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Resumen mensual", style = MaterialTheme.typography.titleLarge)
            Row {
                Text(text = "Agosto")
                Spacer(modifier = Modifier.padding(15.dp))
                Text(text = "2024")
            }
            HorizontalDivider()
            CartHeaderRow(
                firstLabel = "Cantidad de pedidos",
                secondLabel = "15",
                modifier = Modifier,
                resultFocus = true
            )
            CartHeaderRow(
                firstLabel = "Precio total",
                secondLabel = "ARS 15,200.25",
                modifier = Modifier,
                resultFocus = true
            )
            CartHeaderRow(
                firstLabel = "Pedidos Pendientes",
                secondLabel = "3",
                modifier = Modifier,
                resultFocus = true
            )
            CartHeaderRow(
                firstLabel = "Pedidos Finalizados",
                secondLabel = "5",
                modifier = Modifier,
                resultFocus = true
            )
        }
    }
}

@Composable
fun ResumeActionButtons(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        horizontalArrangement = Arrangement.Center, verticalArrangement = Arrangement.Center
    ) {
        items(10) {
            MenuButton(modifier = modifier.padding(5.dp))
        }

    }
}
