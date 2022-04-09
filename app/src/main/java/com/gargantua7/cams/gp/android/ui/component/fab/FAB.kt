package com.gargantua7.cams.gp.android.ui.component.fab

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.gargantua7.cams.gp.android.ui.component.compose.ComposeActivity

/**
 * @author Gargantua7
 */
interface FAB {

    @Composable
    fun fab()

    @Composable
    fun fab(icons: ImageVector) {
        val context = LocalContext.current as ComposeActivity
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            onClick = { fabOnClick(context) }
        ) {
            Icon(icons, "icon")
        }
    }

    fun fabOnClick(context: ComposeActivity)
}
