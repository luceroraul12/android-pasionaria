package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pasionariastore.data.CustomDataStore
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewResumeScreen() {
    PasionariaStoreTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ResumeScreen(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                onCartButtonClicked = { },
                onCartListButtonClicked = {},
                dataStore = CustomDataStore(LocalContext.current),
            )
        }
    }
}

@Composable
fun ResumeScreen(
    modifier: Modifier = Modifier,
    onCartButtonClicked: () -> Unit,
    dataStore: CustomDataStore,
    onCartListButtonClicked: () -> Unit,
) {
    val darkMode = dataStore.getDarkMode.collectAsState(initial = false)
    val scope = rememberCoroutineScope();
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Resumen de tienda",
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Component: Ultimos pedidos realizados",
                modifier = modifier,
                textAlign = TextAlign.Center
            )
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
                Button(onClick = onCartButtonClicked) {
                    Text(text = "Cargar pedido")
                }
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


}