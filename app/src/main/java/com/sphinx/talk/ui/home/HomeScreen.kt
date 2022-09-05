package com.sphinx.talk.ui.home

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.sphinx.talk.api.Room
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.components.Avatar
import com.sphinx.talk.ui.components.Select


@Composable
fun FilterDialog(
    initialFilter:Filter,
    onSearch:(Filter) -> Unit,
    levels:List<String> = com.sphinx.talk.levels,
    languages:List<String>,
    onDismissRequest:() -> Unit
){

    var filter by remember {
        mutableStateOf(initialFilter)
    }


    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OutlinedTextField(
                    value = filter.topic,
                    onValueChange = {
                        filter = filter.copy(
                            topic = it
                        )
                    }
                    ,label = {
                        Text(text = "Topic")
                    }
                )
                Select(
                    value = filter.language ?: "",
                    data = languages,
                    onValueChange = {
                        filter = filter.copy(language = it)
                    },
                    label = {
                        Text(text = "Language")
                    },
                    renderItem = {
                        Text(text = it)
                    }
                )
                Select(
                    value = filter.level ?: "",
                    data = levels,
                    onValueChange = {
                        filter = filter.copy(level = it)
                    }, label = {
                        Text(text = "Level")
                    },
                    renderItem = {
                        Text(text = it)
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(onClick = onDismissRequest,modifier = Modifier
                        .height(50.dp)
                        .weight(1f)) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = { onSearch(filter) },modifier = Modifier
                        .height(50.dp)
                        .weight(1f)
                    ) {
                        Text(text = "Search")
                    }
                }
            }

        }
    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    user: User,
    onNavigateToCreateRoom:() -> Unit,
    onNavigateToSettings:() -> Unit,
    onJoinRoom:(Int) -> Unit,
    homeViewModel: HomeViewModel = viewModel()
){

    val swipeRefreshState = rememberSwipeRefreshState(homeViewModel.isReloading)
    var joiningAlert by remember {
        mutableStateOf<Room?>(null)
    }


    var showFilterDialog by remember {
        mutableStateOf(false)
    }


    val context = LocalContext.current



    if(homeViewModel.toast.isNotEmpty()){
        Toast.makeText(context,homeViewModel.toast,Toast.LENGTH_LONG).show()
        homeViewModel.onToastDone()
    }

    if(joiningAlert !== null) AlertDialog(onDismissRequest = { joiningAlert = null },confirmButton = {
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "yes")
        }
    })

    if(showFilterDialog) FilterDialog(
        initialFilter = homeViewModel.filter,
        languages = emptyList(),
        onSearch = {
            showFilterDialog = false
            homeViewModel.onFilterChange(it)
        },
        onDismissRequest = {showFilterDialog = false}
    )
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                       Text(text = "Create Room")
                },
                icon = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "create_room")
                },
                onClick = onNavigateToCreateRoom
            )
        }

    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { homeViewModel.refresh() }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 80.dp,top = 10.dp),
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Avatar(src = user.avatar,modifier = Modifier.size(40.dp),onClick = onNavigateToSettings)

                        Column(
                            modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                        ) {
                            Text(text = "Welcome back",style = MaterialTheme.typography.subtitle1)
                            Text(text = user.firstName)
                        }

                        IconButton(onClick = { showFilterDialog = true }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                        }
                    }
                }
                items(homeViewModel.rooms,{it.id}){
                    RoomCard(
                        room = it,
                        onJoin = {onJoinRoom(it.id)},
                        onReport = {homeViewModel.report(it.id)},
                        showMenu = homeViewModel.showMenu === it,
                        onDismissMenu = {homeViewModel.dismissMenu()},
                        onShowMenu = {homeViewModel.showMenu(it)}
                    )

                }
            }
        }

    }
}