package com.example.shoplist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.theme.ComposeTheme

@Composable
fun ShopListScreen(
    items: List<ShopItem>,
    modifier: Modifier = Modifier,
    onItemClick: (ShopItem) -> Unit = {},
    onItemLongClick: (ShopItem) -> Unit = {},
    onItemDelete: (ShopItem) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.shop_list_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        if (items.isEmpty()) {
            Text(
                text = stringResource(R.string.shop_list_empty),
                modifier = Modifier.padding(16.dp)
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = items, key = { it.id }) { item ->
                val dismissState = rememberSwipeToDismissBoxState()
                SwipeToDismissBox(
                    state = dismissState,
                    modifier = Modifier.animateItem(),
                    onDismiss = { onItemDelete(item) },
                    backgroundContent = {
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .background(MaterialTheme.colorScheme.errorContainer, MaterialTheme.shapes.medium)
                                .padding(16.dp),
                            contentAlignment = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                                Alignment.CenterStart
                            } else {
                                Alignment.CenterEnd
                            }
                        ) {
                            Text(
                                stringResource(R.string.shop_item_delete),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                ) {
                    ShopItemCard(
                        item = item,
                        onClick = { onItemClick(item) },
                        onLongClick = { onItemLongClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShopItemCard(item: ShopItem, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().combinedClickable(
            onClick = onClick,
            onClickLabel = stringResource(R.string.shop_item_edit),
            onLongClick = onLongClick,
            onLongClickLabel = stringResource(R.string.shop_item_toggle)
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (item.enabled) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                textDecoration = if (item.enabled) TextDecoration.None else TextDecoration.LineThrough
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = stringResource(R.string.shop_item_count, item.count))
                Text(
                    text = stringResource(
                        if (item.enabled) R.string.shop_item_active else R.string.shop_item_inactive
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShopListPreview() {
    ComposeTheme {
        ShopListScreen(
            items = listOf(
                ShopItem(id = 0, name = "Молоко", count = 2, enabled = true),
                ShopItem(id = 1, name = "Хліб", count = 1, enabled = false)
            )
        )
    }
}
