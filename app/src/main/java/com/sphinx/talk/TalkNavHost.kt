package com.sphinx.talk

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.changeEmail.ChangeEmailScreen
import com.sphinx.talk.ui.changePassword.ChangePasswordScreen
import com.sphinx.talk.ui.createRoom.CreateRoomScreen
import com.sphinx.talk.ui.deleteAccount.DeleteAccountScreen
import com.sphinx.talk.ui.editProfile.EditProfileScreen
import com.sphinx.talk.ui.home.HomeScreen
import com.sphinx.talk.ui.profile.ProfileScreen
import com.sphinx.talk.ui.room.RoomScreen
import com.sphinx.talk.ui.settings.SettingsScreen
import com.sphinx.talk.ui.sign.SignScreen
import com.sphinx.talk.ui.signin.SignInScreen
import com.sphinx.talk.ui.signup.SignUpScreen


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TalkNavHost(
    user: User?,
    onUserChange:(User?) -> Unit,
    isDark:Boolean,
    onIsDarkChange:(Boolean) -> Unit,
    navController:NavHostController = rememberNavController()
){
    NavHost(navController = navController,startDestination = if(user === null) "sign" else "home"){
        if(user === null){
            composable("sign"){
                SignScreen(
                    onNavigateToSignUp = { navController.navigate("sign_up") },
                    onNavigateToSignIn = { navController.navigate("sign_in") },

                )
            }
            composable("sign_up"){
                SignUpScreen(
                    onSignUp = onUserChange,
                    onNavigateBack = {navController.navigateUp()},
                    onNavigateToSignIn = {navController.replace("sign_in")}
                )
            }
            composable("sign_in"){
                SignInScreen(
                    onNavigateBack = {navController.navigateUp()},
                    onNavigateToSignUp = {navController.replace("sign_up")},
                    onLogin = onUserChange
                )
            }

        }else{
            composable("home"){
                HomeScreen(
                    user,
                    onNavigateToCreateRoom = {navController.navigate("create_room")},
                    onNavigateToSettings = { navController.navigate("settings") },
                    onJoinRoom = {navController.navigate("room/$it")}
                )
            }


            composable("settings"){
                SettingsScreen(
                    user,
                    onNavigateToEditProfile = {navController.navigate("edit_profile")},
                    isDark = isDark,
                    onIsDarkChange = onIsDarkChange,
                    onLogout = {onUserChange(null)},
                    onNavigateToChangeEmail = {navController.navigate("change_email")},
                    onNavigateToChangePassword = {navController.navigate("change_password")},
                    onNavigateToDeleteAccount = {navController.navigate("delete_account")},
                    onNavigateBack = {navController.navigateUp()}
                )
            }


            composable("edit_profile"){
                EditProfileScreen(
                    onUserSaved = {
                        onUserChange(it)
                        navController.navigateUp()
                    },
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }


            composable("room/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){
                RoomScreen(
                    onNavigateBack = {navController.navigateUp()}
                )
            }
            composable("create_room"){
                CreateRoomScreen(
                    onNavigateBack = {navController.navigateUp()},
                    onDone = {navController.navigate("room/$it"){
                        this.popUpTo("home")
                    } },

                )
            }


            composable("profile/:id",arguments = listOf(navArgument("id"){
                this.type = NavType.IntType
            })){
                ProfileScreen()
            }

            composable("change_email"){
                ChangeEmailScreen(onNavigateBack = { navController.navigateUp() })
            }
            composable("change_password"){
                ChangePasswordScreen(onNavigateBack = { navController.navigateUp() })
            }

            composable("delete_account"){
                DeleteAccountScreen(onNavigateBack = { navController.navigateUp() }) {
                    onUserChange(null)
                }

            }
        }


    }

}