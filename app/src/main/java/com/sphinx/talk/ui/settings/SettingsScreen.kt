package com.sphinx.talk.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.components.Avatar

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(
    user: User,
    onLogout:() -> Unit,
    onNavigateBack:() -> Unit,
    onNavigateToEditProfile:() -> Unit,
    onNavigateToChangeEmail:() -> Unit,
    onNavigateToChangePassword:() -> Unit,
    onNavigateToDeleteAccount:() -> Unit,
    onIsDarkChange:(isDark:Boolean) -> Unit,
    isDark:Boolean,
    settingsViewModel: SettingsViewModel = viewModel()
){
    if(settingsViewModel.isLoggedOut) onLogout()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToEditProfile) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Avatar(src = user.avatar,modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = user.name,style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.size(30.dp))
            Column{
                ListItem(
                    icon = {
                        Icon(imageVector = Icons.Filled.DarkMode, contentDescription = "dark mode")
                    },
                    text = {
                        Text(text = "Dark mode")
                    },
                    trailing = {
                        Switch(checked = isDark, onCheckedChange = onIsDarkChange)
                    }
                )
                ListItem(
                    icon = {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = null)
                    },
                    text = {
                        Text(text = "Change email")
                    },
                    trailing = {
                        Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = null)
                    },
                    modifier = Modifier.clickable { onNavigateToChangeEmail() }
                )
                ListItem(
                    icon = {
                        Icon(imageVector = Icons.Filled.Password, contentDescription = null)
                    },
                    text = {
                        Text(text = "Change password")
                    },
                    trailing = {
                        Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = null)
                    },
                    modifier = Modifier.clickable { onNavigateToChangePassword() }
                )
                Surface(
                    contentColor = MaterialTheme.colors.error,
                    color = Color.Transparent
                ) {
                    ListItem(
                        icon = {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
                        },
                        text = {
                            Text(text = "Delete account")
                        },
                        trailing = {
                            Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = null)
                        },
                        modifier = Modifier.clickable { onNavigateToDeleteAccount() },

                    )
                }
                ListItem(
                    icon = {
                        Icon(imageVector = Icons.Filled.Logout, contentDescription = null)
                    },
                    text = {
                        Text(text = "Logout")
                    },
                    modifier = Modifier.clickable { settingsViewModel.logout() }

                )

            }
        }
    }

}