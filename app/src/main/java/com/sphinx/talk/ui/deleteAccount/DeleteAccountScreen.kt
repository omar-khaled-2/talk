package com.sphinx.talk.ui.deleteAccount

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.R
import com.sphinx.talk.ui.components.OutlinedPasswordField


@Composable
fun DeleteAccountScreen(
    onNavigateBack: () -> Unit,
    deleteAccountViewModel: DeleteAccountViewModel = viewModel(),
    onDelete: () -> Unit
){
    val context = LocalContext.current

    
    
    if(deleteAccountViewModel.isDeleted) onDelete()
    
    
    if(deleteAccountViewModel.alert) AlertDialog(
        onDismissRequest = { deleteAccountViewModel.dismissAlert() },
        confirmButton = {
            Button(onClick = { deleteAccountViewModel.deleteAccount() }) {
                Text(text = "Delete")
            }
        },
        title = {
            Text(text = "Delete account")
        },
        text = {
            Text(text = "Are you sure ?")
        }

    )

    if(deleteAccountViewModel.toast.isNotEmpty()){
        Toast.makeText(context,deleteAccountViewModel.toast,Toast.LENGTH_LONG).show()
        deleteAccountViewModel.onToastDone()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Delete account")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp,alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedPasswordField(
                value = deleteAccountViewModel.password,
                onValueChange = { deleteAccountViewModel.onPasswordChange(it) },
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.error
                ),
                onClick = { deleteAccountViewModel.deleteAccount() },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
            ) {
                Text(text = "Delete")
            }

        }
    }
}