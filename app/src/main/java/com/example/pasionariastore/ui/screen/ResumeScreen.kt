package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme

@Preview(showBackground = true)
@Composable
fun PreviewResumeScreen() {
    PasionariaStoreTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ResumeScreen(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()))
        }
    }
}

@Composable
fun ResumeScreen(modifier: Modifier = Modifier) {
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
                modifier = modifier
                    .height(100.dp),
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
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Cargar pedido")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Historial de pedidos")
                }
            }
        }
    }


}