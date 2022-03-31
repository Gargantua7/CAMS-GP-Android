package com.gargantua7.cams.gp.android.ui.component.topbar

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * @author Gargantua7
 */
interface BackTopBar : TopBar {

    fun onBack(context: Context) {
        if (context is Activity) {
            context.finish()
        }
    }

    @Composable
    override fun RowScope.leftComponents() {
        val context = LocalContext.current
        IconButton(onClick = { onBack(context) }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }

}
