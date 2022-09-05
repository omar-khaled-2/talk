package com.sphinx.talk.ui.editProfile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.R
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.components.Avatar

@ExperimentalMaterialApi
@Composable
fun EditProfileScreen(
    onUserSaved:(User) -> Unit,
    onNavigateBack:() -> Unit,
    editProfileViewModel: EditProfileViewModel = viewModel()

){


    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent(), onResult = {
        editProfileViewModel.onAvatarChange(it)

    })

    val context = LocalContext.current

    if(editProfileViewModel.isSaved !== null) onUserSaved(editProfileViewModel.isSaved!!)


    if(editProfileViewModel.toast.isNotEmpty()){
        Toast.makeText(context,editProfileViewModel.toast,Toast.LENGTH_LONG).show()
        editProfileViewModel.onToastDone()
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
                    Text(text = "Edit profile")
                },
                actions = {

                    if(editProfileViewModel.isReloading) CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                    else IconButton(onClick = { editProfileViewModel.save() }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "save")
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp,alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Avatar(src = editProfileViewModel.avatar,baseUrl = "",modifier = Modifier.size(100.dp),onClick = { launcher.launch("image/*") })
            OutlinedTextField(value = editProfileViewModel.firstName, onValueChange = {editProfileViewModel.onFirstNameChange(it)},modifier = Modifier.fillMaxWidth(),label = {Text(stringResource(id = R.string.first_name))})
            OutlinedTextField(value = editProfileViewModel.lastName, onValueChange = {editProfileViewModel.onLastNameChange(it)},modifier = Modifier.fillMaxWidth(),label = {Text(
                stringResource(id = R.string.last_name))})

        }
    }

}