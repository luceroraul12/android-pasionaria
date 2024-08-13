package com.example.pasionariastore.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
private fun SettingScreenItemSwitchPreview() {
    SettingItemSwitch(
        title = "Configuración",
        description = stringResource(id = R.string.lorem_min),
        true,
        onCheckedChange = {})
}

@Preview()
@Composable
private fun SettingScreenItemClickablePreview() {
    SettingItemClickable(
        title = "Actualizar pedidos",
        description = stringResource(id = R.string.lorem_min),
        onClick = {}
    )
}


@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            MainTopBar(
                title = stringResource(id = R.string.title_setting_screen),
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
        val state = settingViewModel.darkMode
        SettingItemSwitch(
            title = stringResource(R.string.dark_mode),
            description = stringResource(R.string.dark_mode_description),
            checked = state.value,
            onCheckedChange = settingViewModel::updateDarkMode,
        )
        SettingItemClickable(
            title = stringResource(R.string.sync_products),
            description = stringResource(R.string.sync_products_description)
        ) {

        }
    }
}

@Composable
fun SettingItemSwitch(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.default_value))
    ) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(end = dimensionResource(id = R.dimen.default_value))) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = modifier)
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Justify,
                modifier = modifier
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
    HorizontalDivider()
}

@Composable
fun SettingItemClickable(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    val modifier = Modifier.fillMaxWidth()
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(vertical = dimensionResource(id = R.dimen.default_value))
            .clickable { onClick() }
    ) {
        Text(
            text = title,
            modifier = modifier,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier
        )
    }
    HorizontalDivider()
}


