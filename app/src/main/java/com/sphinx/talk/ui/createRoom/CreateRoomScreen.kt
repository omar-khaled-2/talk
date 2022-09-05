package com.sphinx.talk.ui.createRoom

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.R
import com.sphinx.talk.api.Room
import com.sphinx.talk.ui.components.Select


@Composable
fun CreateRoomScreen(
    createRoomViewModel:CreateRoomViewModel = viewModel(),
    onNavigateBack:() -> Unit,
    onDone:(id:Int) -> Unit
){

    if(createRoomViewModel.isCreated !== null){
        onDone(createRoomViewModel.isCreated!!)
        createRoomViewModel.onCreateAlertDone()
    }
    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(text = stringResource(id = R.string.create_room))
                },
                actions = {
                    IconButton(onClick = { createRoomViewModel.submit() },enabled = createRoomViewModel.canSubmit) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "save")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp,Alignment.CenterVertically),
            modifier = Modifier.padding(horizontal = 20.dp).fillMaxSize()
        ) {
            OutlinedTextField(
                value = createRoomViewModel.topic,
                onValueChange = {
                    createRoomViewModel.onTopicChange(it)
                },
                label = {
                    Text(text = stringResource(id = R.string.topic))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Select(
                value = createRoomViewModel.language,
                data = createRoomViewModel.languages,
                onValueChange = {createRoomViewModel.onLanguageChange(it.name)},
                renderItem = {
                     Text(text = "${it.name} ${it.rooms}")
                },
                label = {
                    Text(text = stringResource(id = R.string.language))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Select(
                value = createRoomViewModel.limit.toString(),
                data =  (1..8).map { it },
                onValueChange = {createRoomViewModel.onLimitChange(it)},
                renderItem = {
                    Text(text = it.toString())
                },
                label = {
                    Text(text = stringResource(id = R.string.limit))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Select(
                value = createRoomViewModel.level,
                data = createRoomViewModel.levels,
                onValueChange = {createRoomViewModel.onLevelChange(it)},
                renderItem = {
                    Text(text = it.toString())
                },
                label = {
                    Text(text = stringResource(id = R.string.level))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}