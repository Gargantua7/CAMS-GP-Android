package com.gargantua7.cams.android.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author Gargantua7
 */
sealed class Page {
    abstract val title: String
    abstract val icon: ImageVector

    @Composable
    abstract fun draw()

    @Composable
    abstract fun fab()
}

val item = itemsWithList + listOf(Messages, Me)


