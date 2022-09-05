package com.sphinx.talk.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.placeholder
import com.sphinx.talk.R
import com.sphinx.talk.api.Room
import com.sphinx.talk.api.User
import com.sphinx.talk.ui.components.Avatar
import kotlin.math.min




@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun RoomCard(
    room:Room,
    showMenu:Boolean,
    onDismissMenu:() -> Unit,
    onShowMenu:() -> Unit,
    onReport:() -> Unit,
    onJoin:() -> Unit
){
    Surface(
//        elevation = 2.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column() {

                    Text(text = room.topic,style = MaterialTheme.typography.h5,fontWeight = FontWeight.Bold,color = MaterialTheme.colors.primary)

                    Row {
                        Text(text = room.language,style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = room.level)
                    }
                }
                Box() {
                    IconButton(onClick = onShowMenu) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu")
                    }
                    DropdownMenu(expanded = showMenu,onDismissRequest = onDismissMenu) {
                        DropdownMenuItem(onClick = onReport) {
                            Text(text = "Report")
                        }
                    }
                }

            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                for(i in 0 until room.limit){
                    if(i < room.members.size) Avatar(modifier = Modifier.weight(1f),src = room.members[i].avatar,onClick = {},border = BorderStroke(width = 1.dp,color = MaterialTheme.colors.primary))
                    else Surface(border = BorderStroke(width = 1.dp,color = MaterialTheme.colors.background),shape = CircleShape,modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)){}
                }
            }
            OutlinedButton(onClick = onJoin,modifier = Modifier.align(Alignment.CenterHorizontally),enabled = room.members.size < room.limit
            ) {
                Text(text = stringResource(id = R.string.join_room))
            }
        }
    }
}
