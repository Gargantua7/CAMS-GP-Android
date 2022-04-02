package com.gargantua7.cams.gp.android.ui.component.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * @author Gargantua7
 */
interface SendTopBar : TopBar {

    fun onSend()

    @Composable
    override fun RowScope.rightComponents() {
        IconButton(onClick = ::onSend) {
            Icon(imageVector = Icons.Filled.Send, contentDescription = "Send", tint = Color.White)
        }
    }

}
