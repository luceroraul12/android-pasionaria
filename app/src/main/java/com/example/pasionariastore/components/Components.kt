package com.example.pasionariastore.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.pasionariastore.R
import com.example.pasionariastore.ui.theme.PasionariaStoreTheme

@Preview
@Composable
private fun MainTopBarFinalizeOnPreview() {
    PasionariaStoreTheme {
        MainTopBar(title = "Homitowen (っ◔◡◔)っ",
            onBackClicked = {},
            showBackIcon = true,
            actions = {
                FinalizeButton(onFinalize = {}, canFinalize = true)
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
                FinalizeButton(onFinalize = {})
            })
    }
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
        title = { Text(text = title, fontWeight = FontWeight.Bold, color = Color.White) },
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
fun FinalizeButton(onFinalize: () -> Unit, canFinalize: Boolean = false) {
    IconButton(
        onClick = onFinalize,
        enabled = canFinalize,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colorResource(id = R.color.finalized_active),
            disabledContainerColor = colorResource(
                id = R.color.finalized_inactive
            ),
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.cart_finalize),
            contentDescription = "cartFinalize",
            tint = Color.White
        )
    }
}