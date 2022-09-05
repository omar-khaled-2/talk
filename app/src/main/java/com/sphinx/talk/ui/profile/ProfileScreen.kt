package com.sphinx.talk.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.ui.components.Avatar

@ExperimentalMaterialApi
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel()
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(src = profileViewModel.avatar,modifier = Modifier.size(50.dp),onClick = {})
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = profileViewModel.name)
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            Column {
                Text(text = "Followers")
                Text(text = 20.toString())
            }
            Divider()
            Column {
                Text(text = "Followers")
                Text(text = 20.toString())
            }
            Divider()
            Column {
                Text(text = "Followers")
                Text(text = 20.toString())
            }
        }
    }
}