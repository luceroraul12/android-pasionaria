package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pasionariastore.R
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PasionariaStoreTheme(darkTheme = false) {
        LoginScreen()
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Scaffold {
        LoginBody(modifier = modifier.fillMaxSize().padding(it))
    }
}

@Composable
fun LoginBody(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.screen_horizontal),
                vertical = dimensionResource(
                    id = R.dimen.screen_vertical
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.pasionaria_fixed),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth().padding(25.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )
        LoginField(value = "prueba", onChange = {}, label = "Usuario")
        LoginField(value = "prueba", onChange = {}, label = "Password")
        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded)),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }

}

@Composable
fun LoginField(value: String, onChange: (String) -> Unit, label: String) {
    TextField(
        value = value,
        onValueChange = onChange,
        label = { Text(text = label) },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded)),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)))
}
