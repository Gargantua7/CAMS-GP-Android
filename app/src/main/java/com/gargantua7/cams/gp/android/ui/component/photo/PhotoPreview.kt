package com.gargantua7.cams.gp.android.ui.component.photo

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

/**
 * @author Gargantua7
 */
interface PhotoPreview {

    @Composable
    fun FullScreenImage(
        modifier: Modifier = Modifier,
        image: Bitmap,
        onClick: () -> Unit = {}
    ) {
        var scale by remember { mutableStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState { zoomChange, _, _ ->
            scale = (zoomChange * scale).coerceAtLeast(1f)
        }

        Surface(
            color = MaterialTheme.colors.onSecondary,
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            scale = 1f
                            offset = Offset.Zero
                        },
                        onTap = {
                            onClick.invoke()
                        }
                    )
                }
        ) {
            Image(
                image.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(state = state)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
                    .pointerInput(Unit) {
                        detectDragGestures { _, dragAmount ->
                            offset += dragAmount
                        }
                    }

            )
        }
    }
}
