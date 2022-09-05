package com.sphinx.talk.ui.sign

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sphinx.talk.R


@Composable
fun SignScreen(
    onNavigateToSignUp:() -> Unit,
    onNavigateToSignIn:() -> Unit
){
    Surface(
        color = MaterialTheme.colors.primary
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.app_name),fontSize = 30.sp,fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.improve_your_language),fontSize = 20.sp)
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp,alignment = Alignment.CenterVertically)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    ),onClick = onNavigateToSignUp,modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)) {
                    Text(text = stringResource(id = R.string.create_new_account))
                }
                Button(colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary
                ),onClick = onNavigateToSignIn,modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)) {
                    Text(text = stringResource(id = R.string.login_with_email))
                }

                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly) {
                    IconButton(onClick = { /*TODO*/ }) {

                    }
                    IconButton(onClick = { /*TODO*/ }) {

                    }
                }
            }
        }
    }

}