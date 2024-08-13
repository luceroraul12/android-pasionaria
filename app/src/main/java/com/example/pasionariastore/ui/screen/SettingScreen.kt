package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasionariastore.R
import com.example.pasionariastore.components.MainTopBar
import com.example.pasionariastore.ui.preview.SettingScreenViewModelFake
import com.example.pasionariastore.viewmodel.SettingViewModel

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        navController = rememberNavController(), settingViewModel = SettingScreenViewModelFake(
            LocalContext.current
        )
    )
}

@Preview()
@Composable
private fun SettingScreenItemPreview() {
    SettingItem(
        title = "Configuraci[on",
        description = "asdfljasdlfkjasldf  aldjflajd fl asdfljasldfjlasjdf alsdfj asdasda asdas asd",
        true,
        onCheckedChange = {})
}

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingViewModel: SettingViewModel
) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = "Configuración",
                onBackClicked = { navController.popBackStack() },
                showBackIcon = true
            ) {

            }
        }
    ) {
        SettingBody(modifier = modifier.padding(it), settingViewModel = settingViewModel)
    }
}


@Composable
fun SettingBody(modifier: Modifier, settingViewModel: SettingViewModel) {
    Column(modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_horizontal))) {
        SettingItem(
            title = "Modo oscuro",
            description = "Alternar entre modo claro y oscuro visualmente",
            checked = settingViewModel.darkMode.value,
            onCheckedChange = settingViewModel::updateDarkMode
        )
    }
}

@Composable
fun SettingItem(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.default_value))
    ) {
        Column {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }

}



