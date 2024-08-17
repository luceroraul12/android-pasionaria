package com.luceroraul.pasionariastore.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.luceroraul.pasionariastore.R
import com.luceroraul.pasionariastore.components.MainTopBar
import com.luceroraul.pasionariastore.ui.preview.BackendRepositoryFake
import com.luceroraul.pasionariastore.ui.preview.CartRepositoryFake
import com.luceroraul.pasionariastore.ui.preview.ProductRepositoryFake
import com.luceroraul.pasionariastore.ui.preview.SettingScreenViewModelFake
import com.luceroraul.pasionariastore.ui.preview.SharedViewModelFake
import com.luceroraul.pasionariastore.usecase.ProductSynchronizer
import com.luceroraul.pasionariastore.viewmodel.CheckDatabaseViewModel
import com.luceroraul.pasionariastore.viewmodel.SettingViewModel
import com.luceroraul.pasionariastore.viewmodel.SharedViewModel


@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        navController = rememberNavController(),
        settingViewModel = SettingScreenViewModelFake(
            LocalContext.current
        ),
        checkDatabaseViewModel = CheckDatabaseViewModel(
            BackendRepositoryFake(),
            ProductSynchronizer(
                backendRepository = BackendRepositoryFake(),
                productRepository = ProductRepositoryFake(),
                cartRepository = CartRepositoryFake()
            )
        ),
        sharedViewModel = SharedViewModelFake(LocalContext.current)
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
    settingViewModel: SettingViewModel = hiltViewModel(),
    checkDatabaseViewModel: CheckDatabaseViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel()
) {
    val isLoading by sharedViewModel.isLoading.collectAsState()
    if (isLoading) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else {
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
            SettingBody(
                modifier = modifier.padding(it),
                settingViewModel = settingViewModel,
                checkDatabaseViewModel = checkDatabaseViewModel,
                onChangeLoading = sharedViewModel::updateIsLoading
            )
        }
    }
}


@Composable
fun SettingBody(
    modifier: Modifier,
    settingViewModel: SettingViewModel,
    checkDatabaseViewModel: CheckDatabaseViewModel,
    onChangeLoading: (Boolean) -> Unit,
) {
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
            description = stringResource(R.string.sync_products_description),
            onClick = {
                checkDatabaseViewModel.syncProductsWithLoading(onChangeLoading = { onChangeLoading(it) })
            }
        )
        // TODO. Comento la funcionalidad hasta que pueda incluirla
//        SettingItemClickable(
//            title = stringResource(R.string.sync_carts),
//            description = stringResource(R.string.sync_carts_description),
//            onClick = {}
//        )
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
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = dimensionResource(id = R.dimen.default_value))
        ) {
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


