package com.sphinx.talk.ui.signin

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.R
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.components.OutlinedPasswordField

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = viewModel(),
    onNavigateBack:() -> Unit,
    onNavigateToSignUp:() -> Unit,
    onLogin:(User) -> Unit
){

    if(signInViewModel.user !== null) onLogin(signInViewModel.user!!)

    val context = LocalContext.current

    if(signInViewModel.toast.isNotEmpty()){
        Toast.makeText(context,signInViewModel.toast, Toast.LENGTH_LONG).show()
        signInViewModel.onToastEnd()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = null)
                    }
                },
                title = {}
            )
        }
    ) {
        
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp,alignment = Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Text(text = "Welcome back",style = MaterialTheme.typography.h3,fontWeight = FontWeight.Bold)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have account /")
            TextButton(onClick = onNavigateToSignUp) {
                Text(text = stringResource(id = R.string.sign_up),color = MaterialTheme.colors.primary)
            }
        }
        OutlinedTextField(
            value = signInViewModel.email,
            onValueChange = { signInViewModel.onEmailChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            modifier = Modifier.fillMaxWidth()

        )
        OutlinedPasswordField(
            value = signInViewModel.password,
            onValueChange = { signInViewModel.onPasswordChange(it) },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(onClick = { signInViewModel.submit() },modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}