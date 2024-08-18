package com.luceroraul.pasionariastore.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.luceroraul.pasionariastore.R
import com.luceroraul.pasionariastore.model.MenuItem
import com.luceroraul.pasionariastore.ui.preview.SharedViewModelFake
import com.luceroraul.pasionariastore.ui.theme.PasionariaStoreTheme
import com.luceroraul.pasionariastore.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
private fun MainTopBarFinalizeOnPreview() {
    PasionariaStoreTheme {
        MainTopBar(title = "Homitowen (っ◔◡◔)っ",
            onBackClicked = {},
            showBackIcon = true,
            actions = {
                CustomIconButton(onClick = {}, enabled = true, iconId = R.drawable.cart_finalize)
            })
    }
}

@Preview
@Composable
private fun MainTopBarFinalizeOffPreview() {
    PasionariaStoreTheme {
        MainTopBar(title = "Hamilton ( ͡❛ ͜ʖ ͡❛)",
            onBackClicked = {},
            showBackIcon = true,
            actions = {
                CustomIconButton(onClick = {}, iconId = R.drawable.cart_finalize)
            })
    }
}

@Preview
@Composable
private fun CustomScaffoldPreview() {
    CustomScaffold(
        navController = rememberNavController(),
        content = { Text(text = "", modifier = Modifier.padding(it)) },
        sharedViewModel = SharedViewModelFake(LocalContext.current),
        topBar = {},
        floatingActionButton = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    onBackClicked: () -> Unit,
    showBackIcon: Boolean = false,
    actions: @Composable() (RowScope.() -> Unit)
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.headlineMedium,color = Color.White) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            if (showBackIcon) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable { onBackClicked() })
            }
        },
        actions = actions
    )
}

@Composable
fun CustomIconButton(
    onClick: () -> Unit,
    enabled: Boolean = false,
    @DrawableRes iconId: Int,
    @ColorRes containerColorEnableId: Int = R.color.finalized_active,
    @ColorRes containerColorDisableId: Int = R.color.finalized_inactive
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(id = containerColorEnableId),
            disabledContainerColor = colorResource(id = containerColorDisableId),
        )
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "cartFinalize",
            tint = Color.White
        )
    }
}

@Composable
fun CustomScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit = {},
    sharedViewModel: SharedViewModel = hiltViewModel(),
    topBar: @Composable () -> Unit,
    floatingActionButton: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.navigator_drawer))) {
                    Text(text = "Modulos", style = MaterialTheme.typography.titleLarge)
                    HorizontalDivider()
                    LazyColumn {
                        items(sharedViewModel.menuItems) {
                            if (it.enable)
                                NavigatorMenuItem(
                                    menuItem = it,
                                    navController = navController,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                    }
                                )
                        }
                    }
                }
            }
        },
    ) {
        Scaffold(
            topBar = topBar,
            floatingActionButton = floatingActionButton,
        ) {
            content(it)
        }
    }
}

@Preview
@Composable
private fun NavigatorMenuItemScreen() {
    NavigatorMenuItem(
        MenuItem(
            name = "Accion",
            enable = true,
            imageVector = Icons.Default.Close,
            onNavigatePath = "sd"
        ),
        navController = rememberNavController(),
        onClick = {}
    )
}

@Composable
fun NavigatorMenuItem(menuItem: MenuItem, navController: NavController, onClick: () -> Unit) {
    val modifier = Modifier
        .padding(dimensionResource(id = R.dimen.default_value))
        .clickable {
            navController.navigate(menuItem.onNavigatePath)
            onClick()
        }
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(imageVector = menuItem.imageVector, contentDescription = "")
        Text(
            text = menuItem.name,
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.weight(1f)
        )
    }
}
