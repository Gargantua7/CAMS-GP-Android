package com.gargantua7.cams.gp.android.ui.component.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * @author Gargantua7
 */
@Composable
fun IconRow(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth().run {
                onClick?.let {
                    clickable { it() }
                } ?: this
            }
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Icon(icon, "Icon")
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(0.dp, 15.dp)
        )
    }
}

@Composable
fun IconRow(
    icon: ImageVector,
    hint: String,
    text: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth().run {
                onClick?.let {
                    clickable { it() }
                } ?: this
            }
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Icon(icon, "Icon")
        Spacer(modifier = Modifier.width(10.dp))
        Column(Modifier.padding(0.dp, 10.dp)) {
            Text(text = hint, color = MaterialTheme.colors.secondary, fontSize = 12.sp)
            Text(
                text = text,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}
