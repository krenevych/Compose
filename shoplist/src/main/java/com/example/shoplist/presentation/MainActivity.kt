package com.example.shoplist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.shoplist.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shoplist.presentation.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ShopListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val items by viewModel.shopList.collectAsStateWithLifecycle()
            val editor by viewModel.editor.collectAsStateWithLifecycle()
            ComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        ExtendedFloatingActionButton(onClick = viewModel::startAdding) {
                            Text(stringResource(R.string.shop_item_add))
                        }
                    }
                ) { innerPadding ->
                    ShopListScreen(
                        items = items,
                        modifier = Modifier.padding(innerPadding),
                        onItemClick = viewModel::startEditing,
                        onItemLongClick = viewModel::toggleItemStatus,
                        onItemDelete = viewModel::deleteItem
                    )
                }
                editor?.let { state ->
                    ShopItemEditorDialog(
                        state = state,
                        onDismiss = viewModel::dismissEditor,
                        onSave = viewModel::saveItem
                    )
                }
            }
        }
    }
}
