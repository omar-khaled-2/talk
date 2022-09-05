package com.sphinx.talk.ui.changeEmail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.ui.components.OutlinedPasswordField

@Composable
fun ChangeEmailScreen(
    onNavigateBack:() -> Unit,
    changeEmailViewModel: ChangeEmailViewModel = viewModel()
){
    if(changeEmailViewModel.isSaved) onNavigateBack()

    val context = LocalContext.current
    if(changeEmailViewModel.toast.isNotEmpty()){
        Toast.makeText(context,changeEmailViewModel.toast,Toast.LENGTH_LONG).show()
        changeEmailViewModel.onToastDone()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = null)
                    }
                },
                title = {
                    Text(text = "Change email")
                },
                actions = {
                    IconButton(onClick = { changeEmailViewModel.save() }) {
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
            OutlinedTextField(
                label={
                      Text(text = "Email")
                },
                value = changeEmailViewModel.email,
                onValueChange = {changeEmailViewModel.onEmailChange(it)},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedPasswordField(
                label={
                    Text(text = "Password")
                },
                value = changeEmailViewModel.password,
                onValueChange = {changeEmailViewModel.onPasswordChange(it)},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}