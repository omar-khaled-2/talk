package com.sphinx.talk.ui.signup

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.R
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.components.OutlinedPasswordField

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = viewModel(),
    onNavigateToSignIn:() -> Unit,
    onNavigateBack:() -> Unit,
    onSignUp:(user:User) -> Unit
){

    if(signUpViewModel.user !== null) onSignUp(signUpViewModel.user!!)

    val context = LocalContext.current

    if(signUpViewModel.toast.isNotEmpty()){
        Toast.makeText(context,signUpViewModel.toast,Toast.LENGTH_LONG).show()
        signUpViewModel.onToastEnd()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = "previous")
                    }
                },
                title = {}
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp,alignment = Alignment.CenterVertically),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Welcome",style = MaterialTheme.typography.h3,fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "I already have an account /")
                TextButton(onClick = onNavigateToSignIn) {
                    Text(text = stringResource(id = R.string.login),color = MaterialTheme.colors.primary)
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = signUpViewModel.firstName,
                    onValueChange = { signUpViewModel.onFirstNameChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text(text = stringResource(id = R.string.first_name))
                    },
                    modifier = Modifier.weight(1f)

                )
                OutlinedTextField(
                    value = signUpViewModel.lastName,
                    onValueChange = { signUpViewModel.onLastNameChange(it) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    label = {
                        Text(text = stringResource(id = R.string.last_name))
                    },
                    modifier = Modifier.weight(1f)

                )
            }

            OutlinedTextField(
                value = signUpViewModel.email,
                onValueChange = { signUpViewModel.onEmailChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                modifier = Modifier.fillMaxWidth()

            )
            OutlinedPasswordField(
                value = signUpViewModel.password,
                onValueChange = { signUpViewModel.onPasswordChange(it) },
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Button(onClick = { signUpViewModel.submit() },modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }

}