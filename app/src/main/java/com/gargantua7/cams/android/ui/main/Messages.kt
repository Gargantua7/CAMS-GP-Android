package com.gargantua7.cams.android.ui.main

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Message
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gargantua7.cams.android.R
import com.gargantua7.cams.android.ui.util.stringResource

/**
 * @author Gargantua7
 */
object Messages: Page() {
    override val title = stringResource(R.string.message)
    override val icon = Icons.Filled.Message

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
