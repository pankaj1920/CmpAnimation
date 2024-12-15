package com.app.composeanimation

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun HomeScreen() {

    Box(modifier = Modifier.fillMaxSize().background(Color.Green).padding(20.dp)) {
        Image(
            imageVector =  Icons.Filled.Face, // Replace with your avatar image
            contentDescription = "Animated Avatar",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}