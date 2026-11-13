package com.example.shoplist.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.shoplist.R

@Composable
fun ShopItemEditorDialog(
    state: ShopItemEditorState,
    onDismiss: () -> Unit,
    onSave: (String, Int) -> Unit
) {
    var name by rememberSaveable(state.item?.id) { mutableStateOf(state.item?.name.orEmpty()) }
    var count by rememberSaveable(state.item?.id) { mutableStateOf(state.item?.count?.toString() ?: "1") }
    var submitted by rememberSaveable(state.item?.id) { mutableStateOf(false) }
    val countValue = count.toIntOrNull()
    val nameInvalid = name.isBlank()
    val countInvalid = countValue == null || countValue <= 0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(if (state.item == null) R.string.shop_item_add else R.string.shop_item_edit))
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.shop_item_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !state.isSaving,
                    isError = submitted && nameInvalid,
                    supportingText = {
                        if (submitted && nameInvalid) Text(stringResource(R.string.shop_item_name_error))
                    }
                )
                OutlinedTextField(
                    value = count,
                    onValueChange = { count = it },
                    label = { Text(stringResource(R.string.shop_item_quantity)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !state.isSaving,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = submitted && countInvalid,
                    supportingText = {
                        if (submitted && countInvalid) Text(stringResource(R.string.shop_item_count_error))
                    }
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = !state.isSaving,
                onClick = {
                    submitted = true
                    if (!nameInvalid && countValue != null && countValue > 0) onSave(name.trim(), countValue)
                }
            ) {
                Text(stringResource(R.string.shop_item_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !state.isSaving) {
                Text(stringResource(R.string.shop_item_cancel))
            }
        }
    )
}
