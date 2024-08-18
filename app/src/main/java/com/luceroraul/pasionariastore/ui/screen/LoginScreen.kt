package com.luceroraul.pasionariastore.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.luceroraul.pasionariastore.R
import com.luceroraul.pasionariastore.ui.preview.LoginViewModelFake
import com.luceroraul.pasionariastore.ui.preview.SharedViewModelFake
import com.luceroraul.pasionariastore.ui.theme.PasionariaStoreTheme
import com.luceroraul.pasionariastore.viewmodel.LoginViewModel
import com.luceroraul.pasionariastore.viewmodel.SharedViewModel

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PasionariaStoreTheme(darkTheme = false) {
        LoginScreen(
            navController = rememberNavController(),
            loginViewModel = LoginViewModelFake(
                context = LocalContext.current
            ),
            sharedViewModel = SharedViewModelFake(LocalContext.current)
        )
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = Unit) {
        sharedViewModel.initSubscriptionScreens(
            coroutineScope = this,
            navController = navController
        )
    }
    Scaffold {
        LoginBody(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            loginViewModel = loginViewModel,
            sharedViewModel = sharedViewModel
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel,
    sharedViewModel: SharedViewModel,
) {
    val state by loginViewModel.state.collectAsState()
    val context = LocalContext.current
    DisposableEffect(Unit) {
        onDispose(loginViewModel::cleanState)
    }
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
            onChange = { loginViewModel.updateUsername(it) },
            label = "Usuario",
            keyboardType = KeyboardType.Text,
            imageId = R.drawable.username,
            imeAction = ImeAction.Next,
        )
        LoginField(
            value = state.password,
            onChange = { loginViewModel.updatePassword(it) },
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation(),
            imageId = R.drawable.password,
            imeAction = ImeAction.Done,
        )
        Spacer(modifier = modifier.weight(1f))
        Button(
            onClick = {
                loginViewModel.login(context = context, sharedViewModel.navigationFlow)
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
    imeAction: ImeAction,
) {
    TextField(
        value = value,
        onValueChange = onChange,
        label = { Text(text = label) },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded)),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = visualTransformation,
        leadingIcon = { Icon(painter = painterResource(id = imageId), contentDescription = "") }
    )
    Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.default_value)))
}
