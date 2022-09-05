package com.sphinx.talk.ui.room

import android.Manifest
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sphinx.talk.R
import com.sphinx.talk.api.Message
import com.sphinx.talk.api.Room
import com.sphinx.talk.ui.components.Avatar
import kotlinx.coroutines.launch


fun drawArcPath(size: Size): Path {
    return Path().apply {
        reset()

        // go from (0,0) to (width, 0)
        lineTo(size.width, 0f)

        // go from (width, 0) to (width, height)
        lineTo(size.width, size.height)

        // Draw an arch from (width, height) to (0, height)
        // starting from 0 degree to 180 degree
        arcTo(
            rect =
            Rect(
                Offset(0f, 0f),
                Size(size.width, size.height)
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )

        // go from (0, height) to (0, 0)
        lineTo(0f, 0f)
        close()
    }
}



class BottomRoundedArcShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawArcPath(size = size)
        )
    }
}



@Composable
fun BackHandler(onBack: () -> Unit) {
    val currentOnBack by rememberUpdatedState(onBack)


    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current


    DisposableEffect(lifecycleOwner, backDispatcher) {

        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
        backDispatcher?.addCallback(lifecycleOwner,backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}



//class S :Shape{
//    override fun createOutline(
//        size: Size,
//        layoutDirection: LayoutDirection,
//        density: Density
//    ): Outline {
//        return Shape.createOutline(size,layoutDirection,density)
//    }
//}



@ExperimentalMaterialApi
@Composable
fun Message(
    message: Message,
){
    MaterialTheme.shapes.small
    Row() {
        Avatar(src = message.user.avatar,modifier = Modifier.size(40.dp),onClick = {})
        Spacer(modifier = Modifier.size(10.dp))
        Column {
            Text(text = message.user.name)
            Spacer(modifier = Modifier.size(5.dp))
            Surface(
                shape = RoundedCornerShape(0.dp,10.dp,10.dp,10.dp)
            ) {
                Text(text = message.text,modifier = Modifier.padding(15.dp))
            }
        }
    }

/*    ListItem(
        icon = {

        },
        text = {
            Text(text = message.user.name)
        },
        secondaryText = {
            Text(text = message.text)
        }
    )*/


}



@ExperimentalMaterialApi
@Composable
fun RoomDrawer(
    input:String,
    onInputChange:(String) -> Unit,
    onSendMessage:() -> Unit,
    messages:List<Message>
){

    Surface(
        color = MaterialTheme.colors.background
    ) {
        Column(
            Modifier
                .fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f),contentPadding = PaddingValues(10.dp),verticalArrangement = Arrangement.spacedBy(10.dp)){
                items(messages){
                    Message(message = it)
                }
            }
            TextField(
                value = input,
                onValueChange = onInputChange,
                trailingIcon = {
                    IconButton(onClick = onSendMessage,enabled = input.isNotBlank()) {
                        Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "send")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(id = R.string.send_message))
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )

            )
        }
    }


}

@Composable
fun RoomTopBar(
    room:Room,
    onNavigateBack:() -> Unit
) {
    TopAppBar(
        title = {
            Text(text = room.topic,textAlign = TextAlign.Center,modifier = Modifier.fillMaxWidth())
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Filled.ChevronLeft, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Report, contentDescription = "report")
            }
        }
    )
}


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun RoomScreen(
    roomViewModel: RoomViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {





    val context = LocalContext.current

    BackHandler {
        roomViewModel.onLeave()
    }

    if(roomViewModel.shouldLeave) onNavigateBack()




    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = {
        if(it) roomViewModel.startRecording()
    })

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()




    if(roomViewModel.isReloading) return LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

    if(roomViewModel.toast.isNotEmpty()){
        Toast.makeText(context, roomViewModel.toast, Toast.LENGTH_SHORT).show()
        roomViewModel.onToastDone()
    }


    if(roomViewModel.leavingAlert) AlertDialog(
        onDismissRequest = { roomViewModel.onDismissLeave() },
        title = {
            Text(text = "Leaving")
        },
        text = {
            Text(text = "Are you sure?")
        },
        dismissButton = {
            Button(onClick = { roomViewModel.onDismissLeave()  }) {
                Text(text = "Stay")
            }
        },
        confirmButton = {
            Button(onClick = { roomViewModel.onConfirmLeave() }) {
                Text(text = "Leave")
            }
        }
    )


    Scaffold(
        drawerContent = {
            RoomDrawer(
                input = roomViewModel.input,
                onInputChange = {roomViewModel.onInputChange(it)},
                onSendMessage = {roomViewModel.sendMessage()},
                messages = roomViewModel.messages
            )
        },
        topBar = {
            RoomTopBar(room = roomViewModel.room!!,onNavigateBack = { roomViewModel.onLeave() })
        },
        scaffoldState = scaffoldState,
        bottomBar = {
                Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){

                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.padding(bottom = 20.dp),

                        ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp),

                        ) {
                        IconButton(onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Chat, contentDescription = null)
                        }
                        IconButton(onClick = {if(roomViewModel.isMuted) launcher.launch(Manifest.permission.RECORD_AUDIO) else roomViewModel.stopRecording()}) {
                            Icon(imageVector = if(roomViewModel.isMuted) Icons.Filled.MicOff else Icons.Filled.Mic, contentDescription = null)
                        }
                        IconButton(onClick = {roomViewModel.onIsListeningChange(!roomViewModel.isListening)}){
                            Icon(imageVector = if(roomViewModel.isListening) Icons.Filled.VolumeUp else Icons.Filled.VolumeOff, contentDescription = null)
                        }
                    }
                }
            }
        },
    ){
            Column {

                LazyVerticalGrid(cells = GridCells.Fixed(3),modifier = Modifier.weight(1f),contentPadding = PaddingValues(bottom = 80.dp)){
                    items(roomViewModel.room!!.members){


                        Column(modifier = Modifier
                            .weight(1f)
                            .padding(20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                            Avatar(src = it.avatar,modifier = Modifier.fillParentMaxWidth(),onClick = {})
                            Spacer(modifier = Modifier.size(10.dp))
                            Text(text = it.name)
                        }
                    }
                }
        }



        }

}
