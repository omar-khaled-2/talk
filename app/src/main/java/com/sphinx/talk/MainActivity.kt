package com.sphinx.talk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.sign.SignScreen
import com.sphinx.talk.ui.signin.SignInScreen
import com.sphinx.talk.ui.signup.SignUpScreen
import com.sphinx.talk.ui.theme.TalkTheme

class MainActivity : ComponentActivity() {
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TalkTheme(darkTheme = mainActivityViewModel.isDark){
                Surface(color = MaterialTheme.colors.background) {
                    App(
                        user = mainActivityViewModel.user,
                        onUserChange = { mainActivityViewModel.onUserChange(it) },
                        isDark = mainActivityViewModel.isDark,
                        onIsDarkChange = {mainActivityViewModel.onIsDarkChange(it)}
                    )
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun App(
    user: User?,
    onUserChange:(User?) -> Unit,
    isDark:Boolean,
    onIsDarkChange:(Boolean) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    val statusColor = MaterialTheme.colors.primaryVariant

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusColor
        )

    }

    TalkNavHost(
        user,
        onUserChange = onUserChange,
        isDark = isDark,
        onIsDarkChange = onIsDarkChange
    )
}

