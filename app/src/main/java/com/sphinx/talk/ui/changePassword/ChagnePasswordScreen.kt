package com.sphinx.talk.ui.changePassword

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.ui.components.OutlinedPasswordField

@Composable
fun ChangePasswordScreen(
    onNavigateBack:() -> Unit,
    changePasswordViewModel: ChangePasswordViewModel = viewModel()
){
    if(changePasswordViewModel.isSaved) onNavigateBack()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = null)
                    }
                },
                title = {
                    Text(text = "Change Password")
                },
                actions = {
                    IconButton(onClick = { changePasswordViewModel.save() }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "save")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp,alignment = Alignment.CenterVertically)
        ) {
            OutlinedPasswordField(
                value = changePasswordViewModel.currentPassword,
                onValueChange = {changePasswordViewModel.onCurrentPasswordChange(it)},
                label = {
                    Text("Current password")
                },
                modifier = Modifier.fillMaxWidth()

            )
            OutlinedPasswordField(
                value = changePasswordViewModel.newPassword,
                onValueChange = {changePasswordViewModel.onNewPasswordChange(it)},
                label = {
                    Text("New password")
                },
                modifier = Modifier.fillMaxWidth()

            )
            OutlinedPasswordField(
                value = changePasswordViewModel.confirmNewPassword,
                onValueChange = {changePasswordViewModel.onConfirmNewPasswordChange(it)},
                label = {
                    Text("Confirm new password")
                },
                modifier = Modifier.fillMaxWidth()

            )
        }
    }
}