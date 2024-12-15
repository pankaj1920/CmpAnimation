package com.app.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.app.composeanimation.ui.theme.ComposeAnimationTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAnimationTheme(darkTheme = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
//                        AnimatedVisibilityExample()
//                        GameCharacterMovement()
                        AnimatedAvatar()
                    }

                }
            }
        }
    }
}

@Composable
fun AnimatedVisibilityExample() {
    val startPosition = Offset(100f, 100f)
    val endPosition = Offset(250f, 400f)
}

@Composable
fun AnimatedAvatar() {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    // Animatable values for x and y positions
    val xOffset = remember { Animatable(screenWidth.toFloat()) }
    val yOffset = remember { Animatable(screenHeight.toFloat()) }

    // Trigger animation when the composable is displayed
    LaunchedEffect(Unit) {
        xOffset.animateTo(
            targetValue = (screenWidth / 2).toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
        yOffset.animateTo(
            targetValue = (screenHeight / 2).toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    // Avatar UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C)) // Background color
    ) {
        Image(
            imageVector =  Icons.Filled.Face, // Replace with your avatar image
            contentDescription = "Animated Avatar",
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer(
                    translationX = xOffset.value - 40.dp.toPx(), // Offset for centering
                    translationY = yOffset.value - 40.dp.toPx()
                )
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}


@Composable
fun GameCharacterMovement() {
    val startPosition = Offset(100f, 100f)
    val endPosition = Offset(250f, 400f)
    val controlPoint = remember { mutableStateOf(Offset(200f, 300f)) }
    val position = remember { Animatable(startPosition, Offset.VectorConverter) }

    LaunchedEffect(controlPoint.value) {
        position.animateTo(
            targetValue = endPosition,
            animationSpec = keyframes {
                durationMillis = 5000
                controlPoint.value at 2500 // midway point controlled by the draggable control point
            }
        )
    }

    val onControlPointChange: (offset: Offset) -> Unit = {
        controlPoint.value = it
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Icon(
            Icons.Filled.Face, contentDescription = "Localized description", modifier = Modifier
                .size(50.dp)
                .offset(x = position.value.x.dp, y = position.value.y.dp)
        )

        DraggableControlPoint(controlPoint.value, onControlPointChange)
    }
}

@Composable
fun DraggableControlPoint(controlPoint: Offset, onControlPointChange: (Offset) -> Unit) {
    var localPosition by remember { mutableStateOf(controlPoint) }
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = localPosition.x.roundToInt() - 15,
                    y = localPosition.y.roundToInt() - 15
                )
            }
            .size(30.dp)
            .background(Color.Red, shape = CircleShape)
            .pointerInput(Unit) {
                detectDragGestures(onDragEnd = {
                    onControlPointChange(localPosition)
                }) { _, dragAmount ->
                    // adjust based on screen bounds
                    val newX = (localPosition.x + dragAmount.x).coerceIn(0f, 600f)
                    val newY = (localPosition.y + dragAmount.y).coerceIn(0f, 600f)
                    localPosition = Offset(newX, newY)
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAnimationTheme {

    }
}