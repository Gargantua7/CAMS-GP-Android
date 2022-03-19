package com.gargantua7.cams.android.ui.main

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gargantua7.cams.android.CAMSApplication
import com.gargantua7.cams.android.R

/**
 * @author Gargantua7
 */
object Event: Page() {

    override val title = CAMSApplication.context.getString(R.string.event)
    override val icon = Icons.Filled.Celebration

    @Composable
    override fun draw() {
    }

    @Composable
    override fun fab() {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White,
            onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Add, "add")
        }
    }
}
