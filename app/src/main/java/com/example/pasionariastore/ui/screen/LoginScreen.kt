package com.example.pasionariastore.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasionariastore.R
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme
import com.example.pasionariastore.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PasionariaStoreTheme(darkTheme = false) {
        LoginScreen(navController = rememberNavController())
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val navigateFlow = viewModel.navigationFlow.asSharedFlow()
    val errorFlow = viewModel.loginErrorFlow.asSharedFlow()
    LaunchedEffect(key1 = Unit) {
        launch {
        navigateFlow.collect {
            navController.navigate(it)
        }
        }
        launch {
            errorFlow.collect {
                viewModel.resolveException(
                    code = it.first,
                    message = it.second,
                    currentDestination = navController.currentDestination,
                )
            }
        }
    }
    Scaffold {
        LoginBody(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            viewModel = viewModel,
            navController = navController
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.5f
        )
        LoginField(
            value = state.username,
            onChange = { viewModel.updateUsername(it) },
            label = "Usuario",
            keyboardType = KeyboardType.Text,
            imageId = R.drawable.username
        )
        LoginField(
            value = state.password,
            onChange = { viewModel.updatePassword(it) },
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            imageId = R.drawable.password
        )
        Button(
            onClick = {
                viewModel.login(context = context)
            },
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded)),
            modifier = Modifier
                .fillMaxWidth(),
            enabled = state.enableLoginButton
        ) {
            Text(text = "Ingresar")
        }
    }

}

@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    @DrawableRes imageId: Int,
) {
    TextField(
        value = value,
        onValueChange = onChange,
        label = { Text(text = label) },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded)),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        leadingIcon = { Icon(painter = painterResource(id = imageId), contentDescription = "") }
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)))
}
