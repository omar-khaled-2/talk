package com.sphinx.talk.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.sphinx.talk.Constant
import com.sphinx.talk.R



@ExperimentalMaterialApi
@Composable
fun Avatar(
    src:String? = null,
    modifier: Modifier = Modifier,
    onClick:() -> Unit = {},
    elevation:Dp = 1.dp,
    baseUrl:String = Constant.SERVER_URL,
    border: BorderStroke? = null
){
    Surface(
        modifier = modifier,
        shape = CircleShape,
        onClick = onClick,
        elevation = elevation,
        border = border
    ) {
        Image(
            painter = if(src !== null) rememberImagePainter(baseUrl + src) else painterResource(id = R.drawable.profile),
            contentDescription = "avatar",
            modifier = modifier.aspectRatio(1f)
        )
    }
}