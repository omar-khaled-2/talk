package com.sphinx.talk.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> Select(
    value:String,
    data:List<T>,
    onValueChange:(T) -> Unit,
    modifier:Modifier = Modifier,
    label: @Composable () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    renderItem:@Composable (T) -> Unit,
){
    var showMenu by remember {
        mutableStateOf(false)
    }
    Column(){
        OutlinedTextField(value = value, onValueChange = {},modifier = modifier,trailingIcon = {
            IconButton(onClick = { showMenu = true }) {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        },label = label,leadingIcon = leadingIcon,readOnly = true)
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            for (item in data){
                DropdownMenuItem(onClick = { onValueChange(item);showMenu = false }) {
                    renderItem(item)
                }
            }
        }
    }
}