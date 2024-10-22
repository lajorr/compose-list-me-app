package com.example.compose_list_me_app.common.composables

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FlippingCard() {
    var rotationState by remember { mutableFloatStateOf(0f) }
    var isFlipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = rotationState,
        // You can adjust the animation parameters here
        animationSpec = tween(durationMillis = 400), label = ""
    )

    Card(modifier = Modifier
        .size(100.dp)
        .background(Color.Gray)
        .clickable {
            if (!isFlipped) {
                rotationState = 180f
                isFlipped = true
            } else {
                rotationState = 0f
                isFlipped = false
            }
        }
        .pointerInput(Unit) {
            detectHorizontalDragGestures { change, dragAmount ->
                change.consume()
                Log.i("draggg", "FlippingCard: drag amount -> $dragAmount")
                if (dragAmount > 10) {
                    rotationState = 180f
                    isFlipped = true
                } else if (dragAmount < -10) {
                    rotationState = 0f
                    isFlipped = false
                }
//
//                rotationState += dragAmount * 2
//                if (rotationState.absoluteValue > 90) {
//                    isFlipped = !isFlipped
//                    rotationState = 90f // Reset rotation
//                }
            }

        }
        .graphicsLayer(rotationY = rotation)) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(if (isFlipped) Color.Blue else Color.Red)
        ) {
            // Content of your card goes here
        }
    }
}

@Preview
@Composable
private fun FlippingCardPrev() {
    FlippingCard()

}
